package com.gromov.ui;

import com.gromov.entity.User;
import com.gromov.ui.infoTables.TableDriverForm;
import com.gromov.ui.infoTables.TableManagerForm;
import com.gromov.ui.infoTables.TableOrderForm;
import com.gromov.ui.infoTables.TableRequestForm;

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
                new TableRequestForm(user);
                adminInfoForm.dispose();
            }
        });
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableOrderForm(user);
                adminInfoForm.dispose();
            }
        });
        driverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableDriverForm(user);
                adminInfoForm.dispose();
            }
        });
        ManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableManagerForm(user);
                adminInfoForm.dispose();
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
