package com.gromov.ui;

import com.gromov.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminInfoForm {
    private static JFrame adminInfoForm = new JFrame();
    private static User user;
    private JPanel adminInfoPanel;
    private JPanel buttonPanel;
    private JButton requestButton;
    private JButton driverButton;
    private JButton customerButton;
    private JButton orderButton;
    private JButton ManagerButton;
    private JButton backButton;

    public AdminInfoForm(User user) {
        adminInfoForm.setAlwaysOnTop(true);
        this.user = user;
        adminInfoForm.setContentPane(adminInfoPanel);
        adminInfoForm.setTitle("Информационное меню");
        adminInfoForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminInfoForm.setVisible(true);
        adminInfoForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        adminInfoForm.setBounds(dimension.width / 2 - 125, dimension.height / 2 - 105, 400, 230);
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        driverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminProfileForm(user);
                adminInfoForm.dispose();
            }
        });
    }
}
