package com.gromov.ui;

import com.gromov.entity.Request;
import com.gromov.entity.User;
import com.gromov.service.DAO.RequestDAO;
import com.gromov.service.dataExport.ExcelAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class TableRequestForm {
    private static JFrame tableRequestForm = new JFrame();
    private static User user;
    private JPanel tableRequestPanel;
    private JPanel requestLabelPanel;
    private JPanel buttonPanel;
    private JButton backButton;
    private JPanel requestTablePanePanel;
    private JTable requestTable;
    private JLabel requestLabel;
    private JScrollPane requestTablePane;
    private JButton updateButton;
    private JPanel findByEmailPanel;
    private JTextField findByEmailField;
    private JButton findButton;
    private JLabel findLabel;
    private JButton excelExportButton;

    public TableRequestForm(User user) {
        tableRequestForm.setAlwaysOnTop(true);
        this.user = user;
        tableRequestForm.setContentPane(tableRequestPanel);
        tableRequestForm.setTitle("Справочная информация");
        tableRequestForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableRequestForm.setVisible(true);
        tableRequestForm.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        tableRequestForm.setBounds(dimension.width/2-400,dimension.height/2-200,800,400);
        createRequestTable();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerProfileForm(user);
                tableRequestForm.dispose();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRequestTable();
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRequestTableByEmail();
            }
        });
        excelExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = new Date().toString();
                date = date.replaceAll(":","-");
                ExcelAdapter.excelExport(requestTable,"Заявки "
                        + date,"Заявки");

            }
        });
    }
    private void createRequestTable() {
        List<Request> requests = RequestDAO.getListOfRequests();
        DefaultTableModel model = new DefaultTableModel(null,new String[] {
                "Email заказчика","Ф.И.О. заказчика","Дата отправки","Дата доставки","Откуда","Куда","Название груза",
                "Масса(кг)","Тип", "Статус заявки"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for(Request x : requests) {
            model.insertRow(model.getRowCount(),new String[] {
                    x.getUser().getEmail(),x.getUser().getName(),x.getDateSending().toString(),
                    x.getDateDelivery().toString(),x.getFrom().getName(),x.getTo().getName(),
                    x.getCargo().getName(),x.getCargo().getWeight()+"",x.getCargo().getType().getName(),
                    x.getStatus().getName()
            });
        }
        requestTable.setModel(model);
    }
    private void createRequestTableByEmail() {
        List<Request> requests = RequestDAO.getListOfRequestsByEmail(findByEmailField.getText());
        DefaultTableModel model = new DefaultTableModel(null,new String[] {
                "Email заказчика","ФИО заказчика","Дата отправки","Дата доставки","Откуда","Куда","Название груза",
                "Масса(кг)","Тип", "Статус заявки"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for(Request x : requests) {
            model.insertRow(model.getRowCount(),new String[] {
                    x.getUser().getEmail(),x.getUser().getName(),x.getDateSending().toString(),
                    x.getDateDelivery().toString(),x.getFrom().getName(),x.getTo().getName(),
                    x.getCargo().getName(),x.getCargo().getWeight()+"",x.getCargo().getType().getName(),
                    x.getStatus().getName()
            });
        }
        requestTable.setModel(model);
    }
}
