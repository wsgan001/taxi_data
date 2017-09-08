package com.nwu.data.taxi.service.helper;

import com.nwu.data.taxi.domain.model.GPSReading;
import com.nwu.data.taxi.domain.model.Passenger;
import com.nwu.data.taxi.domain.model.TripEvent;
import com.nwu.data.taxi.domain.repository.PassengerRepository;
import com.nwu.data.taxi.domain.repository.TripEventRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public class TurnOnTurnOffProcessor {
    private PassengerRepository passengerRepository;
    private TripEventRepository tripEventRepository;

    public TurnOnTurnOffProcessor(PassengerRepository passengerRepository, TripEventRepository tripEventRepository) {
        this.passengerRepository = passengerRepository;
        this.tripEventRepository = tripEventRepository;
    }

    public void extractPassengers() {
        File mainDir = Config.getDataFolder();
        int cnt = 0;
        if (mainDir.isDirectory()) {
            for (File f : mainDir.listFiles(Config.getFileFilter())) {
                try {
                    processFile(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(cnt++);
                if (cnt > Config.getMaxnumberoffilestoread())
                    break;
            }
        }
    }

    private void processFile(File f) throws FileNotFoundException {
        Scanner sc = new Scanner(f);
        Integer DropOffLocation = null;
        GPSReading lastReading = null;
        String name = f.getName().split("\\.")[0];
        int fileLine = 0;
        while (sc.hasNext()) {
            fileLine++;
            GPSReading thisReading = parseLine(name, sc.nextLine().trim());
            if (lastReading == null) {
                if (thisReading.isOccupied()) {
                    DropOffLocation = thisReading.getGrid();
                }
                lastReading = thisReading;
            } else {
                if (map(lastReading.getDate()) != map(thisReading.getDate()))
//                    p.println(map(lastReading.getDate()) +"\t"+ lastReading.getGrid()+"\t" + lastReading.getTime());
                if (lastReading.getTime() - thisReading.getTime() > Config
                        .getMaxTimeInterval()) {
                    printTurnOnAndTurnOffs(lastReading, thisReading);
                    if (DropOffLocation != null) {
                        Date d = lastReading.getDate();
                        int date = map(d);
                        Passenger passenger = new Passenger(date, lastReading.getGrid(), DropOffLocation, lastReading.getTime());
                        passengerRepository.save(passenger);
                        //                        passengerPrintStreams.get(id).println(
//                                lastReading.getGrid() + "\t" + DropOffLocation
//                                        + "\t" + lastReading.getTime());
                        DropOffLocation = null;
                    }

                } else {
                    if (thisReading.isOccupied() && !lastReading.isOccupied()) {
                        // drop off
                        DropOffLocation = lastReading.getGrid();
                    } else if (!thisReading.isOccupied()
                            && lastReading.isOccupied()) {
                        // pickup
                        Date d = lastReading.getDate();
                        int date = map(d);
                        Passenger passenger = new Passenger(date, lastReading.getGrid(), DropOffLocation, lastReading.getTime());
                        passengerRepository.save(passenger);
//                        if (passengerPrintStreams.get(id) == null) {
//                            passengerPrintStreams.put(id, new PrintStream(
//                                    new File(Config.getAnalysisoutputpath()
//                                            + "//Passengers//" + id + ".txt")));
//                        }
//                        passengerPrintStreams.get(id).println(
//                                lastReading.getGrid() + "\t" + DropOffLocation
//                                        + "\t" + lastReading.getTime());
                        DropOffLocation = null;
                    }
                }
                lastReading = thisReading;
            }
        }
        System.out.println("file name : " + name + "line: "+ fileLine++);
    }

    private void printTurnOnAndTurnOffs(GPSReading lastReading, GPSReading thisReading) throws FileNotFoundException {
        Date turnOffDate = thisReading.getDate();
        Date turnOnDate = lastReading.getDate();
        int turnOffTime = map(turnOffDate);
        int turnOnTime = map(turnOnDate);

        Date dayOff = new Date(turnOffDate.getYear(), turnOffDate.getMonth(),
                turnOffDate.getDate());
		Date dayOn = new Date(turnOnDate.getYear(), turnOnDate.getMonth(),
				turnOnDate.getDate());
		TripEvent tripEvent = new TripEvent(turnOffTime, (byte) 0, (thisReading.getTime() - dayOff.getTime() / 1000), lastReading.getGrid(), (lastReading.getTime() - thisReading.getTime()));
        tripEventRepository.save(tripEvent);
//		printStreams.get(turnOnTime).println(
//				1 + "\t" + (lastReading.getTime() - dayOn.getTime() / 1000)
//						+ "\t" + lastReading.getGrid());
//        printStreams.get(turnOffTime).println(
//                0 + "\t" + (thisReading.getTime() - dayOff.getTime() / 1000)
//                        + "\t"
//                        + (lastReading.getTime() - thisReading.getTime())
//                        + "\t" + lastReading.getGrid());
    }

    private int map(Date d) {
        return (d.getYear() * 12 + d.getMonth()) * 31 + d.getDate() - 1;
    }

    private GPSReading parseLine(String name, String nextLine) {
        Scanner sc = new Scanner(nextLine);
        double lat = sc.nextDouble();
        double lon = sc.nextDouble();
        byte status = (byte) (sc.next().charAt(0) == '1' ? 1 : 0);
        long time = sc.nextLong();
        return new GPSReading(name, lat, lon, time, status);
    }
}
