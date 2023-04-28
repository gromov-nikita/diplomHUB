package com.gromov.ui;

import com.gromov.entity.User;
import com.gromov.entity.enums.UserType;
import com.gromov.service.DAO.UserDAO;
import jakarta.persistence.NoResultException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPasswordForm {
    private static JFrame forgotPasswordForm = new JFrame();
    private static User user;
    private JPanel forgotPasswordPanel;
    private JTextField emailField;
    private JLabel emaiLabel;
    private JPanel emailLabelPanel;
    private JPanel buttonsPanel;
    private JButton forgotButton;
    private JButton backButton;
    private JPanel backPanel;

    public ForgotPasswordForm(User user) {
        forgotPasswordForm.setAlwaysOnTop(true);
        this.user = user;
        forgotPasswordForm.setContentPane(forgotPasswordPanel);
        forgotPasswordForm.setTitle("Восстановление пароля");
        forgotPasswordForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        forgotPasswordForm.setVisible(true);
        forgotPasswordForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        forgotPasswordForm.setBounds(dimension.width / 2 - 250, dimension.height / 2 - 55, 500, 110);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FixExceptionsForm(user);
                forgotPasswordForm.dispose();
            }
        });
        forgotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User staff = null;
                try {
                    staff = UserDAO.getUserByEmail(emailField.getText());
                    if (staff.getType().equals(UserType.CUSTOMER)) {
                        JOptionPane.showMessageDialog(forgotPasswordForm,
                                "Несанкионированный доступ к информации!!!!!"
                                ,"Сообщение",JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(forgotPasswordForm,
                                "Данные работника:\nEmail: " + staff.getEmail() +
                                        "\nПароль: "+ staff.getPassword(),
                                "Сообщение",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch (NoResultException ex) {
                    JOptionPane.showMessageDialog(forgotPasswordForm,
                            "Пользователь не найден","Сообщение",JOptionPane.WARNING_MESSAGE);
                }

            }
        });
    }
}
