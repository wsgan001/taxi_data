package com.nwu.data.taxi.service;

import com.nwu.data.taxi.domain.model.*;
import com.nwu.data.taxi.domain.repository.*;
import com.nwu.data.taxi.service.helper.Config;
import com.nwu.data.taxi.service.helper.ProbabilityWrapper;
import com.nwu.data.taxi.service.helper.Recommender;
import com.nwu.data.taxi.service.helper.Task;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Passenger;
import com.nwu.data.taxi.service.helper.model.Vehicle;
import com.nwu.data.taxi.service.helper.recommender.MyRecommender;
import com.nwu.data.taxi.service.helper.task.PassengerAddTask;
import com.nwu.data.taxi.service.helper.task.VehicleTurnOffTask;
import com.nwu.data.taxi.service.helper.task.VehicleTurnOnTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SimulatorService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private HashMap<Integer, Grid> graph;
    private HashMap<Long, List<Task>> tasks;
    private List<Vehicle> vehicles;
    private List<Performance> performances;
    private HashMap<String, List<GridProbability>> probabilities;
    private long startTime, endTime;
    private Recommender recommender;
    private int numberOfVehicles;
    @Autowired
    private TaxiRepository taxiRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private TripEventRepository tripEventRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private GridProbabilityRepository gridProbabilityRepository;
    @Autowired
    private PerformanceRepository performanceRepository;

    public List<Performance> process(Recommender recommender, long startTime, long endTime,
                        int numberOfVehicles){
        graph = new HashMap<>();
        tasks = new HashMap<>();
        probabilities = new HashMap<>();
        performances = new ArrayList<>();
        this.recommender = recommender;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfVehicles = numberOfVehicles;
        this.recommender = new MyRecommender();
        String date = Config.DATE_FORMATTER.format(new Date(startTime * 1000));
        loadGraph();
        loadVehicles(date);
        loadPassengers(date);
        loadWeekProbabilities();
        startRecommend();
        savePerformances();
        return performances;
    }

    private void savePerformances() {
        logger.info("Saving Performance! :)");
        for (Vehicle v : vehicles) {
            Taxi taxi = taxiRepository.findTopByName(v.getName());
            performances.add(new Performance(v, taxi));
        }
        performanceRepository.save(performances);
    }

    private void loadGraph() {
        routeRepository.findAll().forEach(route -> extractRoute(route));
        addOtherNeighbors();
    }

    private void addOtherNeighbors() {
        final int count = Config.NUM_OF_LAT_BINS * Config.NUM_OF_LON_BINS;
        for (int i = 0; i < count; i++) {
            Grid from = graph.get(i);
            if (from == null)
                continue;
            for (int j = 0; j < count; j++) {
                if (i == j)
                    continue;
                Grid to = graph.get(j);
                if (to == null)
                    continue;
                if (from.getNeighbors().contains(to)
                        && !to.getNeighbors().contains(from)) {
                    to.addNeighbor(from, from.getTime(to));
                }
            }
        }
    }

    private void extractRoute(Route route) {
        int from = route.getFromGrid();
        int to = route.getToGrid();
        double time = route.getTime();
        if (graph.get(from) == null)
            graph.put(from, new Grid(from));
        if (graph.get(to) == null)
            graph.put(to, new Grid(to));
        graph.get(from).addNeighbor(graph.get(to), time);
    }


    private void loadVehicles(String date) {
        vehicles = new ArrayList<>();
        loadTopVehicles(Math.min(numberOfVehicles / 10, 20));//3
        loadBottomVehicles(Math.min(numberOfVehicles / 10, 20));//3
        loadModerateDrivers(numberOfVehicles - vehicles.size());
        for (Vehicle v : vehicles) {
            loadTripEvent(v, date);
        }
    }

    private void loadPassengers(String date) {
        for(PassengerData passengerData : passengerRepository.findByTravelDate(date)) {
            if (null != passengerData.getStartGrid() && null != passengerData.getEndGrid()) {
                int location = passengerData.getStartGrid();
                int destination = passengerData.getEndGrid();
                long time = passengerData.getTravelDateTime() - Config.TIME_OFFSET;
                if (null != graph.get(location) && null != graph.get(destination)) {
                    Passenger traveller = new Passenger(graph.get(location), graph.get(destination), Config.WAITING_TIME);
                    tasks.computeIfAbsent(time, k -> new ArrayList<>());
                    tasks.get(time).add(new PassengerAddTask(traveller));
                }
            }
        }
    }

    private void loadTurnOffs(Vehicle v, TripEvent tripEvent, long time) {
        tasks.computeIfAbsent(time, k -> new ArrayList<>());
        if (graph.get(tripEvent.getEventGrid()) != null)
            tasks.get(time)
                    .add(new VehicleTurnOffTask(v, tripEvent.getDuration(), graph
                            .get(tripEvent.getEventGrid())));

    }

    private void loadTripEvent(Vehicle v, String date) {
        for(TripEvent tripEvent : tripEventRepository.findByTaxiNameAndEventDate(v.getName(), date)) {
            long time = tripEvent.getEventDateTime();
            if(tripEvent.getEventType() == TripEvent.TURN_ON) {
                loadTurnOn(v, date, tripEvent, time);
            } else {
                loadTurnOffs(v, tripEvent, time);
            }
        }
    }

    private void loadTurnOn(Vehicle v, String date, TripEvent tripEvent, long time) {
        if (time > -1 && time > startTime && graph.get(tripEvent.getEventGrid()) != null) {
            tasks.computeIfAbsent(time, k -> new ArrayList<>());
            tasks.get(time).add(new VehicleTurnOnTask(v, graph.get(tripEvent.getEventGrid())));
            logger.info(v.getName() + " will turn on at " + date + " " + tripEvent.getEventTime());
        }
    }

    private void loadModerateDrivers(int count) {
        loadVehiclesFromConfig(Config.MODERATE_DRIVERS, count);
    }

    private void loadBottomVehicles(int count) {
        loadVehiclesFromConfig(Config.BOTTOM_DRIVERS, count);
    }

    private void loadTopVehicles(int count) {
        loadVehiclesFromConfig(Config.TOP_DRIVERS, count);
    }

    private void loadVehiclesFromConfig(String[] drivers, int count) {
        for (int i = 0; i < count && i < drivers.length; i++) {
            vehicles.add(new Vehicle(drivers[i], null));
        }
    }


    public void startRecommend() {
        long currentTime = tasks.keySet().stream().min(Long::compare).get();
        while (currentTime < endTime) {

            ArrayList<Vehicle> vehiclesNeedRecommendation = new ArrayList<>();

            for (Vehicle v : vehicles) {
                if (v.isOn() && (v.getRoute() == null || v.getRoute().isEmpty())) {
                    vehiclesNeedRecommendation.add(v);
                }
            }

            if (!vehiclesNeedRecommendation.isEmpty()) {
                updateProbabilities(new Date(currentTime * 1000));
                logger.info("start recommending");
                recommender.recommend(vehiclesNeedRecommendation, graph);
            }
            if (null != tasks.get(currentTime)) {
                logger.info("Now is : " + Config.DATETIME_FORMATTER.format(new Date(currentTime * 1000)));
                for (Task task : tasks.get(currentTime)) {
                    task.execute(tasks, currentTime, graph);
                }
                tasks.remove(currentTime);
            }
            currentTime = tasks.keySet().stream().min(Long::compare).get();
        }
    }

    private void updateProbabilities(Date date)  {
        String time = Config.WEEK_FORMATTER.format(date) + String.format("%02d",Integer.parseInt(Config.HOUR_FORMATTER.format(date)) / 2 * 2);
        for(GridProbability gridProbability : probabilities.get(time)) {
            Grid grid = graph.get(gridProbability.getGrid());
            if (null != grid) {
                grid.setProbability(gridProbability.getProbability());
                grid.setMaxNumberOfTaxis(numberOfVehicles / 6);
            }
        }
    }

    private void loadWeekProbabilities()  {
        for(GridProbability gridProbability : gridProbabilityRepository.findByTimeTypeAndTimeChunk(ProbabilityWrapper.BY_WEEK, ProbabilityWrapper.HOUR_CHUNK)) {
            probabilities.computeIfAbsent(gridProbability.getTime(), k -> new ArrayList<>());
        }
    }
}
