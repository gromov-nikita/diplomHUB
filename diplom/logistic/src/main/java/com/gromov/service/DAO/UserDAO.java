package com.gromov.service.DAO;

import com.gromov.entity.User;
import com.gromov.entity.enums.UserType;
import com.gromov.entity.enums.UserType.*;
import com.gromov.service.DBConnection.DBConnection;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class UserDAO {
    public static User logIn(String email, String password) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        User user = (User) session.createQuery(
                        "from User as u where u.email = :email AND u.password = :password")
                .setParameter("email",email).setParameter("password",password)
                .getSingleResult();
        session.close();
        return user;
    }
    public static int signUp(User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        int i = (Integer) session.save(user);
        transaction.commit();
        session.close();
        return i;
    }
}
