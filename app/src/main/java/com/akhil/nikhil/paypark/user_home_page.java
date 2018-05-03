package com.akhil.nikhil.paypark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class user_home_page extends FragmentActivity  {

    ProgressDialog progress_bar;
    private FragmentManager fm ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        progress_bar = new ProgressDialog(user_home_page.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Map is loading");



        fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame , new map_frag());

        ft.commit();


    }


    public void proceed(View view) {

        Intent i = new Intent(user_home_page.this,pay.class);
        startActivity(i);
    }

    public void open_filter(View view) {

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame , new FilterFragment());

        ft.commit();
    }

    public void open_list(View view) {

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame , new ListFragment());

        ft.commit();

    }

    public void open_map(View view) {

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame , new map_frag());

        ft.commit();
    }
}
