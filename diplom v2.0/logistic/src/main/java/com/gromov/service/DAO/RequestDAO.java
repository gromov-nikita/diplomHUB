package com.gromov.service.DAO;

import com.gromov.entity.Country;
import com.gromov.entity.Request;
import com.gromov.entity.Truck;
import com.gromov.entity.User;
import com.gromov.entity.enums.CargoType;
import com.gromov.entity.enums.DriverAvailability;
import com.gromov.entity.enums.RequestStatus;
import com.gromov.entity.enums.WorkStatus;
import com.gromov.service.DBConnection.DBConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;

public class RequestDAO {

    public static List<Request> getListOfRequestsByUserID(User user) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Request> requests = session.createQuery(
                "from Request as r where r.user.id =" + user.getId()).getResultList();
        session.close();
        return requests;
    }
    public static int makeRequest(Request request) {

        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        request.setWorkStatus(WorkStatus.FREE);
        session.beginTransaction();
        CargoDAO.makeCargo(request.getCargo());
        int i = (Integer) session.save(request);
        transaction.commit();
        session.close();
        return i;
    }
    public static List<Request> getListOfRequestsByStatus(RequestStatus status) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Request> requests = session.createQuery(
                "from Request as r where r.status =" + status.name()).getResultList();
        session.close();
        return requests;
    }
    public static List<Request> getListOfRequests() {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Request> requests = session.createQuery(
                "from Request").getResultList();
        session.close();
        return requests;
    }
    public static void updateRequest(Request request) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        session.update(request);
        transaction.commit();
        session.close();
    }
    public static List<Request> getListOfRequestsByEmail(String email) {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        List<Request> requests = session.createQuery(
                "from Request as r where r.user.email ='" + email +"'").getResultList();
        session.close();
        return requests;
    }
    public static void makeFreeRequests() {
        Session session = DBConnection.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        session.createQuery("update Request as r set r.workStatus=:workStatus")
                .setParameter("workStatus",WorkStatus.FREE).executeUpdate();
        transaction.commit();
        session.close();
    }
    public static List<Request> getListOfMaxTenFreeRequestsStrongByTruck(Truck truck) {
        Session session = DBConnection.getSessionFactory().openSession();
        List<Request> requests = session.createQuery(
                "from Request as r where r.status =:status AND r.workStatus=:workStatus AND r.cargo.type=:type " +
                        "AND r.cargo.weight=:weight").setParameter("workStatus",WorkStatus.FREE)
                .setParameter("type", truck.getType()).setParameter("weight",truck.getWeight())
                .setParameter("status",RequestStatus.IN_PROCESS).setMaxResults(10).getResultList();
        return requests;
    }
    public static List<Request> getListOfWrongRequests() {
        Session session = DBConnection.getSessionFactory().openSession();
        List<Request> requests = session.createQuery(
                        "from Request as r where ((select count(d) from Driver as d where " +
                                "d.truck.weight >= r.cargo.weight AND d.truck.type = r.cargo.type " +
                                "AND d.availability =:availability) = 0) AND r.status=:status")
                .setParameter("availability", DriverAvailability.AVAILABLE)
                .setParameter("status",RequestStatus.IN_PROCESS).getResultList();
        return requests;
    }
    public static List<Request> getListOfMaxTenFreeRequestsByTruck(Truck truck) {
        Session session = DBConnection.getSessionFactory().openSession();
        List<Request> requests = session.createQuery(
                        "from Request as r where r.status =:status AND r.workStatus=:workStatus " +
                                "AND r.cargo.type=:type " +
                                "AND r.cargo.weight<=:weight").setParameter("workStatus",WorkStatus.FREE)
                .setParameter("type", truck.getType()).setParameter("weight",truck.getWeight())
                .setParameter("status",RequestStatus.IN_PROCESS).setMaxResults(10).getResultList();
        return requests;
    }
}
