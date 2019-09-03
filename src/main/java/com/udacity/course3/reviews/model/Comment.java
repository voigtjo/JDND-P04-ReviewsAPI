package com.udacity.course3.reviews.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_review")
    private Review review;

    private String text;

    private Date creationtime;

    public Comment() {
    }

    public Comment(Review review, String text) {
        this.review = review;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(review, comment.review) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(creationtime, comment.creationtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(review, text, creationtime);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", review=" + review +
                ", text='" + text + '\'' +
                ", creationtime=" + creationtime +
                '}';
    }
}
