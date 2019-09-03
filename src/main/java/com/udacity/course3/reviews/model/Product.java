package com.udacity.course3.reviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private String text;

    @NotNull
    private float price;

    private Date creationtime;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Review> reviews = new ArrayList<Review>();

    public Product() {
    }

    public Product(String name, String text, @NotNull float price) {
        this.name = name;
        this.text = text;
        this.price = price;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Float.compare(product.price, price) == 0 &&
                Objects.equals(name, product.name) &&
                Objects.equals(text, product.text) &&
                Objects.equals(creationtime, product.creationtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text, price, creationtime);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", price=" + price +
                ", creationtime=" + creationtime +
                '}';
    }
}
