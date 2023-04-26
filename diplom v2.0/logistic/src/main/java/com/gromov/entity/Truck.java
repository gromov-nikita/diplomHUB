package com.gromov.entity;

import com.gromov.entity.enums.CargoType;
import jakarta.persistence.*;


@Entity
@Table(name = "truck", schema = "logistics", catalog = "")
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "weight",nullable = false)
    private int weight;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "type",nullable = false)
    private CargoType type;


    public int getId() {
            return id;
        }

    public void setId(int id) {
            this.id = id;
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
            Truck that = (com.gromov.entity.Truck) o;
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
