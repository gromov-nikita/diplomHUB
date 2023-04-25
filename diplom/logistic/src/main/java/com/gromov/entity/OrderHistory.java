package com.gromov.entity;

import com.gromov.entity.enums.OrderStatus;
import com.gromov.entity.enums.Rating;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "orderhistory",schema = "logistics",catalog = "")
public class OrderHistory {
    @Id
    private int id;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private OrderStatus status;
    @OneToOne
    @MapsId
    @JoinColumn(name = "requestID", referencedColumnName = "cargoID")
    private Request request;
    @ManyToOne
    @JoinColumn(name = "driverID",referencedColumnName = "truckID")
    private Driver driver;
    @OneToOne
    @JoinColumn(name = "commentID", referencedColumnName = "id")
    private Comment comment;

    public OrderHistory() {
        comment = new Comment();
        comment.setRating(Rating.NOTHING);
        comment.setText("");
    }

    public OrderHistory(OrderStatus status, Request request, Driver driver) {
        this.status = status;
        this.request = request;
        this.driver = driver;
        comment = new Comment();
        comment.setRating(Rating.NOTHING);
        comment.setText("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getUnderload() {
        return driver.getTruck().getWeight()-request.getCargo().getWeight();
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderHistory that = (OrderHistory) o;
        return id == that.id && status == that.status && Objects.equals(request, that.request)
                && Objects.equals(driver, that.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, request, driver);
    }

    @Override
    public String toString() {
        return  request.getUser().getEmail()
                + " отправляет " + request.getDateSending()
                + " прибывает " + request.getDateDelivery()
                + " из " + request.getFrom().getName()
                + " в " + request.getTo().getName()
                + " груз " + request.getCargo().getName()
                + " весом " + request.getCargo().getWeight()
                + " кг " + request.getCargo().getType().getName()
                + " везет " + driver.getName();
    }
}
