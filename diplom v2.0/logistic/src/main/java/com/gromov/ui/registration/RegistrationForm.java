package com.gromov.ui.registration;

import com.gromov.entity.User;
import com.gromov.entity.enums.UserType;
import com.gromov.service.DAO.UserDAO;
import com.gromov.ui.ChooseForm;
import com.gromov.ui.CustomerProfileForm;
import org.hibernate.exception.ConstraintViolationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLIntegrityConstraintViolationException;

public abstract class RegistrationForm {
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

    public JButton getRegistrationButton() {
        return registrationButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JPanel getNameLabelPanel() {
        return nameLabelPanel;
    }

    public JPanel getEmailLabelPanel() {
        return emailLabelPanel;
    }

    public JPanel getPasswordLabelPanel() {
        return passwordLabelPanel;
    }

    public JPanel getBackButtonPanel() {
        return backButtonPanel;
    }

    public JPanel getRegistrationButtonPanel() {
        return registrationButtonPanel;
    }
}
