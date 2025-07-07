package models;

import java.sql.Timestamp;

public class Announcement {
    private int id;
    private String title;
    private String message;
    private Timestamp postedAt;

    public Announcement(int id, String title, String message, Timestamp postedAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.postedAt = postedAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getPostedAt() {
        return postedAt;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPostedAt(Timestamp postedAt) {
        this.postedAt = postedAt;
    }
}
