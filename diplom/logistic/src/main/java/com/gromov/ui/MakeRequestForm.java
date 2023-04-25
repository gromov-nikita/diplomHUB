package com.gromov.ui;

import com.gromov.entity.Cargo;
import com.gromov.entity.Country;
import com.gromov.entity.Request;
import com.gromov.entity.User;
import com.gromov.entity.enums.CargoType;
import com.gromov.entity.enums.RequestStatus;
import com.gromov.service.DAO.CountryDAO;
import com.gromov.service.DAO.RequestDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class MakeRequestForm {
    private static JFrame makeRequestForm = new JFrame();
    private static User user;
    private Country country;
    private JPanel makeRequestPanel;
    private JLabel sendingDateLabel;
    private JLabel deliveryDateLabel;
    private JTextField sendingDateField;
    private JTextField deliveryDateField;
    private JComboBox fromCombo;
    private JComboBox toCombo;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JButton makeRequestButton;
    private JButton backButton;
    private JComboBox cargoTypeCombo;
    private JTextField cargoNameField;
    private JTextField cargoWeightField;
    private JLabel cargoWeightLabel;
    private JLabel cargoNameLabel;
    private JLabel cargoTypeLabel;
    private JPanel sendingDateLabelPanel;
    private JPanel deliveryDateLabelPanel;
    private JPanel fromLabelPanel;
    private JPanel toLabelPanel;
    private JPanel cargoNameLabelPanel;
    private JPanel cargoWeightLabelPanel;
    private JPanel cargoTypeLabelPanel;
    private JPanel makeRequestButtonPanel;
    private JPanel backButtonPanel;

    public MakeRequestForm(User user) {
        this.user = user;
        makeRequestForm.setContentPane(makeRequestPanel);
        makeRequestForm.setTitle("Запрос");
        makeRequestForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeRequestForm.setVisible(true);
        makeRequestForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        makeRequestForm.setBounds(dimension.width/2-250,dimension.height/2-155,500,310);
        fromCombo.setMaximumRowCount(5);
        toCombo.setMaximumRowCount(5);
        cargoTypeCombo.addItem(CargoType.SOLID.getName());
        cargoTypeCombo.addItem(CargoType.LIQUID.getName());

        for(Country x : CountryDAO.getListOfCountries()) {
            fromCombo.addItem(x);

            toCombo.addItem(x);
        }

        country = (Country) fromCombo.getSelectedItem();
        toCombo.removeItem(country);
        makeRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request request = new Request(Date.valueOf(sendingDateField.getText()),
                        Date.valueOf(deliveryDateField.getText()),
                        RequestStatus.IN_PROCESS,user,(Country)fromCombo.getSelectedItem(),
                        (Country)toCombo.getSelectedItem(),
                        new Cargo(cargoNameField.getText(),
                                Integer.valueOf(cargoWeightField.getText()),
                                CargoType.getCargoTypeByName((String)cargoTypeCombo.getSelectedItem()))
                );
                RequestDAO.makeRequest(request);
                new CustomerProfileForm(user);
                makeRequestForm.dispose();

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerProfileForm(user);
                makeRequestForm.dispose();
            }
        });
        fromCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toCombo.addItem(country);
                country = (Country) fromCombo.getSelectedItem();
                toCombo.removeItem(country);
            }
        });
    }

    public static JFrame getMakeRequestForm() {
        return makeRequestForm;
    }

    public static User getUser() {
        return user;
    }

    public JPanel getMakeRequestPanel() {
        return makeRequestPanel;
    }

    public JLabel getSendingDate() {
        return sendingDateLabel;
    }

    public JLabel getDeliveryDate() {
        return deliveryDateLabel;
    }

    public JTextField getSendingDateField() {
        return sendingDateField;
    }

    public JTextField getDeliveryDateField() {
        return deliveryDateField;
    }

    public JComboBox getFromCombo() {
        return fromCombo;
    }

    public JComboBox getToCombo() {
        return toCombo;
    }

    public JLabel getFromLabel() {
        return fromLabel;
    }

    public JLabel getToLabel() {
        return toLabel;
    }

    public JButton getMakeQueryButton() {
        return makeRequestButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public static void setUser(User user) {
        MakeRequestForm.user = user;
    }
}
