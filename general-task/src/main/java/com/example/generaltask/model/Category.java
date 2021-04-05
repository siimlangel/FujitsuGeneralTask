package com.example.generaltask.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 *  An entity class with contains the information of a single category.
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String type;

    /**
     * @see com.example.generaltask.model.Feedback
     */
    @ManyToMany(mappedBy = "categories")
    private Set<Feedback> feedbacks;

    public Category() {}

    public Category(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", type='" + type + "}";
    }
}
