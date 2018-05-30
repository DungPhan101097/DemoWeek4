package com.example.lap10715.demoweek4.demo_realm;

import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import io.realm.RealmObject;

public class Note extends RealmObject {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String title;

    private String content;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
