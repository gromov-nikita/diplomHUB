package com.gromov.ui;

import com.gromov.entity.Driver;
import com.gromov.entity.OrderHistory;
import com.gromov.entity.User;
import com.gromov.entity.enums.OrderStatus;
import com.gromov.entity.enums.Rating;
import com.gromov.entity.enums.UserType;
import com.gromov.service.DAO.DriverDAO;
import com.gromov.service.DAO.OrderHistoryDAO;
import com.gromov.service.DAO.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CommentForm {
    private static JFrame commentManagerForm = new JFrame();
    private static User user;
    private JPanel comentPanel;
    private JButton backButton;
    private JTextArea comment;
    private JComboBox combo;
    private JComboBox orderCombo;
    private JLabel label;
    private JLabel orderLabel;
    private JScrollPane commentPane;
    public CommentForm(User user) {
        commentManagerForm.setAlwaysOnTop(true);
        this.user = user;
        commentManagerForm.setContentPane(comentPanel);
        commentManagerForm.setTitle("Отзывы");
        commentManagerForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        commentManagerForm.setVisible(true);
        commentManagerForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        orderCombo.setMaximumRowCount(10);
        combo.setMaximumRowCount(10);
        orderCombo.setMaximumRowCount(10);
        comment.setEditable(false);
        commentManagerForm.setBounds(dimension.width-(dimension.width - 100/2),dimension.height/2-175, dimension.width - 100,350);
        fillCombo();
        fillOrderCombo();
        fillTextArea();
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillOrderCombo();
            }
        });
        orderCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillTextArea();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(user.getType().equals(UserType.MANAGER)) {
                    new ManagerOrderForm(user);
                } else if (user.getType().equals(UserType.ADMIN)) {
                    new AdminInfoForm(user);
                }
                commentManagerForm.dispose();
            }
        });
    }
    private void fillCombo() {
        combo.removeAllItems();
        if(user.getType().equals(UserType.MANAGER)) {
            label.setText("Водители:");
            for (Driver x : DriverDAO.getListOfDriversByUser(user)) {
                combo.addItem(x);
            }
        }
        else if(user.getType().equals(UserType.ADMIN)) {
            label.setText("Менеджеры:");
            for (User x : UserDAO.getListOfUsersByType(UserType.MANAGER)) {
                combo.addItem(x);
            }
        }
    }
    private void fillOrderCombo() {
        orderCombo.removeAllItems();
        List<OrderHistory> orders = null;
        if(combo.getSelectedItem()!=null) {
            if (user.getType().equals(UserType.MANAGER)) {
                orders = OrderHistoryDAO.getListOfOrdersByStatusAndDriver(OrderStatus.COMPLETED,
                        (Driver) combo.getSelectedItem());
                if (orders != null) {
                    for (OrderHistory x : orders) {
                        orderCombo.addItem(x);
                    }
                }
            } else if (user.getType().equals(UserType.ADMIN)) {
                orders = OrderHistoryDAO.getListOfOrdersByStatusAndManager(OrderStatus.COMPLETED,
                        (User) combo.getSelectedItem());
                if (orders != null) {
                    for (OrderHistory x : orders) {
                        orderCombo.addItem(x);
                    }
                }
            }
        }
    }
    private void fillTextArea() {
        comment.setText("");

        if(orderCombo.getSelectedItem()!=null) {
            if (!((OrderHistory) orderCombo.getSelectedItem()).getComment().getRating().equals(Rating.NOTHING)) {
                comment.setText("Оценка: ");
                comment.append(((OrderHistory) orderCombo.getSelectedItem()).getComment().getRating().getName() + "\n\n");
                comment.append("Отзыв: \n");
                comment.append(((OrderHistory) orderCombo.getSelectedItem()).getComment().getText() + "\n\n");
            }
            else if (!((OrderHistory) orderCombo.getSelectedItem()).getComment().getText().isEmpty()) {
                comment.setText("Оценка: ");
                comment.append("Отсуствует\n\n");
                comment.append("Отзыв: \n");
                comment.append(((OrderHistory) orderCombo.getSelectedItem()).getComment().getText() + "\n\n");
            }
            else {
                comment.setText("Отзыв отсуствует");
            }
        }
    }
}
