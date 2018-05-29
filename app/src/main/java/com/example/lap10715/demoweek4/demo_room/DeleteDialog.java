package com.example.lap10715.demoweek4.demo_room;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.example.lap10715.demoweek4.R;

public class DeleteDialog extends Dialog implements View.OnClickListener{

    private Context context;
    private Button btnYes, btnNo;
    private int position;

    public DeleteDialog(@NonNull Context context, int position) {
        super(context);
        this.context = context;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_yes:
                DemoRoomActivity curActivity = (DemoRoomActivity)context;
                curActivity.deleteNote(position);
                break;
            case R.id.btn_no:
                dismiss();
                break;
        }
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_note);

        btnYes = findViewById(R.id.btn_yes);
        btnNo = findViewById(R.id.btn_no);

        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }


}
