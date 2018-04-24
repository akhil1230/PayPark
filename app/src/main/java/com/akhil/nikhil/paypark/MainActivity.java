package com.akhil.nikhil.paypark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sign);
    }



    public void createacc(View view) {

        Intent i = new Intent(MainActivity.this,create_newactivity.class);
        startActivity(i);
    }

    public void signin(View view) {

        Intent i = new Intent(MainActivity.this,user_home_page.class);
        startActivity(i);
    }
    
}
