package com.akhil.nikhil.paypark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class sp_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_login);
    }

    public void createacc(View view) {

        Intent i = new Intent(sp_login.this,create_sp.class);
        startActivity(i);
    }

    public void signin(View view) {
    }
}
