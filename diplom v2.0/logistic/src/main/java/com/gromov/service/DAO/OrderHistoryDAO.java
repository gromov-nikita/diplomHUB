package com.gromov.service.DAO;

import com.gromov.entity.Driver;
import com.gromov.entity.OrderHistory;
import com.gromov.entity.Request;
import com.gromov.entity.User;
import com.gromov.entity.enums.CargoType;
import com.gromov.entity.enums.OrderStatus;
import com.gromov.entity.enums.RequestStatus;
import com.gromov.service.DBConnection.DBConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.List;

public class OrderHistoryDAO {
    public static int makeOrder(OrderHistory order) {

        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        CommentDAO.postComment(order.getComment());
        int i = (Integer) session.save(order);
        transaction.commit();
        session.close();
        return i;
    }
    public static List<OrderHistory> getListOfOrdersByStatus(OrderStatus status) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                    "from OrderHistory").getResultList();

        }
        else {
            orders = session.createQuery(
                    "from OrderHistory as o where o.status =:status").setParameter("status", status).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndManager(OrderStatus status, User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                    "from OrderHistory as o where o.driver.user.id=:id").setParameter("id",user.getId())
                    .getResultList();

        }
        else {
            orders = session.createQuery(
                    "from OrderHistory as o where o.status =:status AND o.driver.user.id=:id")
                    .setParameter("status", status).setParameter("id",user.getId())
                    .getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByUserAndStatus(OrderStatus status, User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                    "from OrderHistory as o where o.request.user.id = :id")
                    .setParameter("id",user.getId()).getResultList();

        }
        else {
            orders = session.createQuery(
                    "from OrderHistory as o where o.status =:status and o.request.user.id =: id")
                    .setParameter("status", status).setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return orders;
    }
    public static void updateStatus(OrderHistory order) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        session.update(order);
        transaction.commit();
        session.close();
    }
    public static List<OrderHistory> getListOfOrders() {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders = session.createQuery(
                "from OrderHistory").getResultList();
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByDriver(Driver driver) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders = session.createQuery(
                "from OrderHistory as o where o.driver.id =:id").setParameter("id",driver.getId()).getResultList();
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndCustomerEmail(OrderStatus status,String email) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.user.email =:email")
                    .setParameter("email", email).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.user.email =:email and o.status =:status")
                    .setParameter("email", email).setParameter("status", status).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndDriverName(OrderStatus status,String name) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.name =:name")
                    .setParameter("name", name).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.name =:name" +
                                    " and o.status =:status")
                    .setParameter("name", name).setParameter("status", status).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndLessPrice(OrderStatus status,int price) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.price <=:price")
                    .setParameter("price", price).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.price <=:price and o.status =:status")
                    .setParameter("price", price).setParameter("status", status).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndMaxWeight(OrderStatus status,int maxWeight) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.truck.weight =:weight")
                    .setParameter("weight", maxWeight).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.truck.weight =:weight" +
                                    " and o.status =:status")
                    .setParameter("weight", maxWeight).setParameter("status", status).getResultList();
        }
        session.close();
        return orders;
    }

    public static List<OrderHistory> getListOfOrdersByStatusAndCargoWeight(OrderStatus status,int cargoWeight) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.cargo.weight =:weight")
                    .setParameter("weight", cargoWeight).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.cargo.weight =:weight" +
                                    " and o.status =:status")
                    .setParameter("weight", cargoWeight).setParameter("status", status).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndCargoType(OrderStatus status, CargoType cargoType) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.cargo.type =:type")
                    .setParameter("type", cargoType).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.cargo.type =:type and o.status =:status")
                    .setParameter("type", cargoType).setParameter("status", status).getResultList();
        }
        session.close();
        return orders;
    }

   public static List<OrderHistory> getListOfOrdersByStatusAndCustomerEmailAndManager(OrderStatus status,
                                                                                       String email,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.user.email =:email " +
                                    "AND o.driver.user.id =:id")
                    .setParameter("email", email).setParameter("id",user.getId()).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.user.email =:email and o.status =:status" +
                                    "AND o.driver.user.id =:id")
                    .setParameter("email", email).setParameter("status", status)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndDriverNameAndManager(
            OrderStatus status,String name,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.name =:name AND o.driver.user.id =:id")
                    .setParameter("name", name).setParameter("id",user.getId()).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.name =:name" +
                                    " and o.status =:status AND o.driver.user.id =:id")
                    .setParameter("name", name).setParameter("status", status)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndLessPriceAndManager(
            OrderStatus status,int price,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.price <=:price AND o.driver.user.id =:id")
                    .setParameter("price", price).setParameter("id",user.getId()).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.price <=:price and o.status =:status " +
                                    "AND o.driver.user.id =:id")
                    .setParameter("price", price).setParameter("status", status)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndMaxWeightAndManager(
            OrderStatus status,int maxWeight,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.truck.weight =:weight " +
                                    "AND o.driver.user.id =:id")
                    .setParameter("weight", maxWeight).setParameter("id",user.getId()).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.driver.truck.weight =:weight" +
                                    " and o.status =:status AND o.driver.user.id =:id")
                    .setParameter("weight", maxWeight).setParameter("status", status)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return orders;
    }

    public static List<OrderHistory> getListOfOrdersByStatusAndCargoWeightAndManager(
            OrderStatus status,int cargoWeight,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.cargo.weight =:weight " +
                                    "AND o.driver.user.id =:id")
                    .setParameter("weight", cargoWeight).setParameter("id",user.getId()).getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.cargo.weight =:weight" +
                                    " and o.status =:status AND o.driver.user.id =:id")
                    .setParameter("weight", cargoWeight).setParameter("status", status)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByStatusAndCargoTypeAndManager(
            OrderStatus status, CargoType cargoType,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.cargo.type =:type " +
                                    "AND o.driver.user.id =:id")
                    .setParameter("type", cargoType).setParameter("id",user.getId())
                    .getResultList();
        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.cargo.type =:type and o.status =:status " +
                                    "AND o.driver.user.id =:id")
                    .setParameter("type", cargoType).setParameter("status", status)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return orders;
    }
    public static List<OrderHistory> getListOfOrdersByUserAndStatusAndManager(OrderStatus status, User user,
                                                                              User manager) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<OrderHistory> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = session.createQuery(
                            "from OrderHistory as o where o.request.user.id = :id " +
                                    "AND o.driver.user.id =:managerId")
                    .setParameter("id",user.getId()).setParameter("managerId",manager.getId())
                    .getResultList();

        }
        else {
            orders = session.createQuery(
                            "from OrderHistory as o where o.status =:status and o.request.user.id =: id " +
                                    "AND o.driver.user.id =:managerId")
                    .setParameter("status", status).setParameter("id",user.getId())
                    .setParameter("managerId",manager.getId()).getResultList();
        }
        session.close();
        return orders;
    }

}
