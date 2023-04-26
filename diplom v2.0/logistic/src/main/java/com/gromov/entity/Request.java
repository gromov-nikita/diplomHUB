package com.gromov.entity;

import com.gromov.entity.enums.RequestStatus;
import com.gromov.entity.enums.WorkStatus;
import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "request", schema = "logistics", catalog = "")
public class Request {
    @Id
    private int id;
    @Basic
    @Column(name = "dateSending",nullable = false)
    private Date dateSending;
    @Basic
    @Column(name = "dateDelivery",nullable = false)
    private Date dateDelivery;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private RequestStatus status;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "workStatus",nullable = false)
    private WorkStatus workStatus;
    @ManyToOne
    @JoinColumn(name = "userID",referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "fromID",referencedColumnName = "id")
    private Country from;
    @ManyToOne
    @JoinColumn(name = "toID",referencedColumnName = "id")
    private Country to;
    @OneToOne
    @MapsId
    @JoinColumn(name = "cargoID",referencedColumnName = "id")
    private Cargo cargo;

    public Request() {
    }

    public Request(Date dateSending, Date dateDelivery, RequestStatus status,
                   User user, Country from, Country to, Cargo cargo) {
        this.dateSending = dateSending;
        this.dateDelivery = dateDelivery;
        this.status = status;
        this.user = user;
        this.from = from;
        this.to = to;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateSending() {
        return dateSending;
    }

    public void setDateSending(Date dateSending) {
        this.dateSending = dateSending;
    }

    public Date getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Country getFrom() {
        return from;
    }

    public void setFrom(Country from) {
        this.from = from;
    }

    public Country getTo() {
        return to;
    }

    public void setTo(Country to) {
        this.to = to;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    @Override
    public String toString() {
        return user.getEmail() + " отправляет " + dateSending + " прибывает " + dateDelivery + " из "
                + from.getName() + " в " + to.getName() + " груз " + cargo.getName()
                + " весом " + cargo.getWeight() + " кг " + cargo.getType().getName();
    }
}
