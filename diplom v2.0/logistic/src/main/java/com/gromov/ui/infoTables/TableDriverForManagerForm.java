package com.gromov.ui.infoTables;

import com.gromov.entity.Driver;
import com.gromov.entity.User;
import com.gromov.entity.enums.*;
import com.gromov.service.DAO.DriverDAO;
import com.gromov.service.dataExport.ExcelAdapter;
import com.gromov.ui.ManagerOrderForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TableDriverForManagerForm {
    private static JFrame tableDriverForm = new JFrame();
    private static User user;
    private JPanel tableDriverPanel;
    private JLabel driverLabel;
    private JPanel driverLabelPanel;
    private JPanel buttonPanel;
    private JButton backButton;
    private JButton updateTableButton;
    private JPanel driverTablePanePanel;
    private JTable driverTable;
    private JScrollPane driverTablePane;
    private JPanel findByNameDriver;
    private JTextField findField;
    private JButton findButton;
    private JComboBox findCombo;
    private JComboBox availabilityCombo;
    private JButton excelExportButton;
    private JComboBox ratingCombo;

    public TableDriverForManagerForm(User user) {
        tableDriverForm.setAlwaysOnTop(true);
        this.user = user;
        tableDriverForm.setContentPane(tableDriverPanel);
        tableDriverForm.setTitle("Справочная информация");
        tableDriverForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableDriverForm.setVisible(true);
        tableDriverForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        tableDriverForm.setBounds(dimension.width/2-500, dimension.height / 2 - 200, 1000, 400);
        createDriverTable();
        findCombo.setMaximumRowCount(10);
        availabilityCombo.setMaximumRowCount(10);
        ratingCombo.setMaximumRowCount(10);
        findCombo.addItem(FindSystem.NAME.getName());
        findCombo.addItem(FindSystem.LESS_PRICE.getName());
        findCombo.addItem(FindSystem.MORE_MAX_WEIGHT.getName());
        findCombo.addItem(FindSystem.CARGO_TYPE.getName());
        availabilityCombo.addItem(DriverAvailability.ALL.getName());
        availabilityCombo.addItem(DriverAvailability.AVAILABLE.getName());
        availabilityCombo.addItem(DriverAvailability.NOT_AVAILABLE.getName());
        ratingCombo.addItem(FindByRating.ALL.getName());
        ratingCombo.addItem(FindByRating.MORE_TWO.getName());
        ratingCombo.addItem(FindByRating.LESS_TWO.getName());
        ratingCombo.addItem(FindByRating.MORE_THREE.getName());
        ratingCombo.addItem(FindByRating.LESS_THREE.getName());
        ratingCombo.addItem(FindByRating.MORE_FOUR.getName());
        ratingCombo.addItem(FindByRating.LESS_FOUR.getName());
        ratingCombo.addItem(FindByRating.NOTHING.getName());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerOrderForm(user);
                tableDriverForm.dispose();
            }
        });
        updateTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDriverTable();
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDriverTableBy();
            }
        });
        excelExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = new Date().toString();
                date = date.replaceAll(":","-");
                ExcelAdapter.excelExport(driverTable, "Водители и их транспорт "
                        + date, "Водители и их транспорт");
            }
        });
    }
    private void createDriverTableBy() {
        List<Driver> drivers;
        FindSystem findBy;

        if(!findField.getText().isEmpty()) {
            findBy = FindSystem.getFindSystemTypeByName((String)findCombo.getSelectedItem());
            switch (findBy) {
                case NAME: {
                    drivers = DriverDAO.getListOfDriversByAvailabilityAndNameAndManager(
                            findField.getText(),
                            DriverAvailability.getDriverAvailabilityByName(
                                    (String) availabilityCombo.getSelectedItem()),user);
                    break;
                }
                case CARGO_TYPE: {
                    drivers = DriverDAO.getListOfDriversByAvailabilityAndCargoTypeAndManager(
                            CargoType.getCargoTypeByName(findField.getText()),
                            DriverAvailability.getDriverAvailabilityByName(
                                    (String) availabilityCombo.getSelectedItem()),user);
                    break;
                }
                case LESS_PRICE: {
                    drivers = DriverDAO.getListOfDriversByAvailabilityAndPriceLessAndManager(
                            Integer.valueOf((String) findField.getText()),
                            DriverAvailability.getDriverAvailabilityByName(
                                    (String) availabilityCombo.getSelectedItem()),user);
                    break;
                }
                case MORE_MAX_WEIGHT: {
                    drivers = DriverDAO.getListOfDriversByAvailabilityAndWeightMoreAndManager(
                            Integer.valueOf((String) findField.getText()),
                            DriverAvailability.getDriverAvailabilityByName(
                                    (String) availabilityCombo.getSelectedItem()),user);
                    break;
                }
                default: {
                    drivers = null;
                }
            }
        }
        else {
            drivers = DriverDAO.getListOfDriversByAvailabilityAndManager(
                    DriverAvailability.getDriverAvailabilityByName(
                            (String)availabilityCombo.getSelectedItem()),user);
        }
        if(!(FindByRating.getRatingByName((String)ratingCombo.getSelectedItem()).equals(FindByRating.ALL))) {
            List<Driver> saveDrivers = new LinkedList<>();
            switch (FindByRating.getRatingByName((String)ratingCombo.getSelectedItem())) {
                case MORE_TWO : {
                    for(Driver x : drivers) {
                        if(x.getRating() >= 2) {
                            saveDrivers.add(x);
                        }
                    }
                    break;
                }
                case MORE_THREE : {
                    for(Driver x : drivers) {
                        if(x.getRating() >= 3) {
                            saveDrivers.add(x);
                        }
                    }
                    break;
                }
                case MORE_FOUR : {
                    for(Driver x : drivers) {
                        if(x.getRating() >= 4) {
                            saveDrivers.add(x);
                        }
                    }
                    break;
                }
                case LESS_TWO : {
                    for(Driver x : drivers) {
                        if(x.getRating() < 2 && (((int)x.getRating()) !=-1)) {
                            saveDrivers.add(x);
                        }
                    }
                    break;
                }
                case LESS_THREE : {
                    for(Driver x : drivers) {
                        if(x.getRating() < 3 && (((int)x.getRating()) !=-1)) {
                            saveDrivers.add(x);
                        }
                    }
                    break;
                }
                case LESS_FOUR : {
                    for(Driver x : drivers) {
                        if(x.getRating() < 4 && (((int)x.getRating()) !=-1)) {
                            saveDrivers.add(x);
                        }
                    }
                    break;
                }
                case NOTHING : {
                    for(Driver x : drivers) {
                        if((((int)x.getRating())) == -1) {
                            saveDrivers.add(x);
                        }
                    }
                }
            }
            drivers = saveDrivers;
        }
        DefaultTableModel model = new DefaultTableModel(null,new String[] {
                "Ф.И.О.","Цена(BYN/км)","Грузоподъемность(кг)","Тип груза","Средняя оценка","Статус"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        float rating;
        for(Driver x : drivers) {
            rating = x.getRating();
            if(!(((int)rating+1) == 0)) {
                model.insertRow(model.getRowCount(), new String[]{
                        x.getName(), x.getPrice() + "", x.getTruck().getWeight() + "",
                        x.getTruck().getType().getName(), rating+"",x.getAvailability().getName()
                });
            }
            else {
                model.insertRow(model.getRowCount(), new String[]{
                        x.getName(), x.getPrice() + "", x.getTruck().getWeight() + "",
                        x.getTruck().getType().getName(), "Нет оценок" ,x.getAvailability().getName()
                });
            }
        }
        driverTable.setModel(model);
    }
    private void createDriverTable() {
        List<Driver> drivers = DriverDAO.getListOfDrivers();
        DefaultTableModel model = new DefaultTableModel(null,new String[] {
                "Ф.И.О.","Цена(BYN/км)","Грузоподъемность(кг)","Тип груза","Средняя оценка","Статус"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        float rating;
        for(Driver x : drivers) {
            rating = x.getRating();
            if(!(((int)rating+1) == 0)) {
                model.insertRow(model.getRowCount(), new String[]{
                        x.getName(), x.getPrice() + "", x.getTruck().getWeight() + "",
                        x.getTruck().getType().getName(), rating+"",x.getAvailability().getName()
                });
            }
            else {
                model.insertRow(model.getRowCount(), new String[]{
                        x.getName(), x.getPrice() + "", x.getTruck().getWeight() + "",
                        x.getTruck().getType().getName(), "Нет оценок" ,x.getAvailability().getName()
                });
            }

        }
        driverTable.setModel(model);
    }
}
