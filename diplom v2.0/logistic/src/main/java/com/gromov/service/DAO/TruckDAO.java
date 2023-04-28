package com.gromov.service.DAO;

import com.gromov.entity.Truck;
import com.gromov.entity.User;
import com.gromov.service.DBConnection.DBConnection;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class TruckDAO {
    public static int createTruck(Truck truck){
        Session session = null;
        int i = -386;
        session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        i = (Integer) session.save(truck);
        transaction.commit();
        session.close();
        return i;
    }
}
