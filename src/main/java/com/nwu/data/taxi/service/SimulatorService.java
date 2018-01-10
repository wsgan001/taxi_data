package com.nwu.data.taxi.service;

import com.nwu.data.taxi.domain.model.*;
import com.nwu.data.taxi.domain.repository.*;
import com.nwu.data.taxi.service.helper.Config;
import com.nwu.data.taxi.service.helper.Recommender;
import com.nwu.data.taxi.service.helper.Task;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Passenger;
import com.nwu.data.taxi.service.helper.model.Vehicle;
import com.nwu.data.taxi.service.helper.recommender.MyRecommender;
import com.nwu.data.taxi.service.helper.recommender.NeighborRecommender;
import com.nwu.data.taxi.service.helper.task.PassengerAddTask;
import com.nwu.data.taxi.service.helper.task.PerformanceSaveTask;
import com.nwu.data.taxi.service.helper.task.VehicleTurnOffTask;
import com.nwu.data.taxi.service.helper.task.VehicleTurnOnTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimulatorService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private HashMap<Integer, Grid> graph;
    private HashMap<Long, List<Task>> tasks;
    private List<Vehicle> vehicles;
    private HashMap<String, List<KalGridProbability>> probabilities;
    private long endTime;
    private int recommenderType;
    private Recommender recommender;
    private int numberOfVehicles;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private TripEventRepository tripEventRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private GridProbabilityRepository gridProbabilityRepository;
    @Autowired
    private KalGridProbabilityRepository kalGridProbabilityRepository;
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private TaxiRepository taxiRepository;
    @Autowired
    private GridReadingRepository gridReadingRepository;

    public SimulatorService() {
        this.graph = new HashMap<>();
        this.tasks = new HashMap<>();
        this.probabilities = new HashMap<>();
    }

    public void initEnvironment(long startTime, long endTime,
                                int numberOfVehicles) {
        this.endTime = endTime;
        this.numberOfVehicles = numberOfVehicles;
        loadGraph();
        loadVehicles(getDate(startTime));
        loadPassengers(getDate(startTime));
    }


    public void start(int recommenderType, long startTime) {
        this.recommenderType = recommenderType;
        loadPerformanceTask(startTime, endTime, recommenderType);
        switch (recommenderType) {
            case Config.MY:
                this.recommender = new MyRecommender();
                loadKalProbabilities(getDate(startTime));
                break;
            case Config.NEIGHBOR:
                this.recommender = new NeighborRecommender();
                loadAvgProbabilities(getDate(startTime));
                break;
            case Config.AVG:
                this.recommender = new MyRecommender();
                loadAvgProbabilities(getDate(startTime));
                break;
            default:
                this.recommender = new MyRecommender();
                loadKalProbabilities(getDate(startTime));
                break;
        }

        long currentTime = tasks.keySet().stream().min(Long::compare).get();
        while (currentTime <= endTime) {

            logger.info("Now is : " + Config.DATETIME_FORMATTER.format(new Date(currentTime * 1000)));
            for (Task task : tasks.get(currentTime)) {
                task.execute(tasks, currentTime, graph);
            }
            tasks.remove(currentTime);
            if (currentTime != endTime) {
                List<Vehicle> recommendList = vehicles.stream()
                        .filter(vehicle ->
                                vehicle.isOn() &&
                                        CollectionUtils.isEmpty(vehicle.getRoute()))
                        .collect(Collectors.toList());

                if (!recommendList.isEmpty()) {
                    //                updateWeekProbabilities(new Date(currentTime * 1000));
                    updateKalProbabilities(new Date(currentTime * 1000));
                    logger.info("start recommending");
                    recommender.recommend(recommendList, graph);
                }

                if (tasks.keySet().isEmpty()) {
                    break;
                }
                currentTime = tasks.keySet().stream().min(Long::compare).get();
            } else {
                break;
            }
        }
    }


    private void loadPerformanceTask(long startTime, long endTime, int recommenderType) {
        for (long time = startTime + Config.HALF_HOUR_CHUNK; time <= endTime; time += Config.HALF_HOUR_CHUNK) {
            tasks.computeIfAbsent(time, k -> new ArrayList<>());
            tasks.get(time).add(new PerformanceSaveTask(performanceRepository, vehicles, recommenderType));
        }
    }


    private void loadGraph() {
        routeRepository.findAll().forEach(this::extractRoute);
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
        for (PassengerData passengerData : passengerRepository.findByTravelDate(date)) {
            if (null != passengerData.getStartGrid() && null != passengerData.getEndGrid()) {
                int location = passengerData.getStartGrid();
                int destination = passengerData.getEndGrid();
                long time = passengerData.getTravelDateTime() - Config.TIME_OFFSET;
                time =  date.equals(getDate(time)) ? time : passengerData.getTravelDateTime();
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
        for (TripEvent tripEvent : tripEventRepository.findByTaxiNameAndEventDate(v.getName(), date)) {
            long time = tripEvent.getEventDateTime();
            if (tripEvent.getEventType() == TripEvent.TURN_ON) {
                loadTurnOn(v, tripEvent, time);
            } else {
                loadTurnOffs(v, tripEvent, time);
            }
        }
    }

    private void loadTurnOn(Vehicle v, TripEvent tripEvent, long time) {
        if (graph.get(tripEvent.getEventGrid()) != null) {
            tasks.computeIfAbsent(time, k -> new ArrayList<>());
            tasks.get(time).add(new VehicleTurnOnTask(v, graph.get(tripEvent.getEventGrid())));
            logger.info(v.getName() + " will turn on at " + tripEvent.getEventDate() + " " + tripEvent.getEventTime());
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
        List<String> names = new ArrayList<>();
        for (int i = 0; i < count && i < drivers.length; i++) {
            names.add(drivers[i]);
        }
        taxiRepository.findByNameIn(names).forEach(taxi -> vehicles.add(new Vehicle(taxi)));
    }

    private void updateWeekProbabilities(Date date) {
        String time = Config.WEEK_FORMATTER.format(date) + String.format("%02d", Integer.parseInt(Config.HOUR_FORMATTER.format(date)) / 2 * 2);
        updateProbabilities(time);
    }

    private void updateProbabilities(String time) {
        for (KalGridProbability gridProbability : probabilities.get(time)) {
            Grid grid = graph.get(gridProbability.getGrid());
            if (null != grid) {
                double probability = recommenderType == Config.MY ? gridProbability.getProbability() : gridProbability.getAvgProbability();
                grid.setProbability(probability);
                grid.setMaxNumberOfTaxis(numberOfVehicles / 6);
            }
        }
    }

    private void updateKalProbabilities(Date date) {
        String minute = Integer.parseInt(Config.MINUTE_FORMATTER.format(date)) > 30 ? "30" : "00";
        String time = Config.KAL_HOUR_FORMATTER.format(date) + minute;
        updateProbabilities(time);
    }

//    private void loadWeekProbabilities() {
//        for (GridProbabilityI gridProbability : gridProbabilityRepository.findByTimeTypeAndTimeChunk(ProbabilityWrapper.BY_WEEK, ProbabilityWrapper.HOUR_CHUNK)) {
//            probabilities.computeIfAbsent(gridProbability.getTime(), k -> new ArrayList<>());
//            probabilities.get(gridProbability.getTime()).add(gridProbability);
//        }
//    }

    private void loadKalProbabilities(String date) {
        for (KalGridProbability kalGridProbability : kalGridProbabilityRepository.findByTimeContains(date)) {
            probabilities.computeIfAbsent(kalGridProbability.getTime(), k -> new ArrayList<>());
            probabilities.get(kalGridProbability.getTime()).add(kalGridProbability);
        }
    }

    private void loadAvgProbabilities(String date) {
        for (KalGridProbability kalGridProbability : kalGridProbabilityRepository.findByTimeContains(date)) {
            probabilities.computeIfAbsent(kalGridProbability.getTime(), k -> new ArrayList<>());
            probabilities.get(kalGridProbability.getTime()).add(kalGridProbability);
        }
    }

    private String getDate(long startTime) {
        return Config.DATE_FORMATTER.format(new Date(startTime * 1000));
    }

    public void initRealWord(long startTime, long endTime, int numberOfVehicles) {
        this.endTime = endTime;
        loadGraph();
        vehicles = new ArrayList<>();
        loadTopVehicles(Math.min(numberOfVehicles / 10, 20));//3
        loadBottomVehicles(Math.min(numberOfVehicles / 10, 20));//3
        loadModerateDrivers(numberOfVehicles - vehicles.size());
        loadPerformanceTask(startTime, endTime, recommenderType);
    }

    public void analyzeReal() {
        long currentTime = tasks.keySet().stream().min(Long::compare).get();
        while (currentTime <= endTime) {

            logger.info("Now is : " + Config.DATETIME_FORMATTER.format(new Date(currentTime * 1000)));

            for (Vehicle vehicle : vehicles) {
                double travelDistance = vehicle.getTravelDistance();
                double liveDistance = vehicle.getLiveDistance();
                double travelTime = vehicle.getTravelTime();
                double liveTime = vehicle.getLiveTime();
                double time = currentTime - 7200;

                for (GridReading gridReading : gridReadingRepository.findByTaxiIdAndEventDateTimeIsBetweenOrderByEventDateTime(vehicle.getTaxi().getId(), currentTime - 7200, currentTime)) {
                    Grid grid = graph.get(gridReading.getEventGrid());
                    if (null == vehicle.getCurrentGrid()) {
                        vehicle.setCurrentGrid(grid);
                    } else {
                        double duration = gridReading.getEventDateTime() - time;
                        if (duration < Config.MAX_TIME_INTERVAL && null != grid) {
                            double distance = vehicle.getCurrentGrid().getDistance(grid);
                            travelDistance += distance;
                            travelTime += duration;
                            if (gridReading.getStatus() == 1) {
                                liveDistance += distance;
                                liveTime += duration;
                            }
                        }
                        time = gridReading.getEventDateTime();
                        vehicle.setCurrentGrid(grid);
                    }
                }

                vehicle.setTravelDistance(travelDistance);
                vehicle.setLiveDistance(liveDistance);
                vehicle.setTravelTime(travelTime);
                vehicle.setLiveTime(liveTime);
            }

            for (Task task : tasks.get(currentTime)) {
                task.execute(tasks, currentTime, graph);
            }
            tasks.remove(currentTime);
            if (tasks.keySet().isEmpty()) {
                break;
            }
            currentTime = tasks.keySet().stream().min(Long::compare).get();
        }
    }
}
