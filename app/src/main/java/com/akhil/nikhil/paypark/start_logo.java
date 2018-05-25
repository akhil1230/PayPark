package com.akhil.nikhil.paypark;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class start_logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_logo);

        if(FirebaseAuth.getInstance() != null)
        {
            FirebaseAuth.getInstance().signOut();
        }


        Handler h = new Handler();

        Runnable r = new Runnable() {
            @Override
            public void run() {


                Intent i = new Intent(start_logo.this, Start_activity.class);

                startActivity(i);

                finish();

            }
        };
        h.postDelayed(r, 1000);

    }
}
