package com.example.model;

public class ParkingSpot {
    private int spotNumber;
    private boolean occupied;

    public ParkingSpot() {
        // Конструктор по умолчанию нужен для Gson
    }

    public ParkingSpot(int spotNumber, boolean occupied) {
        this.spotNumber = spotNumber;
        this.occupied = occupied;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "spotNumber=" + spotNumber +
                ", occupied=" + occupied +
                '}';
    }
}