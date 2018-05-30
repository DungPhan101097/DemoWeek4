package com.example.lap10715.demoweek4.demo_realm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.lap10715.demoweek4.R;
import com.example.lap10715.demoweek4.demo_room.NewNoteActivity;

import java.util.Date;

import io.realm.Realm;


public class DemoRealmActivity extends AppCompatActivity {
    public static final int RESULT_NEW_NOTE = 1;
    public static final int RESULT_MODIFY_NOTE = 2;
    public static final String INIT_TITLE = "init_title";
    public static final String INIT_CONTENT = "init_content";
    private ListView lvNotes;
    private MyAdapter myAdapter;
    private int curPosChange = 0;
    private Realm mRealm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        lvNotes = findViewById(R.id.lv_notes);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        myAdapter = new MyAdapter(this, R.layout.custom_item_note, mRealm);
        lvNotes.setAdapter(myAdapter);

        lvNotes.setOnItemClickListener((parent, view, position, id) -> {
            PopupMenu popup = new PopupMenu(DemoRealmActivity.this, view);
            popup.getMenuInflater()
                    .inflate(R.menu.popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id1 = item.getItemId();
                switch (id1) {
                    case R.id.item_modify:
                        curPosChange = position;
                        Intent intent = new Intent(DemoRealmActivity.this,
                                NewNoteActivity.class);
                        Note modifiedNote = myAdapter.getNote(position);
                        intent.putExtra(INIT_TITLE, modifiedNote.getTitle());
                        intent.putExtra(INIT_CONTENT, modifiedNote.getContent());
                        startActivityForResult(intent, RESULT_MODIFY_NOTE);
                        return true;

                    case R.id.item_delete:
                        DeleteDialog deleteDialog = new DeleteDialog(DemoRealmActivity.this,
                                position);
                        deleteDialog.show();
                        return true;
                }
                return false;
            });

            popup.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.act_new_note:
                startActivityForResult(new Intent(this, NewNoteActivity.class),
                        RESULT_NEW_NOTE);

                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String title = data.getStringExtra(NewNoteActivity.RESULT_TITLE);
            String content = data.getStringExtra(NewNoteActivity.RESULT_CONTENT);
            Date curDate = new Date();

            switch (resultCode) {
                case RESULT_NEW_NOTE:
                    myAdapter.insertNote(title, content, curDate);
                    break;
                case RESULT_MODIFY_NOTE:
                    myAdapter.modifyNote(title, content, curDate, curPosChange);
                    break;
            }
        }
    }


    public void deleteNote(int position) {
        myAdapter.deleteNote(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
