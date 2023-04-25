package com.gromov.entity;

import com.gromov.entity.enums.UserType;
import jakarta.persistence.*;

import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "logistics")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "name",nullable = false,length = 45)
    private String name;
    @Basic
    @Column(name = "email",nullable = false,length = 45)
    private String email;
    @Basic
    @Column(name = "password",nullable = false,length = 45)
    private String password;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "type",nullable = false)
    private UserType type;
    @OneToMany(mappedBy = "user")
    private Collection<Request> requestEntities;

    public User() {
    }

    public User(String name, String email, String password, UserType type,
                Collection<Request> requestEntities) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.requestEntities = requestEntities;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Collection<Request> getRequestEntities() {
        return requestEntities;
    }
    public void setRequestEntities(Collection<Request> requestEntities) {
        this.requestEntities = requestEntities;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", requestEntities=" + requestEntities +
                '}';
    }
}
