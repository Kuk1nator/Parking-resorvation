package com.example.gui;

import com.example.model.ParkingSpot;
import com.example.service.ParkingService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private ParkingService parkingService = new ParkingService();
    private JPanel parkingSpotsPanel;
    private static final int SPOTS_PER_ROW = 3; // Количество мест в строке
    private static final int NUMBER_OF_ROWS = 10; // Количество строк

    public MainFrame() {
        setTitle("Parking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Увеличенный размер
        setLayout(new BorderLayout());

        parkingSpotsPanel = new JPanel();
        parkingSpotsPanel.setLayout(new GridLayout(NUMBER_OF_ROWS, SPOTS_PER_ROW, 10, 10)); // Use GridLayout for rows and columns, spacing between components
        add(new JScrollPane(parkingSpotsPanel), BorderLayout.CENTER); // Add a scroll pane to the center

        refreshParkingSpotsPanel();

        setLocationRelativeTo(null);
        setVisible(true); // Добавлено, чтобы окно было видно
    }

    private void refreshParkingSpotsPanel() {
        parkingSpotsPanel.removeAll();
        List<ParkingSpot> spots = parkingService.getAllParkingSpots();
        for (ParkingSpot spot : spots) {
            ParkingSpotPanel spotPanel = new ParkingSpotPanel(spot, parkingService, this);
            parkingSpotsPanel.add(spotPanel);
        }
        parkingSpotsPanel.revalidate();
        parkingSpotsPanel.repaint();
    }

    public void updateParkingSpot(ParkingSpot spot) {
        parkingService.updateParkingSpot(spot);
        refreshParkingSpotsPanel();
    }

    public void deleteParkingSpot(ParkingSpot spot) {
        parkingService.deleteParkingSpot(spot);
        refreshParkingSpotsPanel();
    }

    public void reserveParkingSpot(int spotNumber) {
        parkingService.reserveParkingSpot(spotNumber);
        refreshParkingSpotsPanel();
    }
}