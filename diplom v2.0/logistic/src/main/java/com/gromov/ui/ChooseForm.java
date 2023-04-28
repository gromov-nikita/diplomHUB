package com.gromov.ui;

import com.gromov.ui.registration.CustomerRegistrationForm;
import com.gromov.ui.registration.RegistrationForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseForm  {
    private static JFrame chooseForm = new JFrame();
    private JPanel choosePanel;
    private JButton registrationButton;
    private JButton autorizationButton;
    public ChooseForm() {
        chooseForm.setContentPane(choosePanel);
        chooseForm.setTitle("Стартовое меню");
        chooseForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooseForm.setVisible(true);
        chooseForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        chooseForm.setBounds(dimension.width/2-150,dimension.height/2-60,300,120);
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerRegistrationForm();
                chooseForm.dispose();
            }
        });
        autorizationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AutorizationForm();
                chooseForm.dispose();
            }
        });
    }

    public static JFrame getChooseForm() {
        return chooseForm;
    }

    public JPanel getChoosePanel() {
        return choosePanel;
    }

    public JButton getRegistrationButton() {
        return registrationButton;
    }

    public JButton getAutorizationButton() {
        return autorizationButton;
    }
}
