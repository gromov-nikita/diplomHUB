package com.gromov.entity;

import com.gromov.entity.enums.CargoType;
import jakarta.persistence.*;

@Entity
@Table(name = "cargo", schema = "logistics", catalog = "")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "name",nullable = false,length = 45)
    private String name;
    @Basic
    @Column(name = "weight",nullable = false)
    private int weight;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "type",nullable = false)
    private CargoType type;

    public Cargo() {
    }

    public Cargo(String name, int weight, CargoType type) {
        this.name = name;
        this.weight = weight;
        this.type = type;
    }

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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public CargoType getType() {
        return type;
    }

    public void setType(CargoType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cargo that = (Cargo) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + weight;
        return result;
    }
}
