package com.akhil.nikhil.paypark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Start_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);
    }

    public void user_btn(View view) {
        Intent i = new Intent(Start_activity.this,MainActivity.class);
        startActivity(i);
    }

    public void sp_btn(View view) {
        Intent i = new Intent(Start_activity.this,sp_login.class);
        startActivity(i);
    }
}
