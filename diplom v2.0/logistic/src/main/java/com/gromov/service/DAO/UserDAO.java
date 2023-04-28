package com.gromov.service.DAO;

import com.gromov.entity.User;
import com.gromov.entity.enums.UserType;
import com.gromov.entity.enums.UserType.*;
import com.gromov.service.DBConnection.DBConnection;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.DataException;

import java.sql.SQLException;
import java.util.List;


public class UserDAO {
    public static User logIn(String email, String password) throws NoResultException{
        Session session = null;
        User user = null;
        session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        user = (User) session.createQuery(
                            "from User as u where u.email = :email AND u.password = :password")
                    .setParameter("email", email).setParameter("password", password)
                    .getSingleResult();
        session.close();
        return user;
    }
    public static int signUp(User user) throws SQLException {
        try {
            getUserByEmail(user.getEmail());
        }
        catch (NoResultException ex) {
            Session session = null;
            int i = -386;
            session = DBConnection.getSessionFactory().openSession();
            Transaction transaction = session.getTransaction();
            session.beginTransaction();
            i = (Integer) session.save(user);
            transaction.commit();
            session.close();
            return i;
        }
        throw new SQLException("Пользователь существует");
    }
    public static User getUserByEmail(String email) throws NoResultException {
        Session session = null;
        User user = null;
        session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        user = (User) session.createQuery(
                            "from User as u where u.email = :email")
                    .setParameter("email", email).getSingleResult();
        session.close();
        return user;
    }
    public static List<User> getUserByType(UserType type) {
        Session session = null;
        List<User> users = null;
        session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        users = session.createQuery(
                        "from User as u where u.type = :type")
                .setParameter("type", type).getResultList();
        session.close();
        return users;
    }
}
