package com.gromov.ui.infoTables;

import com.gromov.entity.Driver;
import com.gromov.entity.User;
import com.gromov.entity.enums.*;
import com.gromov.service.DAO.DriverDAO;
import com.gromov.service.DAO.UserDAO;
import com.gromov.service.dataExport.ExcelAdapter;
import com.gromov.ui.AdminInfoForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TableManagerForm {
    private static JFrame tableManagerForm = new JFrame();
    private static User user;
    private JPanel tableManagerPanel;
    private JLabel managerLabel;
    private JPanel buttonPanel;
    private JPanel tablePanel;
    private JTable managerTable;
    private JTextField findField;
    private JComboBox findCombo;
    private JComboBox ratingCombo;
    private JButton backButton;
    private JButton updateButton;
    private JButton excelExportButton;
    private JLabel findLabel;
    private JScrollPane tablePane;
    private JButton findButton;
    private JPanel findPanel;

    public TableManagerForm(User user) {
        tableManagerForm.setAlwaysOnTop(true);
        this.user = user;
        tableManagerForm.setContentPane(tableManagerPanel);
        tableManagerForm.setTitle("Справочная информация");
        tableManagerForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableManagerForm.setVisible(true);
        tableManagerForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        tableManagerForm.setBounds(dimension.width / 2 - 500, dimension.height / 2 - 200, 1000, 300);
        createManagerTable();
        findCombo.setMaximumRowCount(10);
        ratingCombo.setMaximumRowCount(10);
        findCombo.addItem(FindSystem.NAME.getName());
        findCombo.addItem(FindSystem.EMAIL.getName());
        findCombo.addItem(FindSystem.DRIVER_AMOUNT.getName());
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
                new AdminInfoForm(user);
                tableManagerForm.dispose();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createManagerTable();
            }
        });
        excelExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = new Date().toString();
                date = date.replaceAll(":","-");
                ExcelAdapter.excelExport(managerTable, "Менеджеры "
                        + date, "Менеджеры");
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createManagerTableBy();
            }
        });
    }
    private void createManagerTable() {
        List<User> managers = null;
        managers = UserDAO.getListOfUsersByType(UserType.MANAGER);
        DefaultTableModel model = new DefaultTableModel(null,new String[] {
                "Ф.И.О.","Email","Кол-во водителей","Средняя оценка"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        float rating;
        int driverAmount;
        for(User x : managers) {
            float saveSMTH;
            rating = 0;
            driverAmount = x.getDrivers().size();
            rating = getManagerRating(x);
            if(((int)rating) != -1) {
                model.insertRow(model.getRowCount(), new String[]{
                        x.getName(), x.getEmail(), driverAmount+"",rating+""
                });
            }
            else {
                model.insertRow(model.getRowCount(), new String[]{
                        x.getName(), x.getEmail(), driverAmount+"","Нет оценок"
                });
            }

        }
        managerTable.setModel(model);
    }
    private void createManagerTableBy() {
        List<User> users = null;
        FindSystem findBy;
        if (!findField.getText().isEmpty()) {
            findBy = FindSystem.getFindSystemTypeByName((String) findCombo.getSelectedItem());
            switch (findBy) {
                case NAME: {
                    users = UserDAO.getListOfUsersByNameAndType(findField.getText(),UserType.MANAGER);
                    break;
                }
                case EMAIL: {
                    users = UserDAO.getListOfUsersByEmailAndType(findField.getText(),UserType.MANAGER);
                    break;
                }
                case DRIVER_AMOUNT: {
                    users = UserDAO.getListOfUsersByDriverAmountAndType(
                            Integer.valueOf(findField.getText()),UserType.MANAGER);
                    break;
                }
                default: {
                    users = null;
                }
            }
        } else {
            users = UserDAO.getListOfUsersByType(UserType.MANAGER);
        }
        if(!(FindByRating.getRatingByName((String)ratingCombo.getSelectedItem()).equals(FindByRating.ALL))) {
            List<User> saveUsers = new LinkedList<>();
            switch (FindByRating.getRatingByName((String)ratingCombo.getSelectedItem())) {
                case MORE_TWO : {
                    for(User x : users) {
                        if(getManagerRating(x) >= 2) {
                            saveUsers.add(x);
                        }
                    }
                    break;
                }
                case MORE_THREE : {
                    for(User x : users) {
                        if(getManagerRating(x) >= 3) {
                            saveUsers.add(x);
                        }
                    }
                    break;
                }
                case MORE_FOUR : {
                    for(User x : users) {
                        if(getManagerRating(x) >= 4) {
                            saveUsers.add(x);
                        }
                    }
                    break;
                }
                case LESS_TWO : {
                    for(User x : users) {
                        float rating = getManagerRating(x);
                        if(rating < 2 && (((int)rating) !=-1)) {
                            saveUsers.add(x);
                        }
                    }
                    break;
                }
                case LESS_THREE : {
                    for(User x : users) {
                        float rating = getManagerRating(x);
                        if(rating < 3 && (((int)rating) !=-1)) {
                            saveUsers.add(x);
                        }
                    }
                    break;
                }
                case LESS_FOUR : {
                    for(User x : users) {
                        float rating = getManagerRating(x);
                        if(rating < 4 && (((int)rating) !=-1)) {
                            saveUsers.add(x);
                        }
                    }
                    break;
                }
                case NOTHING : {
                    for(User x : users) {
                        if((((int)getManagerRating(x))) == -1) {
                            saveUsers.add(x);
                        }
                    }
                }
            }
            users = saveUsers;
        }
        DefaultTableModel model = new DefaultTableModel(null,new String[] {
                "Ф.И.О.","Email","Кол-во водителей","Средняя оценка"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        float rating;
        for(User x : users) {
            int driverAmount = x.getDrivers().size();
            rating = getManagerRating(x);
            if(((int)rating) != -1) {
                model.insertRow(model.getRowCount(), new String[]{
                        x.getName(), x.getEmail(), driverAmount+"",rating+""
                });
            }
            else {
                model.insertRow(model.getRowCount(), new String[]{
                        x.getName(), x.getEmail(), driverAmount+"","Нет оценок"
                });
            }
        }
        managerTable.setModel(model);
    }
    private float getManagerRating(User user) {
        float saveSMTH;
        float rating = 0;
        int n = 0;
        for (Driver x : user.getDrivers()) {
            saveSMTH = x.getRating();
            if (saveSMTH != -1) {
                rating += saveSMTH;
                n++;
            }
        }
        if(rating==0) {
            rating = -1;
        }
        return rating/n;
    }
}