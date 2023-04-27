package com.gromov.ui;

import com.gromov.entity.User;
import com.gromov.entity.enums.UserType;
import com.gromov.service.DAO.UserDAO;
import jakarta.persistence.NoResultException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutorizationForm {
    private static JFrame autorizationForm = new JFrame();
    private JPanel autorizationPanel;
    private JTextField emailField;
    private JTextField passwordField;
    private JButton autorizationButton;
    private JButton backButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JPanel backButtonPanel;
    private JPanel emailLabelPanel;
    private JPanel passwordLabelPanel;
    private JPanel autorizationButtonPanel;

    public AutorizationForm() {
        autorizationForm.setContentPane(autorizationPanel);
        autorizationForm.setTitle("Авторизация");
        autorizationForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        autorizationForm.setVisible(true);
        autorizationForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        autorizationForm.setBounds(dimension.width/2-150,dimension.height/2-77,300,154);
        autorizationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User user = UserDAO.logIn(emailField.getText(), passwordField.getText());
                    if(user.getType().equals(UserType.CUSTOMER)) {
                        new CustomerProfileForm(user);
                    }
                    else if (user.getType().equals(UserType.MANAGER)) {
                        new ManagerProfileForm(user);
                    }
                    else if (user.getType().equals(UserType.ADMIN)) {
                        new AdminProfileForm(user);
                    }
                    autorizationForm.dispose();
                }
                catch (NoResultException ex) {
                    JOptionPane.showMessageDialog(autorizationForm,"Пользователь не найден","Сообщение",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseForm();
                autorizationForm.dispose();
            }
        });
    }
}
