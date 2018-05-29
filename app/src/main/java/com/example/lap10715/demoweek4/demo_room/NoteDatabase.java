package com.example.lap10715.demoweek4.demo_room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Note.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance = null;

    public abstract NoteDao getNoteDao();

    public static final Migration MIGRATION_1_2 = new Migration(1,2){

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static void destroyInstance(){
        instance = null;
    }

    public static NoteDatabase getAppDatabase(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "Note.db")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }
}
