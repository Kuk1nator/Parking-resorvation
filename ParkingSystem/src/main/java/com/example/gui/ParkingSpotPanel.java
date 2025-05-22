package com.example.gui;

import com.example.model.ParkingSpot;
import com.example.service.ParkingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;

public class ParkingSpotPanel extends JPanel {

    private ParkingSpot spot;
    private ParkingService parkingService;
    private MainFrame mainFrame;
    private JButton actionButton;
    private static final int SPOT_WIDTH = 80;
    private static final int SPOT_HEIGHT = 40;

    public ParkingSpotPanel(ParkingSpot spot, ParkingService parkingService, MainFrame mainFrame) {
        this.spot = spot;
        this.parkingService = parkingService;
        this.mainFrame = mainFrame;

        // Layout and appearance
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Center the button
        setPreferredSize(new Dimension(SPOT_WIDTH, SPOT_HEIGHT)); // Set size
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(spot.isOccupied() ? Color.RED : Color.GREEN);

        // Label for spot number
        JLabel numberLabel = new JLabel("Место " + spot.getSpotNumber());
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        add(numberLabel);

        // Button
        if (spot.isOccupied()) {
            actionButton = new JButton("Отменить бронь"); // Button for canceling reservation
            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cancelReservation();
                }
            });
            add(actionButton);
        } else {
            actionButton = new JButton("Забронировать");
            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showReservationDialog();
                }
            });
            add(actionButton);
        }
    }

    private void showReservationDialog() {
        // Create a dialog to input car details
        JDialog dialog = new JDialog(mainFrame, "Бронирование места", true);
        dialog.setLayout(new GridLayout(5, 2)); // Added more rows
        dialog.setSize(300, 200); // Adjusted size
        dialog.setLocationRelativeTo(mainFrame);

        // Input fields
        JLabel carNumberLabel = new JLabel("Номер авто:");
        JTextField carNumberField = new JTextField();
        JLabel carModelLabel = new JLabel("Марка и модель:");
        JTextField carModelField = new JTextField();

        JLabel dateAndTimeLabel = new JLabel("Дата и время:");
        JTextField dateAndTimeField = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        JButton confirmButton = new JButton("Подтвердить");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carNumber = carNumberField.getText();
                String carModel = carModelField.getText();
                String dateTimeString = dateAndTimeField.getText();
                try {
                    LocalDateTime reservationDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    parkingService.reserveParkingSpot(spot.getSpotNumber());
                    generateReceipt(spot.getSpotNumber(), carNumber, carModel, reservationDateTime);
                    mainFrame.updateParkingSpot(spot); // Update the GUI
                    dialog.dispose(); // Close dialog

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Ошибка ввода даты/времени или формата.");
                }
            }
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> dialog.dispose());

        // Add components to the dialog
        dialog.add(carNumberLabel);
        dialog.add(carNumberField);
        dialog.add(carModelLabel);
        dialog.add(carModelField);
        dialog.add(dateAndTimeLabel);
        dialog.add(dateAndTimeField);
        dialog.add(confirmButton);
        dialog.add(cancelButton);

        dialog.setVisible(true);
    }

    private void cancelReservation() {
            parkingService.cancelReservation(spot.getSpotNumber());
            mainFrame.updateParkingSpot(spot);
    }

    private void generateReceipt(int spotNumber, String carNumber, String carModel, LocalDateTime dateTime) {
        String filename = "receipt_" + spotNumber + "_" + dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Чек бронирования\n");
            writer.write("-------------------\n");
            writer.write("Место: " + spotNumber + "\n");
            writer.write("Номер авто: " + carNumber + "\n");
            writer.write("Марка и модель: " + carModel + "\n");
            writer.write("Дата и время: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n");
            writer.write("-------------------\n");
            writer.write("Спасибо за бронь!");
            JOptionPane.showMessageDialog(this, "Чек сохранен: " + filename);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Ошибка при создании чека.");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Optional: Draw a simple rectangle (parking spot)
        g.setColor(getBackground());
        g.fillRect(0, 0, SPOT_WIDTH, SPOT_HEIGHT);
    }
}