package com.example.lap10715.demoweek4.demo_realm;

import android.app.Activity;
import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.lap10715.demoweek4.R;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import io.reactivex.disposables.CompositeDisposable;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MyAdapter extends ArrayAdapter {

    private Context context;
    private RealmResults<Note> notes;
    private int layoutItem;
    private Realm mRealm;

    public MyAdapter(@NonNull Context context, int resource, Realm realm) {
        super(context, resource);
        this.context = context;
        this.layoutItem = resource;

        mRealm = realm;
        notes = mRealm.where(Note.class).findAll();
        notes.addChangeListener(notes1 -> notifyDataSetChanged());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(layoutItem, null);
        TextView tvTitle = row.findViewById(R.id.tv_title);
        TextView tvContent = row.findViewById(R.id.tv_content);
        TextView tvDate = row.findViewById(R.id.tv_date);

        tvTitle.setText(notes.get(position).getTitle());
        tvContent.setText(notes.get(position).getContent());

        tvDate.setText(new SimpleDateFormat("MM-dd-yyyy")
                .format(notes.get(position).getDate()));
        return row;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    public void deleteNote(int position) {
        mRealm.executeTransaction(realm -> {
            Note delNote = notes.get(position);
            delNote.deleteFromRealm();
        });

    }

    public void insertNote(String title, String content, Date curDate) {
        mRealm.executeTransaction(realm -> {
            Note myNote = mRealm.createObject(Note.class);
            myNote.setTitle(title);
            myNote.setContent(content);
            myNote.setDate(curDate);
        });
    }

    public void modifyNote(String title, String content, Date curDate, int curPosChange) {
        mRealm.executeTransaction(realm -> {
            Note modifiedNote = notes.get(curPosChange);
            modifiedNote.setTitle(title);
            modifiedNote.setContent(content);
            modifiedNote.setDate(curDate);
        });
    }

    public Note getNote(int position) {
        return notes.get(position);
    }

}
