package com.gromov.service.DAO;

import com.gromov.entity.Comment;
import com.gromov.entity.OrderHistory;
import com.gromov.entity.User;
import com.gromov.service.DBConnection.DBConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CommentDAO {
    public static User getComment(OrderHistory order) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        User user = (User) session.createQuery(
                        "from Comment as c where c.id = :id").setParameter("id",order.getComment().getId())
                .getSingleResult();
        session.close();
        return user;
    }
    public static Long postComment(Comment comment) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        long i = (long) session.save(comment);
        transaction.commit();
        session.close();
        return i;
    }
    public static void updateComment(Comment comment) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        session.update(comment);
        transaction.commit();
        session.close();
    }
}
