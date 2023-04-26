package com.gromov.entity;

import com.gromov.entity.enums.DriverAvailability;
import com.gromov.entity.enums.Rating;
import jakarta.persistence.*;

@Entity
@Table(name = "comment", schema = "logistics", catalog = "")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "text")
    private String text;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "rating",nullable = false)
    private Rating rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
