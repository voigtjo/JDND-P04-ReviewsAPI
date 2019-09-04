package com.udacity.course3.reviews.model;

import java.util.Date;
import java.util.Objects;

public class CommentD {

    private String text;
    private Date creationtime;

    public CommentD() {
    }

    public CommentD(String text, Date creationtime) {
        this.text = text;
        this.creationtime = creationtime;
    }

    public CommentD(Comment comment) {
        this.text = comment.getText();
        this.creationtime = comment.getCreationtime();
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
        if (!(o instanceof CommentD)) return false;
        CommentD commentD = (CommentD) o;
        return Objects.equals(text, commentD.text) &&
                Objects.equals(creationtime, commentD.creationtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, creationtime);
    }

    @Override
    public String toString() {
        return "CommentD{" +
                "text='" + text + '\'' +
                ", creationtime=" + creationtime +
                '}';
    }
}
