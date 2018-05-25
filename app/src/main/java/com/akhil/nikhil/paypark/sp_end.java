package com.akhil.nikhil.paypark;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class sp_end extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_end);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void finish(View view) {

        finishAffinity();
    }
}
