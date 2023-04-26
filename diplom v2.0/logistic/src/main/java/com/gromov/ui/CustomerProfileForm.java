package com.gromov.ui;

import com.gromov.entity.Request;
import com.gromov.entity.User;
import com.gromov.service.DAO.RequestDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerProfileForm {
    private static JFrame customerProfileForm = new JFrame();
    private static User user;
    private JPanel customerProfilePanel;
    private JTable requestTable;
    private JLabel queryLabel;
    private JLabel requestLabel;
    private JButton updateTableButton;
    private JButton makeRequestButton;
    private JButton backButton;
    private JScrollPane tableScrollPane;
    private JPanel backButtonPanel;
    private JPanel requestLabelPanel;
    private JPanel requestButtonLabel;
    private JPanel tableScrollPanePanel;
    private JButton commentButton;

    public CustomerProfileForm(User user) {
        this.user = user;
        customerProfileForm.setContentPane(customerProfilePanel);
        customerProfileForm.setTitle("Профиль");
        customerProfileForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerProfileForm.setVisible(true);
        customerProfileForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        customerProfileForm.setBounds(dimension.width/2-275,dimension.height/2-150,550,300);

        createQueryTable();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseForm();
                customerProfileForm.dispose();
            }
        });
        makeRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MakeRequestForm(user);
                customerProfileForm.dispose();
            }
        });
        updateTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createQueryTable();
            }
        });
        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MakeCommentForm(user);
                customerProfileForm.dispose();
            }
        });
    }
    public static void setUser(User user) {
        CustomerProfileForm.user = user;
    }

    public static JFrame getCustomerProfileForm() {
        return customerProfileForm;
    }

    public static User getUser() {
        return user;
    }

    public JPanel getCustomerProfilePanel() {
        return customerProfilePanel;
    }


    public JTable getOrderTable() {
        return requestTable;
    }

    public JLabel getQueryLabel() {
        return queryLabel;
    }

    public JLabel getOrderLabel() {
        return requestLabel;
    }

    public JButton getUpdateTablesButton() {
        return updateTableButton;
    }

    public JButton getMakeRequestButton() {
        return makeRequestButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
    private void createQueryTable() {
        List<Request> requests = RequestDAO.getListOfRequestsByUserID(user);
        DefaultTableModel model = new DefaultTableModel(null,new String[] {
                "Наименование","Дата отправки","Дата доставки","Статус"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for(Request x : requests) {
            model.insertRow(model.getRowCount(),new String[] {
                    x.getCargo().getName(),x.getDateSending().toString(),
                    x.getDateDelivery().toString(),x.getStatus().getName()
            });
        }
        requestTable.setModel(model);
    }
}
