package com.example.service;

import com.example.model.ParkingSpot;
import com.example.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingService {

    private static final String DATA_FILE = "data/parking_data.txt";
    private List<ParkingSpot> parkingSpots = new ArrayList<>();
    private static final int NUMBER_OF_SPOTS = 30; // Количество парковочных мест

    public ParkingService() {
        parkingSpots = FileUtil.loadParkingSpots(DATA_FILE);

        if (parkingSpots == null || parkingSpots.isEmpty()) {
            // Initialize with 30 spots and random occupancy
            parkingSpots = initializeParkingSpots(NUMBER_OF_SPOTS);
            FileUtil.saveParkingSpots(parkingSpots, DATA_FILE); // Save the initial state
        }
    }

    private List<ParkingSpot> initializeParkingSpots(int numberOfSpots) {
        List<ParkingSpot> spots = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= numberOfSpots; i++) {
            // Randomly determine if the spot is occupied
            boolean occupied = random.nextBoolean();
            spots.add(new ParkingSpot(i, occupied));
        }
        return spots;
    }

    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpots;
    }


    public void updateParkingSpot(ParkingSpot spot) {
        for (int i = 0; i < parkingSpots.size(); i++) {
            if (parkingSpots.get(i).getSpotNumber() == spot.getSpotNumber()) {
                parkingSpots.set(i, spot);
                FileUtil.saveParkingSpots(parkingSpots, DATA_FILE);
                return;
            }
        }
    }

    public void deleteParkingSpot(ParkingSpot spot) {
        parkingSpots.removeIf(s -> s.getSpotNumber() == spot.getSpotNumber());
        FileUtil.saveParkingSpots(parkingSpots, DATA_FILE);
    }

    public void reserveParkingSpot(int spotNumber) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotNumber() == spotNumber) {
                spot.setOccupied(true);
                FileUtil.saveParkingSpots(parkingSpots, DATA_FILE);
                return;
            }
        }
    }

    public void cancelReservation(int spotNumber) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotNumber() == spotNumber) {
                spot.setOccupied(false); // Set spot to unoccupied
                FileUtil.saveParkingSpots(parkingSpots, DATA_FILE);
                return;
            }
        }
    }
}