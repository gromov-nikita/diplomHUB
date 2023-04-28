package com.gromov.ui;

import com.gromov.entity.*;
import com.gromov.entity.enums.DriverAvailability;
import com.gromov.entity.enums.OrderStatus;
import com.gromov.service.DAO.DriverDAO;
import com.gromov.service.DAO.OrderHistoryDAO;
import com.gromov.ui.info.TableDriverForManagerForm;
import com.gromov.ui.info.TableOrderForManagerForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerOrderForm {
    private static JFrame managerOrderForm = new JFrame();
    private static User user;
    private String orderStatus;
    private JPanel managerOrderPanel;
    private JComboBox oldStatusCombo;
    private JComboBox newStatusCombo;
    private JButton getOrderTableButton;
    private JButton updateButton;
    private JComboBox orderCombo;
    private JButton backButton;
    private JLabel oldStatusLabel;
    private JLabel orderLabel;
    private JLabel newStatusLabel;
    private JButton getDriverTable;
    private JPanel mainOrderMenuLabelPanel;
    private JPanel newStatusLabelPanel;
    private JPanel orderLabelPanel;
    private JPanel oldStatusLabelPanel;
    private JPanel backButtonPanel;
    private JPanel oldStatusComboPanel;
    private JPanel orderComboPanel;
    private JPanel newStatusComboPanel;
    private JPanel updateButtonPanel;
    private JPanel getTableButtonPanel;
    private JButton getCommentsButton;

    public ManagerOrderForm(User user) {
        managerOrderForm.setAlwaysOnTop(true);
        this.user = user;
        managerOrderForm.setContentPane(managerOrderPanel);
        managerOrderForm.setTitle("Менеджер заказов");
        managerOrderForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerOrderForm.setVisible(true);
        managerOrderForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        orderCombo.setMaximumRowCount(10);
        oldStatusCombo.setMaximumRowCount(10);
        newStatusCombo.setMaximumRowCount(10);
        managerOrderForm.setBounds(0,dimension.height/2-75,dimension.width,150);
        oldStatusCombo.addItem(OrderStatus.PROCESSED.getName());
        oldStatusCombo.addItem(OrderStatus.SENT.getName());
        oldStatusCombo.addItem(OrderStatus.DELIVERED.getName());
        newStatusCombo.addItem(OrderStatus.CANCELED.getName());
        newStatusCombo.addItem(OrderStatus.PROCESSED.getName());
        newStatusCombo.addItem(OrderStatus.SENT.getName());
        newStatusCombo.addItem(OrderStatus.DELIVERED.getName());
        newStatusCombo.addItem(OrderStatus.COMPLETED.getName());
        orderStatus = (String) oldStatusCombo.getSelectedItem();
        newStatusCombo.removeItem(orderStatus);
        fillOrderCombo(OrderStatus.getOrderStatusByName((String)oldStatusCombo.getSelectedItem()));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerProfileForm(user);
                managerOrderForm.dispose();
            }
        });
        getOrderTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableOrderForManagerForm(user);
                managerOrderForm.dispose();
            }
        });
        getDriverTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableDriverForManagerForm(user);
                managerOrderForm.dispose();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderHistory order = (OrderHistory)orderCombo.getSelectedItem();
                order.setStatus(OrderStatus.getOrderStatusByName((String)newStatusCombo.getSelectedItem()));
                OrderHistoryDAO.updateStatus(order);
                if(!(OrderStatus.COMPLETED.getName().equals((String)newStatusCombo.getSelectedItem())
                        || OrderStatus.CANCELED.getName().equals((String)newStatusCombo.getSelectedItem()))) {
                    order.getDriver().setAvailability(DriverAvailability.NOT_AVAILABLE);
                    DriverDAO.updateStatus(order.getDriver());
                }
                else {
                    order.getDriver().setAvailability(DriverAvailability.AVAILABLE);
                    DriverDAO.updateStatus(order.getDriver());
                }

                fillOrderCombo(OrderStatus.getOrderStatusByName((String)oldStatusCombo.getSelectedItem()));
            }
        });
        oldStatusCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newStatusCombo.addItem(orderStatus);
                orderStatus = (String) oldStatusCombo.getSelectedItem();
                newStatusCombo.removeItem(orderStatus);
                fillOrderCombo(OrderStatus.getOrderStatusByName((String)oldStatusCombo.getSelectedItem()));
            }
        });

        getCommentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CommentManagerForm(user);
                managerOrderForm.dispose();
            }
        });
    }
    private void fillOrderCombo(OrderStatus status) {
        orderCombo.removeAllItems();
        for(OrderHistory x : OrderHistoryDAO.getListOfOrdersByStatusAndManager(status,user)) {
            orderCombo.addItem(x);
        }
    }
}