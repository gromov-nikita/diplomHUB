package com.gromov.ui.registration;

import com.gromov.entity.User;
import com.gromov.entity.enums.UserType;
import com.gromov.service.DAO.UserDAO;
import com.gromov.ui.AdminProfileForm;
import com.gromov.ui.ChooseForm;
import com.gromov.ui.CustomerProfileForm;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CustomerRegistrationForm extends RegistrationForm {
    public CustomerRegistrationForm() {
        super();
        getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseForm();
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
                    user.setType(UserType.CUSTOMER);
                    UserDAO.signUp(user);
                    new CustomerProfileForm(user);
                    getRegistrationForm().dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(getRegistrationForm(),
                            "Введенный вами пользователь уже существует","Сообщение",JOptionPane.WARNING_MESSAGE);

                }
            }
        });
    }
}
