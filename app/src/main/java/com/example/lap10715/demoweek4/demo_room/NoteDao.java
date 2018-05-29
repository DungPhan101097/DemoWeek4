package com.example.lap10715.demoweek4.demo_room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    Flowable<List<Note>> getAll();

    @Query("SELECT * FROM Note")
    List<Note> getAll2();

    @Query("SELECT * FROM Note WHERE Title LIKE :title LIMIT 1")
    Note findByTitile(String title);

    @Insert
    long insert(Note note);

    @Update
    int update(Note note);

    @Delete
    int delete(Note note);
}
