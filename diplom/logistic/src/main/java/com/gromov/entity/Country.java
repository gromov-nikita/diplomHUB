package com.gromov.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "country",schema = "logistics", catalog = "")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "name",nullable = false,length = 45)
    private String name;
    @OneToMany(mappedBy = "from")
    private Collection<Request> requestFromEntities;
    @OneToMany(mappedBy = "to")
    private Collection<Request> requestToEntities;

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

    public Collection<Request> getRequestFromEntities() {
        return requestFromEntities;
    }

    public void setRequestFromEntities(Collection<Request> requestFromEntities) {
        this.requestFromEntities = requestFromEntities;
    }

    public Collection<Request> getRequestToEntities() {
        return requestToEntities;
    }

    public void setRequestToEntities(Collection<Request> requestToEntities) {
        this.requestToEntities = requestToEntities;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country that = (Country) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
