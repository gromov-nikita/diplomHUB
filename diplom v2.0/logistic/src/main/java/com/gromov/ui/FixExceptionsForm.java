package com.gromov.ui;

import com.gromov.entity.User;
import com.gromov.service.DAO.RequestDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FixExceptionsForm {
    private static JFrame fixExceptionsForm = new JFrame();
    private static User user;
    private JPanel fixExceptionsPanel;
    private JPanel buttonPanel;
    private JButton freeRequestsButton;
    private JButton forgotPasswordButton;
    private JButton backButton;
    public FixExceptionsForm(User user) {
        fixExceptionsForm.setAlwaysOnTop(true);
        this.user = user;
        fixExceptionsForm.setContentPane(fixExceptionsPanel);
        fixExceptionsForm.setTitle("Ошибки");
        fixExceptionsForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fixExceptionsForm.setVisible(true);
        fixExceptionsForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        fixExceptionsForm.setBounds(dimension.width/2-150,dimension.height/2-70,300,140);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminProfileForm(user);
                fixExceptionsForm.dispose();
            }
        });
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ForgotPasswordForm(user);
                fixExceptionsForm.dispose();
            }
        });
        freeRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestDAO.makeFreeRequests();
            }
        });
    }
}
