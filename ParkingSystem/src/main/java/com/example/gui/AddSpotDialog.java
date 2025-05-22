package com.example.gui;

import com.example.model.ParkingSpot;
import com.example.service.ParkingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSpotDialog extends JDialog {

    private JTextField spotNumberField;
    private JCheckBox occupiedCheckBox;
    public AddSpotDialog(JFrame parent, ParkingService parkingService) {
        super(parent, "Добавить парковочное место", true); // true означает модальное окно
        setLayout(new GridLayout(3, 2));
        setSize(300, 150);

        JLabel spotNumberLabel = new JLabel("Номер места:");
        spotNumberField = new JTextField();

        JLabel occupiedLabel = new JLabel("Занято:");
        occupiedCheckBox = new JCheckBox();

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int spotNumber = Integer.parseInt(spotNumberField.getText());
                    boolean occupied = occupiedCheckBox.isSelected();

                    ParkingSpot newSpot = new ParkingSpot(spotNumber, occupied);
                    // The addParkingSpot method has been removed. Implement the desired functionality here.
                    JOptionPane.showMessageDialog(AddSpotDialog.this, "Парковочное место добавлено: " + newSpot);

                    dispose(); // Закрыть диалог
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddSpotDialog.this, "Неверный формат номера места.");
                }
            }
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> dispose());

        add(spotNumberLabel);
        add(spotNumberField);
        add(occupiedLabel);
        add(occupiedCheckBox);
        add(addButton);
        add(cancelButton);

        setLocationRelativeTo(parent); // По центру относительно родительского окна
    }
}