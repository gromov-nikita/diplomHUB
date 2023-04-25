package com.gromov.ui;

import com.gromov.entity.User;
import com.gromov.entity.enums.UserType;
import com.gromov.service.DAO.UserDAO;
import org.hibernate.exception.ConstraintViolationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegistrationForm {
    private static JFrame registrationForm = new JFrame();
    private JPanel panel1;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JButton registrationButton;
    private JButton backButton;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JPanel registrationPanel;
    private JPanel nameLabelPanel;
    private JPanel emailLabelPanel;
    private JPanel passwordLabelPanel;
    private JPanel backButtonPanel;
    private JPanel registrationButtonPanel;

    public RegistrationForm() {
        registrationForm.setContentPane(registrationPanel);
        registrationForm.setTitle("Регистрация");
        registrationForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrationForm.setVisible(true);
        registrationForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        registrationForm.setBounds(dimension.width/2-160,dimension.height/2-100,320,180);
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User user = new User();
                    user.setName(nameField.getText());
                    user.setEmail(emailField.getText());
                    user.setPassword(passwordField.getText());
                    user.setType(UserType.CUSTOMER);
                    UserDAO.signUp(user);
                    new CustomerProfileForm(user);
                    registrationForm.dispose();
                }
                catch (ConstraintViolationException ex) {
                    JOptionPane.showMessageDialog(registrationForm,
                            "Введенный вами пользователь уже существует","Сообщение",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseForm();
                registrationForm.dispose();

            }
        });
    }

    public static JFrame getRegistrationForm() {
        return registrationForm;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JButton getSendLabel() {
        return registrationButton;
    }

    public JButton getBackLabel() {
        return backButton;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getEmailLabel() {
        return emailLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public JPanel getRegistrationPanel() {
        return registrationPanel;
    }
}
