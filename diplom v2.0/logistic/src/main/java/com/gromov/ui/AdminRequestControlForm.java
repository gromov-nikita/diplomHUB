package com.gromov.ui;

import com.gromov.entity.Driver;
import com.gromov.entity.Request;
import com.gromov.entity.User;
import com.gromov.entity.enums.RequestStatus;
import com.gromov.service.DAO.DriverDAO;
import com.gromov.service.DAO.RequestDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminRequestControlForm {
    private static JFrame adminRequestControlForm = new JFrame();
    private static User user;
    private JPanel adminRequestControlPanel;
    private JComboBox requestCombo;
    private JLabel requestLabel;
    private JLabel driverLabel;
    private JComboBox driverCombo;
    private JPanel backPanel;
    private JButton backButton;
    private JPanel buttonsPanel;
    private JButton cancelRequestButton;
    public AdminRequestControlForm(User user) {
        adminRequestControlForm.setAlwaysOnTop(true);
        this.user = user;
        adminRequestControlForm.setContentPane(adminRequestControlPanel);
        adminRequestControlForm.setTitle("Управление запросами");
        adminRequestControlForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminRequestControlForm.setVisible(true);
        adminRequestControlForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        adminRequestControlForm.setBounds(dimension.width/2-500,dimension.height/2-75,1000,150);
        fillRequestCombo();
        fillDriverCombo();
        driverCombo.setMaximumRowCount(10);
        requestCombo.setMaximumRowCount(10);
        requestCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillDriverCombo();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminProfileForm(user);
                adminRequestControlForm.dispose();
            }
        });
        cancelRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request request = (Request) requestCombo.getSelectedItem();
                request.setStatus(RequestStatus.CANCELED);
                RequestDAO.updateRequest(request);
                fillRequestCombo();
            }
        });
    }
    public void fillDriverCombo() {
        driverCombo.removeAllItems();
        if (requestCombo.getSelectedItem() != null) {
            for (Driver x : DriverDAO.getListOfDriversByCargo((
                    (Request) requestCombo.getSelectedItem()).getCargo())) {
                driverCombo.addItem(x);
            }
        }
    }
    public void fillRequestCombo() {
        requestCombo.removeAllItems();
        for(Request x : RequestDAO.getListOfWrongRequests()) {
            requestCombo.addItem(x);
        }
    }
}
