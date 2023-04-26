package com.gromov.service.DAO;

import com.gromov.entity.Country;
import com.gromov.entity.User;
import com.gromov.service.DBConnection.DBConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CountryDAO {
    public static List<Country> getListOfCountries() {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Country> countries = session.createQuery(
                        "from Country").getResultList();
        session.close();
        return countries;
    }
}
