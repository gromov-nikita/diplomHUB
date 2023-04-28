package com.gromov.ui;

import com.gromov.entity.Driver;
import com.gromov.entity.Truck;
import com.gromov.entity.User;
import com.gromov.entity.enums.CargoType;
import com.gromov.entity.enums.DriverAvailability;
import com.gromov.entity.enums.UserType;
import com.gromov.service.DAO.DriverDAO;
import com.gromov.service.DAO.TruckDAO;
import com.gromov.service.DAO.UserDAO;
import com.gromov.ui.registration.ManagerRegistrationForm;
import jakarta.persistence.NoResultException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DriverCreatorForm {
    private static JFrame driverCreatorForm = new JFrame();
    private static User user;
    private JPanel driverCreatorPanel;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField weightField;
    private JComboBox cargoTypeCombo;
    private JComboBox managerCombo;
    private JPanel backPanel;
    private JButton backButton;
    private JPanel buttonPanel;
    private JButton registrationButton;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel weightLabel;
    private JLabel cargoTypeLabel;
    private JLabel managerLabel;
    public DriverCreatorForm(User user) {
        driverCreatorForm.setAlwaysOnTop(true);
        this.user = user;
        driverCreatorForm.setContentPane(driverCreatorPanel);
        driverCreatorForm.setTitle("Регистрация водителя");
        driverCreatorForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        driverCreatorForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        cargoTypeCombo.setMaximumRowCount(10);
        managerCombo.setMaximumRowCount(10);
        driverCreatorForm.setBounds(dimension.width / 2 - 500, dimension.height / 2 - 110, 1000, 220);
        fillCargoTypeCombo();
        fillManagerCombo();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminProfileForm(user);
                driverCreatorForm.dispose();
            }
        });
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Driver driver = new Driver();
                Truck truck = new Truck();
                truck.setType(CargoType.getCargoTypeByName((String)cargoTypeCombo.getSelectedItem()));
                truck.setWeight(Integer.valueOf(weightField.getText()));
                driver.setTruck(truck);
                driver.setName(nameField.getText());
                driver.setPrice(Integer.valueOf(priceField.getText()));
                driver.setUser((User)managerCombo.getSelectedItem());
                driver.setAvailability(DriverAvailability.AVAILABLE);
                DriverDAO.createDriver(driver);
                JOptionPane.showMessageDialog(driverCreatorForm,"Водитель успешно создан.",
                        "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                new AdminProfileForm(user);
                driverCreatorForm.dispose();
            }
        });
    }
    private void fillManagerCombo() {
            List<User> users = UserDAO.getUserByType(UserType.MANAGER);
            if(users.size()!=0) {
                for(User x : users) {
                    managerCombo.addItem(x);
                }
                driverCreatorForm.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(driverCreatorForm,
                        "Нет менеджера к которому можно зачислить водителя.\n" +
                                "Требуется создать менеджера.\n" +
                                "Вы будете перенаправлены на страницу создания менеджера.",
                        "Сообщение", JOptionPane.ERROR_MESSAGE);
                driverCreatorForm.dispose();
                new ManagerRegistrationForm(user);
            }
    }
    private void fillCargoTypeCombo() {
        for(String x : CargoType.getListOfNames()) {
            cargoTypeCombo.addItem(x);
        }
    }
}

