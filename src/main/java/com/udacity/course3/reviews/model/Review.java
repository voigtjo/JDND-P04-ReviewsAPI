package com.udacity.course3.reviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Review {
    private static final int MIN_STAR = 1;
    private static final int MAX_STAR = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_product")
    private Product product;

    private String text;

    @Min(MIN_STAR) @Max(MAX_STAR)
    private int star;

    private Date creationtime;

    @OneToMany(mappedBy = "review")
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    public Review() {
    }

    public Review(Product product, String text, @Min(MIN_STAR) @Max(MAX_STAR) int star) {
        this.product = product;
        this.text = text;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Date getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return star == review.star &&
                Objects.equals(product, review.product) &&
                Objects.equals(text, review.text) &&
                Objects.equals(creationtime, review.creationtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, text, star, creationtime);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", product=" + product +
                ", text='" + text + '\'' +
                ", star=" + star +
                ", creationtime=" + creationtime +
                '}';
    }
}
