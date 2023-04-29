package com.gromov.ui.infoTables;

import com.gromov.entity.OrderHistory;
import com.gromov.entity.User;
import com.gromov.entity.enums.CargoType;
import com.gromov.entity.enums.FindSystem;
import com.gromov.entity.enums.OrderStatus;
import com.gromov.entity.enums.UserType;
import com.gromov.service.DAO.OrderHistoryDAO;
import com.gromov.service.dataExport.ExcelAdapter;
import com.gromov.ui.AdminInfoForm;
import com.gromov.ui.AdminProfileForm;
import com.gromov.ui.ManagerOrderForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TableOrderForm {
    private static JFrame tableOrderForm = new JFrame();
    private static User user;
    private JPanel tableOrderPanel;
    private JPanel orderLabelPanel;
    private JLabel orderLabel;
    private JPanel orderTablePanePanel;
    private JTable orderTable;
    private JScrollPane orderTablePane;
    private JPanel buttonPanel;
    private JButton backButton;
    private JButton updateTableButton;
    private JPanel findPanel;
    private JTextField findField;
    private JLabel findLabel;
    private JComboBox underloadCombo;
    private JComboBox orderStatusCombo;
    private JButton findButton;
    private JButton excelExportButton;
    private JComboBox findCombo;

    public TableOrderForm(User user) {
        tableOrderForm.setAlwaysOnTop(true);
        this.user = user;
        tableOrderForm.setContentPane(tableOrderPanel);
        tableOrderForm.setTitle("Справочная информация");
        tableOrderForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableOrderForm.setVisible(true);
        tableOrderForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        tableOrderForm.setBounds(0,dimension.height/2-200,dimension.width,400);
        orderStatusCombo.setMaximumRowCount(5);
        underloadCombo.setMaximumRowCount(5);
        findCombo.setMaximumRowCount(5);
        createOrderTable();
        findCombo.addItem(FindSystem.CUSTOMER_EMAIL.getName());
        findCombo.addItem(FindSystem.DRIVER_NAME.getName());
        findCombo.addItem(FindSystem.LESS_PRICE.getName());
        findCombo.addItem(FindSystem.MAX_WEIGHT.getName());
        findCombo.addItem(FindSystem.CARGO_WEIGHT.getName());
        findCombo.addItem(FindSystem.CARGO_TYPE.getName());
        underloadCombo.addItem(FindSystem.ALL.getName());
        underloadCombo.addItem(FindSystem.UNDERLOAD_TRUE.getName());
        underloadCombo.addItem(FindSystem.UNDERLOAD_FALSE.getName());
        orderStatusCombo.addItem(OrderStatus.ALL.getName());
        orderStatusCombo.addItem(OrderStatus.PROCESSED.getName());
        orderStatusCombo.addItem(OrderStatus.SENT.getName());
        orderStatusCombo.addItem(OrderStatus.DELIVERED.getName());
        orderStatusCombo.addItem(OrderStatus.COMPLETED.getName());
        orderStatusCombo.addItem(OrderStatus.CANCELED.getName());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.getType().equals(UserType.MANAGER)) {
                    new ManagerOrderForm(user);
                }
                else if (user.getType().equals(UserType.ADMIN)) {
                    new AdminInfoForm(user);
                }
                tableOrderForm.dispose();
            }
        });
        updateTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrderTable();
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrderTableBy();
            }
        });
        excelExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = new Date().toString();
                date = date.replaceAll(":","-");
                ExcelAdapter.excelExport(orderTable, "Заказы "
                        + date, "Заказы");
            }


        });
    }
    private void createOrderTableBy() {
        List<OrderHistory> orders = null;
        FindSystem findBy;
        if (user.getType().equals(UserType.MANAGER)) {
            if (!findField.getText().isEmpty()) {
                findBy = FindSystem.getFindSystemTypeByName((String) findCombo.getSelectedItem());
                switch (findBy) {
                    case CUSTOMER_EMAIL: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndCustomerEmailAndManager(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                findField.getText(), user);
                        break;
                    }
                    case DRIVER_NAME: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndDriverNameAndManager(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                findField.getText(), user);
                        break;
                    }
                    case LESS_PRICE: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndLessPriceAndManager(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                Integer.valueOf(findField.getText()), user);
                        break;
                    }
                    case MAX_WEIGHT: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndMaxWeightAndManager(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                Integer.valueOf(findField.getText()), user);
                        break;
                    }
                    case CARGO_WEIGHT: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndCargoWeightAndManager(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                Integer.valueOf(findField.getText()), user);
                        break;
                    }
                    case CARGO_TYPE: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndCargoTypeAndManager(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                CargoType.getCargoTypeByName(findField.getText()), user);
                        break;
                    }
                    default: {
                        orders = null;
                    }
                }
            } else {
                orders = OrderHistoryDAO.getListOfOrdersByStatusAndManager(OrderStatus.getOrderStatusByName(
                        (String) orderStatusCombo.getSelectedItem()), user);

            }
        }
        else if (user.getType().equals(UserType.ADMIN)) {
            if (!findField.getText().isEmpty()) {
                findBy = FindSystem.getFindSystemTypeByName((String) findCombo.getSelectedItem());
                switch (findBy) {
                    case CUSTOMER_EMAIL: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndCustomerEmail(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                findField.getText());
                        break;
                    }
                    case DRIVER_NAME: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndDriverName(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                findField.getText());
                        break;
                    }
                    case LESS_PRICE: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndLessPrice(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                Integer.valueOf(findField.getText()));
                        break;
                    }
                    case MAX_WEIGHT: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndMaxWeight(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                Integer.valueOf(findField.getText()));
                        break;
                    }
                    case CARGO_WEIGHT: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndCargoWeight(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                Integer.valueOf(findField.getText()));
                        break;
                    }
                    case CARGO_TYPE: {
                        orders = OrderHistoryDAO.getListOfOrdersByStatusAndCargoType(
                                OrderStatus.getOrderStatusByName((String) orderStatusCombo.getSelectedItem()),
                                CargoType.getCargoTypeByName(findField.getText()));
                        break;
                    }
                    default: {
                        orders = null;
                    }
                }
            } else {
                orders = OrderHistoryDAO.getListOfOrdersByStatus(OrderStatus.getOrderStatusByName(
                        (String) orderStatusCombo.getSelectedItem()));

            }
        }
        List<OrderHistory> saveOrders = new LinkedList<>();
        FindSystem underload = FindSystem.getFindSystemTypeByName((String)underloadCombo.getSelectedItem());
        if(underload.equals(FindSystem.UNDERLOAD_TRUE)) {
            for(OrderHistory x : orders) {
                if(x.getUnderload() != 0) {
                    saveOrders.add(x);
                }
            }
            orders = saveOrders;
        }
        else {
            if (underload.equals(FindSystem.UNDERLOAD_FALSE)) {
                for (OrderHistory x : orders) {
                    if (x.getUnderload() == 0) {
                        saveOrders.add(x);
                    }
                }
                orders = saveOrders;
            }
        }

        DefaultTableModel model = new DefaultTableModel(null,new String[] {
                "Email заказчика","Ф.И.О. заказчика","Дата отправки",
                "Дата доставки","Откуда","Куда","Название груза",
                "Масса груза(кг)","Ф.И.О. водителя","Цена(BYN/км)",
                "Грузоподъемность(кг)","Тип груза","Недогруз(кг)","Статус заказа",
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for(OrderHistory x : orders) {
            model.insertRow(model.getRowCount(),new String[] {
                    x.getRequest().getUser().getEmail(),x.getRequest().getUser().getName(),
                    x.getRequest().getDateSending().toString(), x.getRequest().getDateDelivery().toString(),
                    x.getRequest().getFrom().getName(),x.getRequest().getTo().getName(),
                    x.getRequest().getCargo().getName(),x.getRequest().getCargo().getWeight()+"",
                    x.getDriver().getName(), x.getDriver().getPrice()+"",x.getDriver().getTruck().getWeight()+"",
                    x.getDriver().getTruck().getType().getName(),
                    x.getUnderload()+"",x.getStatus().getName()
            });
        }
        orderTable.setModel(model);

    }
    private void createOrderTable() {
        List<OrderHistory> orders = null;
        if (user.getType().equals(UserType.MANAGER)) {
            orders = OrderHistoryDAO.getListOfOrdersByUser(user);
        }
        else if(user.getType().equals(UserType.ADMIN)) {
            orders = OrderHistoryDAO.getListOfOrders();
        }
        DefaultTableModel model = new DefaultTableModel(null,new String[] {
                "Email заказчика","Ф.И.О. заказчика","Дата отправки",
                "Дата доставки","Откуда","Куда","Название груза",
                "Масса груза(кг)","Ф.И.О. водителя","Цена(BYN/км)",
                "Грузоподъемность(кг)","Тип груза","Недогруз(кг)","Статус заказа",
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for(OrderHistory x : orders) {
            model.insertRow(model.getRowCount(),new String[] {
                    x.getRequest().getUser().getEmail(),x.getRequest().getUser().getName(),
                    x.getRequest().getDateSending().toString(), x.getRequest().getDateDelivery().toString(),
                    x.getRequest().getFrom().getName(),x.getRequest().getTo().getName(),
                    x.getRequest().getCargo().getName(),x.getRequest().getCargo().getWeight()+"",
                    x.getDriver().getName(), x.getDriver().getPrice()+"",x.getDriver().getTruck().getWeight()+"",
                    x.getDriver().getTruck().getType().getName(),
                    x.getUnderload()+"",x.getStatus().getName()
            });
        }
        orderTable.setModel(model);
    }


}
