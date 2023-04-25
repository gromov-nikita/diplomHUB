package com.gromov.ui;

import com.gromov.entity.Driver;
import com.gromov.entity.OrderHistory;
import com.gromov.entity.User;
import com.gromov.entity.enums.Rating;
import com.gromov.service.DAO.DriverDAO;
import com.gromov.service.DAO.OrderHistoryDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommentManagerForm {
    private static JFrame commentManagerForm = new JFrame();
    private static User user;
    private JPanel comentPanel;
    private JButton backButton;
    private JTextArea comment;
    private JComboBox driverCombo;
    private JComboBox orderCombo;
    private JLabel driverLabel;
    private JLabel orderLabel;
    private JScrollPane commentPane;
    public CommentManagerForm(User user) {


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
        driverCombo.setMaximumRowCount(10);
        orderCombo.setMaximumRowCount(10);
        comment.setEditable(false);
        commentManagerForm.setBounds(dimension.width-(dimension.width - 100/2),dimension.height/2-175, dimension.width - 100,350);
        for(Driver x : DriverDAO.getListOfDrivers()) {
            driverCombo.addItem(x);
        }
        for(OrderHistory x : OrderHistoryDAO.getListOfOrdersByDriver((Driver)driverCombo.getSelectedItem())) {
            orderCombo.addItem(x);
        }
        if(orderCombo.getSelectedItem()!=null) {
            if(!((OrderHistory)orderCombo.getSelectedItem()).getComment().getRating().equals(Rating.NOTHING)) {
                comment.setText("Оценка: ");
                comment.append(((OrderHistory) orderCombo.getSelectedItem()).getComment().getRating().getName() + "\n\n");
                comment.append("Отзыв: \n");
                comment.append(((OrderHistory) orderCombo.getSelectedItem()).getComment().getText() + "\n\n");
            }
            else {
                comment.setText("Отзыв отсуствует");
            }
        }

        driverCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderCombo.removeAllItems();
                for(OrderHistory x : OrderHistoryDAO.getListOfOrdersByDriver((Driver)driverCombo.getSelectedItem())) {
                    orderCombo.addItem(x);
                }
            }
        });
        orderCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comment.setText("");
                if(orderCombo.getSelectedItem()!=null) {
                    if (!((OrderHistory) orderCombo.getSelectedItem()).getComment().getRating().equals(Rating.NOTHING)) {
                        comment.setText("Оценка: ");
                        comment.append(((OrderHistory) orderCombo.getSelectedItem()).getComment().getRating().getName() + "\n\n");
                        comment.append("Отзыв: \n");
                        comment.append(((OrderHistory) orderCombo.getSelectedItem()).getComment().getText() + "\n\n");
                    } else {
                        comment.setText("Отзыв отсуствует");
                    }
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerOrderForm(user);
                commentManagerForm.dispose();
            }
        });
    }
}
