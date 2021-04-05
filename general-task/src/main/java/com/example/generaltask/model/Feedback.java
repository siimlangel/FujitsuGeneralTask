package com.example.generaltask.model;



import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Set;

/**
 * An entity class which contains the information of a single feedback.
 *
 */

@Entity
@Table(name="feedback")
public class Feedback extends DateAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String text;

    @Column(nullable = false)
    @NotNull
    private String username;

    @Column(nullable = false)
    @NotNull
    @Email
    private String email;



    /**
     * Many to many relationship between feedbacks and categories,
     * with one to many or just having a category row category data would be duplicated if
     * different feedbacks have the same category.
     */
    @ManyToMany
    @JoinTable(
            name = "feedback_category",
            joinColumns = @JoinColumn(name = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;


    public Feedback() {}

    public Feedback(Long id, @NotEmpty @NotNull String text, @NotEmpty @NotNull String username, @NotEmpty @NotNull String email, Set<Category> categories) {
        this.id = id;
        this.text = text;
        this.username = username;
        this.email = email;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    public Set<Category> getCategories() {
        return categories;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", categories=" + categories +
                '}';
    }
}
