package com.udacity.course3.reviews.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document("reviews")
public class ReviewD {
    @Id
    private int _id;

    @Indexed
    private int productId;

    private String text;
    private int star;
    private Date creationtime;

    private List<CommentD> comments = new ArrayList<>();

    public ReviewD() {
    }

    public ReviewD(Review review) {
        this._id = review.getId();
        this.productId = review.getProduct().getId();
        this.text = review.getText();
        this.star = review.getStar();
        this.creationtime = review.getCreationtime();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public List<CommentD> getComments() {
        return comments;
    }

    public void setComments(List<CommentD> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewD)) return false;
        ReviewD reviewD = (ReviewD) o;
        return productId == reviewD.productId &&
                star == reviewD.star &&
                Objects.equals(text, reviewD.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, text, star);
    }

    @Override
    public String toString() {
        return "ReviewD{" +
                "_id=" + _id +
                ", productId=" + productId +
                ", text='" + text + '\'' +
                ", star=" + star +
                ", creationtime=" + creationtime +
                ", comments=" + comments +
                '}';
    }
}
