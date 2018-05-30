package com.example.lap10715.demoweek4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.lap10715.demoweek4.demo_realm.DemoRealmActivity;
import com.example.lap10715.demoweek4.demo_room.DemoRoomActivity;


public class MainActivity extends AppCompatActivity {

    private Button btnKeepYourNoteRoom;
    private Button btnKeepYourNoteRealm;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please provide permission!", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            }
        } else {
            initViews();
        }



    }

    private void initViews() {
        btnKeepYourNoteRoom = findViewById(R.id.btn_keep_note_room);
        btnKeepYourNoteRealm = findViewById(R.id.btn_keep_note_realm);

        btnKeepYourNoteRoom.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this,
                        DemoRoomActivity.class)));

        btnKeepYourNoteRealm.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this,
                        DemoRealmActivity.class)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initViews();
                } else {
                    Toast.makeText(this, "Application can't access any data!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

        }
    }
}
