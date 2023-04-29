package com.gromov.service.DAO;

import com.gromov.entity.Cargo;
import com.gromov.entity.Driver;
import com.gromov.entity.Request;
import com.gromov.entity.User;
import com.gromov.entity.enums.CargoType;
import com.gromov.entity.enums.DriverAvailability;
import com.gromov.entity.enums.RequestStatus;
import com.gromov.service.DBConnection.DBConnection;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DriverDAO {
    public static List<Driver> getListOfFreeDriversByCargo(Cargo cargo) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers = session.createQuery(
                "from Driver as d where d.availability = 'AVAILABLE' and d.truck.weight >=:weight " +
                        "and d.truck.type =:type").setParameter("weight",cargo.getWeight())
                .setParameter("type",cargo.getType()).getResultList();
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByCargo(Cargo cargo) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers = session.createQuery(
                        "from Driver as d where d.truck.weight >=:weight " +
                                "and d.truck.type =:type").setParameter("weight",cargo.getWeight())
                .setParameter("type",cargo.getType()).getResultList();
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfFreeDriversByUser(User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers = session.createQuery(
                        "from Driver as d where d.availability = 'AVAILABLE' and d.user.id =:id ")
                .setParameter("id",user.getId()).getResultList();
        session.close();
        return drivers;
    }
    public static void updateStatus(Driver driver) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        session.update(driver);
        transaction.commit();
        session.close();
    }
    public static List<Driver> getListOfDrivers() {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers = session.createQuery(
                "from Driver").getResultList();
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByUser(User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers = session.createQuery(
                "from Driver as d where d.user.id=:id").setParameter("id",user.getId()).getResultList();
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByName(String name) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers = session.createQuery(
                        "from Driver as d where d.name =:name")
                .setParameter("name",name).getResultList();
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailabilityAndName(String name, DriverAvailability availability) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if (availability.equals(DriverAvailability.ALL)) {
            drivers = session.createQuery(
                            "from Driver as d where d.name =:name")
                    .setParameter("name", name).getResultList();
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.name =:name and d.availability=: availability")
                    .setParameter("name", name).setParameter("availability", availability).getResultList();
        }
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailabilityAndWeightMore(int weight,DriverAvailability availability) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if(availability.equals(DriverAvailability.ALL)) {
            drivers = session.createQuery(
                            "from Driver as d where d.truck.weight >=:weight")
                    .setParameter("weight", weight).getResultList();
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.truck.weight >=:weight and d.availability=: availability")
                    .setParameter("weight", weight).setParameter("availability", availability).getResultList();
        }
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailability(DriverAvailability availability) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if(availability.equals(DriverAvailability.ALL)) {
            drivers = getListOfDrivers();
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.availability =:availability")
                    .setParameter("availability", availability).setParameter("availability", availability).getResultList();
        }
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailabilityAndPriceLess(int price,DriverAvailability availability) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if(availability.equals(DriverAvailability.ALL)) {
            drivers = session.createQuery(
                            "from Driver as d where d.price <=:price")
                    .setParameter("price", price).getResultList();
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.price <=:price and d.availability=: availability")
                    .setParameter("price", price).setParameter("availability", availability).getResultList();
        }
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailabilityAndCargoType(CargoType type,DriverAvailability availability) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if(availability.equals(DriverAvailability.ALL)) {
            drivers = session.createQuery(
                            "from Driver as d where d.truck.type =:type")
                    .setParameter("type", type).getResultList();
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.truck.type =:type and d.availability=: availability")
                    .setParameter("type", type).setParameter("availability", availability).getResultList();
        }
        session.close();
        return drivers;
    }
   public static List<Driver> getListOfDriversByNameAndManager(String name,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers = session.createQuery(
                        "from Driver as d where d.name =:name AND d.user.id=:id")
                .setParameter("name",name).setParameter("id",user.getId()).getResultList();
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailabilityAndNameAndManager(
            String name, DriverAvailability availability,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if (availability.equals(DriverAvailability.ALL)) {
            drivers = session.createQuery(
                            "from Driver as d where d.name =:name AND d.user.id=:id")
                    .setParameter("name", name).setParameter("id",user.getId()).getResultList();
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.name =:name and d.availability=: availability " +
                                    "AND d.user.id=:id")
                    .setParameter("name", name).setParameter("availability", availability)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailabilityAndWeightMoreAndManager
            (int weight,DriverAvailability availability,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if(availability.equals(DriverAvailability.ALL)) {
            drivers = session.createQuery(
                            "from Driver as d where d.truck.weight >=:weight AND d.user.id=:id")
                    .setParameter("weight", weight).setParameter("id",user.getId()).getResultList();
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.truck.weight >=:weight and d.availability=: availability " +
                                    "AND d.user.id=:id")
                    .setParameter("weight", weight).setParameter("availability", availability)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByManager(User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers = session.createQuery(
                "from Driver as d where d.user.id=:id")
                .setParameter("id",user.getId()).getResultList();
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailabilityAndManager(
            DriverAvailability availability,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if(availability.equals(DriverAvailability.ALL)) {
            drivers = getListOfDriversByManager(user);
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.availability =:availability AND d.user.id=:id")
                    .setParameter("availability", availability).setParameter("availability", availability)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailabilityAndPriceLessAndManager(
            int price,DriverAvailability availability,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if(availability.equals(DriverAvailability.ALL)) {
            drivers = session.createQuery(
                            "from Driver as d where d.price <=:price AND d.user.id=:id")
                    .setParameter("price", price).setParameter("id",user.getId())
                    .getResultList();
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.price <=:price and d.availability=: availability " +
                                    "AND d.user.id=:id")
                    .setParameter("price", price).setParameter("availability", availability)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return drivers;
    }
    public static List<Driver> getListOfDriversByAvailabilityAndCargoTypeAndManager(
            CargoType type,DriverAvailability availability,User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Driver> drivers;
        if(availability.equals(DriverAvailability.ALL)) {
            drivers = session.createQuery(
                            "from Driver as d where d.truck.type =:type AND d.user.id=:id")
                    .setParameter("type", type).setParameter("id",user.getId())
                    .getResultList();
        }
        else {
            drivers = session.createQuery(
                            "from Driver as d where d.truck.type =:type and d.availability=: availability " +
                                    "AND d.user.id=:id")
                    .setParameter("type", type).setParameter("availability", availability)
                    .setParameter("id",user.getId()).getResultList();
        }
        session.close();
        return drivers;
    }
    public static int createDriver(Driver driver) {
        Session session = null;
        int i = -386;
        session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        TruckDAO.createTruck(driver.getTruck());
        i = (Integer) session.save(driver);
        transaction.commit();
        session.close();
        return i;
    }
}
