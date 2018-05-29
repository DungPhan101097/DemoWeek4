package com.example.lap10715.demoweek4.demo_room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private Long id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Content")
    private String content;

    @ColumnInfo(name = "Date")
    private Date date;

    public Note(Long id, String title, String content, Date date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

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


