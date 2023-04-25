package com.gromov.service.DAO;

import com.gromov.entity.Cargo;
import com.gromov.entity.Driver;
import com.gromov.entity.Request;
import com.gromov.entity.enums.CargoType;
import com.gromov.entity.enums.DriverAvailability;
import com.gromov.entity.enums.RequestStatus;
import com.gromov.service.DBConnection.DBConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}
