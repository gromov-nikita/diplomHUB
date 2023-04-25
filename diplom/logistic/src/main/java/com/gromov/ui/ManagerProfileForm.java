package com.gromov.ui;

import com.gromov.entity.*;
import com.gromov.entity.enums.DriverAvailability;
import com.gromov.entity.enums.OrderStatus;
import com.gromov.entity.enums.RequestStatus;
import com.gromov.service.DAO.DriverDAO;
import com.gromov.service.DAO.OrderHistoryDAO;
import com.gromov.service.DAO.RequestDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerProfileForm {
    private static JFrame managerProfileForm = new JFrame();
    private static User user;
    private JPanel managerProfilePanel;
    private JComboBox driverCombo;
    private JButton cancelButton;
    private JButton acceptButton;
    private JPanel cancelAcceptPanel;
    private JButton backButton;
    private JLabel requestLabel;
    private JLabel driverLabel;
    private JComboBox requestCombo;
    private JButton orderButton;
    private JPanel requestLabelPanel;
    private JPanel driverLabelPanel;
    private JPanel backButtonPanel;
    private JButton requestTableButton;

    public ManagerProfileForm(User user) {
        managerProfileForm.setAlwaysOnTop(true);
        this.user = user;
        managerProfileForm.setContentPane(managerProfilePanel);
        managerProfileForm.setTitle("Профиль");
        managerProfileForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerProfileForm.setVisible(true);
        managerProfileForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        managerProfileForm.setBounds(dimension.width/2-500,dimension.height/2-75,1000,150);
        requestCombo.setMaximumRowCount(10);
        fillRequestCombo();
        driverCombo.setMaximumRowCount(10);
        fillDriverComboByCargo();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseForm();
                managerProfileForm.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request request = (Request) requestCombo.getSelectedItem();
                request.setStatus(RequestStatus.CANCELED);
                RequestDAO.updateStatus(request);
                fillRequestCombo();
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request request = (Request) requestCombo.getSelectedItem();
                Driver driver = (Driver) driverCombo.getSelectedItem();
                OrderHistoryDAO.makeOrder(new OrderHistory(OrderStatus.PROCESSED,
                        request,
                        (Driver)driverCombo.getSelectedItem()));
                request.setStatus(RequestStatus.ACCEPTED);
                driver.setAvailability(DriverAvailability.NOT_AVAILABLE);
                RequestDAO.updateStatus(request);
                DriverDAO.updateStatus(driver);
                fillRequestCombo();
            }
        });

        requestCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillDriverComboByCargo();
            }
        });
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerOrderForm(user);
                managerProfileForm.dispose();
            }
        });
        requestTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableRequestForm(user);
                managerProfileForm.dispose();
            }
        });
    }
    private void fillRequestCombo() {
        requestCombo.removeAllItems();
        for(Request x : RequestDAO.getListOfRequestsByStatus(RequestStatus.IN_PROCESS)) {
            requestCombo.addItem(x);

        }
    }
    private void fillDriverComboByCargo() {
        driverCombo.removeAllItems();
        Request request = (Request)requestCombo.getSelectedItem();
        if(request != null) {
            for (Driver x : DriverDAO.getListOfFreeDriversByCargo(request.getCargo())) {
                driverCombo.addItem(x);
            }
        }

    }
}
