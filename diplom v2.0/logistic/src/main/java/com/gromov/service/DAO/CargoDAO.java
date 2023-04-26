package com.gromov.service.DAO;

import com.gromov.entity.Cargo;
import com.gromov.entity.Request;
import com.gromov.entity.User;
import com.gromov.service.DBConnection.DBConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CargoDAO {
    public static int makeCargo(Cargo cargo) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        int i = (Integer) session.save(cargo);
        transaction.commit();
        session.close();
        return i;
    }
}
