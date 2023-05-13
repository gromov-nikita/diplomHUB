package com.gromov.ui;

import com.gromov.entity.OrderHistory;
import com.gromov.entity.User;
import com.gromov.entity.enums.OrderStatus;
import com.gromov.entity.enums.Rating;
import com.gromov.service.DAO.CommentDAO;
import com.gromov.service.DAO.OrderHistoryDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MakeCommentForm {
    private static JFrame makeCommentForm = new JFrame();
    private static User user;
    private Rating rating;
    private JPanel makeCommentPanel;
    private JButton backButton;
    private JButton makeCommentButton;
    private JRadioButton oneRadio;
    private JRadioButton twoRadio;
    private JComboBox orderCombo;
    private JRadioButton threeRadio;
    private JRadioButton fourRadio;
    private JRadioButton fiveRadio;
    private JLabel orderLabel;
    private JLabel markLabel;
    private JLabel textLabel;
    private JPanel radioPanel;
    private JScrollPane commentPane;
    private JTextArea comment;
    private JPanel buttonPanel;
    private ButtonGroup radioButtonGroup;

    public MakeCommentForm(User user) {
        this.user = user;
        makeCommentForm.setContentPane(makeCommentPanel);
        makeCommentForm.setTitle("Отзыв");
        makeCommentForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeCommentForm.setVisible(true);
        makeCommentForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        orderCombo.setMaximumRowCount(10);
        makeCommentForm.setBounds(dimension.width/2-(dimension.width-100)/2,dimension.height/2-150, dimension.width-100,300);
        for(OrderHistory x : OrderHistoryDAO.getListOfOrdersByUserAndStatus(OrderStatus.COMPLETED,user)) {
            orderCombo.addItem(x);
        }
        setRadioComment((OrderHistory) orderCombo.getSelectedItem());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerProfileForm(user);
                makeCommentForm.dispose();
            }
        });
        makeCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderHistory order = (OrderHistory) orderCombo.getSelectedItem();
                order.getComment().setText(comment.getText());
                if(rating==null) {
                    order.getComment().setRating(Rating.NOTHING);
                }
                else {
                    order.getComment().setRating(rating);
                }
                CommentDAO.updateComment(order.getComment());
                JOptionPane.showMessageDialog(makeCommentForm,"Отзыв успешно отправлен." +
                        "\nСпасибо за уделенное время." +
                        "\nВаше мнение очень важно для нас!!!!!","Сообщение",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        orderCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRadioComment((OrderHistory) orderCombo.getSelectedItem());
            }

        });


        oneRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioButtonGroup.clearSelection();
                oneRadio.setSelected(true);
                rating = Rating.ONE;
            }
        });
        twoRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioButtonGroup.clearSelection();
                twoRadio.setSelected(true);
                rating = Rating.TWO;
            }
        });
        threeRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioButtonGroup.clearSelection();
                threeRadio.setSelected(true);
                rating = Rating.THREE;
            }
        });
        fourRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioButtonGroup.clearSelection();
                fourRadio.setSelected(true);
                rating = Rating.FOUR;
            }
        });
        fiveRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioButtonGroup.clearSelection();
                fiveRadio.setSelected(true);
                rating = Rating.FIVE;
            }
        });
    }
    private void setRadioComment(OrderHistory order) {
        radioButtonGroup.clearSelection();
        switch (order.getComment().getRating()) {
            case ONE : {
                oneRadio.setSelected(true);
                rating = Rating.ONE;
                break;
            }
            case TWO: {
                twoRadio.setSelected(true);
                rating = Rating.TWO;
                break;
            }
            case THREE: {
                threeRadio.setSelected(true);
                rating = Rating.THREE;
                break;
            }
            case FOUR: {
                fourRadio.setSelected(true);
                rating = Rating.FOUR;
                break;
            }
            case FIVE: {
                fiveRadio.setSelected(true);
                rating = Rating.FIVE;
                break;
            }
            default : {
                radioButtonGroup.clearSelection();
            }
        }
        comment.setText(order.getComment().getText());

    }

}
