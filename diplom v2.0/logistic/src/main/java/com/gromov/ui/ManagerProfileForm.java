package com.gromov.ui;

import com.gromov.entity.*;
import com.gromov.entity.enums.DriverAvailability;
import com.gromov.entity.enums.OrderStatus;
import com.gromov.entity.enums.RequestStatus;
import com.gromov.entity.enums.WorkStatus;
import com.gromov.service.DAO.DriverDAO;
import com.gromov.service.DAO.OrderHistoryDAO;
import com.gromov.service.DAO.RequestDAO;
import com.gromov.ui.infoTables.TableRequestForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ManagerProfileForm {
    private static JFrame managerProfileForm = new JFrame();
    private static User user;
    private static List<Request> requests = null;
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
    private JButton getRequests;

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
        fillDriverComboByAvailabilityAndUser();
        fillRequestComboByTruck();
        driverCombo.setMaximumRowCount(10);

        managerProfileForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                swapWorkStatus(requests);
                super.windowClosing(e);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapWorkStatus(requests);
                new ChooseForm();
                managerProfileForm.dispose();
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
                RequestDAO.updateRequest(request);
                DriverDAO.updateStatus(driver);
                fillDriverComboByAvailabilityAndUser();
                fillRequestComboByTruck();
            }
        });
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapWorkStatus(requests);
                new ManagerOrderForm(user);
                managerProfileForm.dispose();
            }
        });
        requestTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapWorkStatus(requests);
                new TableRequestForm(user);
                managerProfileForm.dispose();
            }
        });
        getRequests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillRequestComboByTruck();
            }
        });
    }
    private void fillRequestCombo() {
        swapWorkStatus(requests);
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
    private void fillRequestComboByTruck() {
        swapWorkStatus(requests);
        requestCombo.removeAllItems();
        Driver driver = (Driver)driverCombo.getSelectedItem();
        if(driver != null) {
            requests = swapWorkStatus(
                    RequestDAO.getListOfMaxTenFreeRequestsStrongByTruck(driver.getTruck()));
            if(requests!=null && requests.size()!=0) {
                for (Request x : requests) {
                    requestCombo.addItem(x);
                }
            }
            else {
                requests = swapWorkStatus(RequestDAO.getListOfMaxTenFreeRequestsByTruck(driver.getTruck()));
                if(requests!=null && requests.size()!=0) {
                    for (Request x : requests) {
                        requestCombo.addItem(x);
                    }
                }
            }
        }
    }
    private void fillDriverComboByAvailabilityAndUser() {
        driverCombo.removeAllItems();
        for (Driver x : DriverDAO.getListOfFreeDriversByUser(user)) {
            driverCombo.addItem(x);
        }
    }
    private List<Request> swapWorkStatus(List<Request> requests) {
        if(requests!=null) {
            for (Request x : requests) {
                if (x.getWorkStatus().equals(WorkStatus.BOOKED)) {
                    x.setWorkStatus(WorkStatus.FREE);
                    RequestDAO.updateRequest(x);
                } else {
                    x.setWorkStatus(WorkStatus.BOOKED);
                    RequestDAO.updateRequest(x);
                }
            }
        }
        return requests;
    }
}
