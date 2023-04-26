package com.gromov.entity;

import com.gromov.entity.enums.DriverAvailability;
import com.gromov.entity.enums.Rating;
import com.gromov.service.DAO.OrderHistoryDAO;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "driver",schema = "logistics",catalog = "")
public class Driver {
    @Id
    private int id;
    @Basic
    @Column(name = "name",nullable = false,length = 45)
    private String name;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "availability",nullable = false)
    private DriverAvailability availability;
    @Basic
    @Column(name = "price",nullable = false)
    private int price;
    @OneToOne
    @MapsId
    @JoinColumn(name = "truckID",referencedColumnName = "id")
    private Truck truck;
    @OneToMany(mappedBy = "driver")
    private Collection<OrderHistory> orderHistoryEntities;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DriverAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(DriverAvailability availability) {
        this.availability = availability;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Collection<OrderHistory> getOrderHistoryEntities() {
        return orderHistoryEntities;
    }

    public void setOrderHistoryEntities(Collection<OrderHistory> orderHistoryEntities) {
        this.orderHistoryEntities = orderHistoryEntities;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
    public float getRating() {
        float rating = 0;
        int n = 0;
        for(OrderHistory x : OrderHistoryDAO.getListOfOrdersByDriver(this)) {
            if (!x.getComment().getRating().equals(Rating.NOTHING)) {
                rating += x.getComment().getRating().getMark();
                n++;
            }
        }
        if(n!=0) {
            return rating / n;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver that = (Driver) o;
        return id == that.id && price == that.price && Objects.equals(name, that.name)
                && availability == that.availability && Objects.equals(orderHistoryEntities,
                that.orderHistoryEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, availability, price, orderHistoryEntities);
    }

    @Override
    public String toString() {
        return name + " плата " + price + " BYN за 1 км. Грузоподъеность " + truck.getWeight() + "кг. Тип грузов "
                + truck.getType().getName();
    }
}
