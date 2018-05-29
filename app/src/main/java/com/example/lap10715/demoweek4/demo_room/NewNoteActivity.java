package com.example.lap10715.demoweek4.demo_room;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.lap10715.demoweek4.R;

public class NewNoteActivity extends AppCompatActivity {

    public static final String RESULT_TITLE= "result_title";
    public static final String RESULT_CONTENT= "result_content";
    private EditText edtTitle, edtContent;
    private Button btnNew;
    private boolean isModify = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        edtTitle = findViewById(R.id.edt_title);
        edtContent = findViewById(R.id.edt_content);
        btnNew = findViewById(R.id.btn_new);

        initData();

        btnNew.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String content = edtContent.getText().toString();

            Intent returnIntent = new Intent();
            returnIntent.putExtra(RESULT_TITLE, title);
            returnIntent.putExtra(RESULT_CONTENT, content);

            if(isModify){
                setResult(DemoRoomActivity.RESULT_MODIFY_NOTE,returnIntent);
            }
            else{
                setResult(DemoRoomActivity.RESULT_NEW_NOTE,returnIntent);
            }
            finish();
        });

    }

    private void initData() {
        Intent intent = getIntent();
        if(intent!= null){
            String title = intent.getStringExtra(DemoRoomActivity.INIT_TITLE);
            String content = intent.getStringExtra(DemoRoomActivity.INIT_CONTENT);
            if(!(title == null && content == null))
                isModify = true;
            edtTitle.setText(title);
            edtContent.setText(content);
        }
    }


}
