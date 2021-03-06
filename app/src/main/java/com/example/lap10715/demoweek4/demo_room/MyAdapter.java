package com.example.lap10715.demoweek4.demo_room;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.lap10715.demoweek4.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MyAdapter extends ArrayAdapter {

    private Context context;
    private List<Note> notes;
    private int layoutItem;
    private CompositeDisposable compositeDisposable;
    private NoteDao noteDao;

    public MyAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.layoutItem = resource;
        notes = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();

        NoteDatabase noteDatabase = NoteDatabase.getAppDatabase(context);
        noteDao = noteDatabase.getNoteDao();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void loadData() {
        Disposable curDispoable = noteDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listNote -> {
                    MyAdapter.this.notes = listNote;

                    setNotifyOnChange(true);
                    notifyDataSetChanged();
                });

        compositeDisposable.add(curDispoable);
    }

    public void unsubscribe() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
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

        Disposable curDispoable = Observable.create(emitter -> {
            Note deleteNote = this.notes.get(position);
            long isSuccess = noteDao.delete(deleteNote);
            emitter.onNext(isSuccess);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .filter(isSuccess-> (long)isSuccess > -1)
                .flatMap(booleanOptional -> noteDao.getAll().toObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listNote -> {
                    MyAdapter.this.notes = listNote;

                    setNotifyOnChange(true);
                    notifyDataSetChanged();
                });

        compositeDisposable.add(curDispoable);
    }

    public void insertNote(String title, String content, Date curDate) {
        Disposable curDispoable = Observable.create(emitter -> {
            long isSuccess = noteDao.insert(new Note(null, title, content, curDate));
            emitter.onNext( isSuccess);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .filter(isSuccess-> (long)isSuccess > -1)
                .flatMap(booleanOptional -> noteDao.getAll().toObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listNote -> {
                    MyAdapter.this.notes = listNote;

                    setNotifyOnChange(true);
                    notifyDataSetChanged();
                });

        compositeDisposable.add(curDispoable);
    }

    public void modifyNote(String title, String content, Date curDate, int curPosChange) {
        Disposable curDispoable = Observable.create(emitter -> {

            Note curNote = notes.get(curPosChange);
            curNote.setTitle(title);
            curNote.setContent(content);
            curNote.setDate(curDate);

            long isSuccess = noteDao.update(curNote);

            emitter.onNext(isSuccess);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .filter(isSuccess-> (long)isSuccess > -1)
                .flatMap(boolOptional-> noteDao.getAll().toObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listNote -> {
                    MyAdapter.this.notes = listNote;

                    setNotifyOnChange(true);
                    notifyDataSetChanged();
                });

        compositeDisposable.add(curDispoable);
    }

    public Note getNote(int position) {
        return notes.get(position);
    }

}
