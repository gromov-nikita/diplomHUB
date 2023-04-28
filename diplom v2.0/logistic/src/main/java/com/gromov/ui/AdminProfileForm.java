package com.gromov.ui;

import com.gromov.entity.User;
import com.gromov.ui.registration.ManagerRegistrationForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminProfileForm {
    private static JFrame adminProfileForm = new JFrame();
    private static User user;
    private JPanel adminProfilePanel;
    private JPanel buttonPanel;
    private JButton addManagerButton;
    private JButton getInfoButton;
    private JButton addDriverButton;
    private JButton fixExceptionsButton;
    private JButton getRequestsButton;
    private JButton backButton;

    public AdminProfileForm(User user) {
        adminProfileForm.setAlwaysOnTop(true);
        this.user = user;
        adminProfileForm.setContentPane(adminProfilePanel);
        adminProfileForm.setTitle("Профиль");
        adminProfileForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminProfileForm.setVisible(true);
        adminProfileForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        adminProfileForm.setBounds(dimension.width/2-150,dimension.height/2-115,300,230);
        addManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerRegistrationForm(user);
                adminProfileForm.dispose();
            }
        });
        addDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DriverCreatorForm(user);
                adminProfileForm.dispose();
            }
        });
        getInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminProfileForm.dispose();
            }
        });
        getRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminRequestControlForm(user);
                adminProfileForm.dispose();
            }
        });
        fixExceptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FixExceptionsForm(user);
                adminProfileForm.dispose();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseForm();
                adminProfileForm.dispose();
            }
        });
    }

}
