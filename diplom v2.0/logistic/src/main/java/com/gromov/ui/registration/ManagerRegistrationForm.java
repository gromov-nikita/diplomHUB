package com.gromov.ui.registration;

import com.gromov.entity.User;
import com.gromov.entity.enums.UserType;
import com.gromov.service.DAO.UserDAO;
import com.gromov.ui.AdminProfileForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ManagerRegistrationForm extends RegistrationForm {
    private static User user;
    public ManagerRegistrationForm(User user) {
        super();
        this.user = user;
        getRegistrationForm().setTitle("Регистрация менеджера");
        getRegistrationButton().setText("Зарегистрировать");
        getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminProfileForm(user);
                getRegistrationForm().dispose();
            }
        });
        getRegistrationButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User user = new User();
                    user.setName(getNameField().getText());
                    user.setEmail(getEmailField().getText());
                    user.setPassword(getPasswordField().getText());
                    user.setType(UserType.MANAGER);
                    UserDAO.signUp(user);
                    JOptionPane.showMessageDialog(getRegistrationForm(),
                            "Менеджер успешно создан","Сообщение",JOptionPane.INFORMATION_MESSAGE);
                    new AdminProfileForm(user);
                    getRegistrationForm().dispose();
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(getRegistrationForm(),
                            "Введенный вами пользователь уже существует","Сообщение",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
