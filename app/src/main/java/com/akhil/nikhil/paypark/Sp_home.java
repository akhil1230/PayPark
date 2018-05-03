package com.akhil.nikhil.paypark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.akhil.nikhil.paypark.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Sp_home extends AppCompatActivity {


    RadioGroup on_off_radio ;

    RadioButton on_radio , off_radio ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_home);

        on_off_radio = findViewById(R.id.on_off_group);

        on_radio = findViewById(R.id.on_radio);
        off_radio = findViewById(R.id.off_radio);

    }

    public void sub_sp(View view)
    {

        String avail = "" +
                "" ;

        if(on_radio.isChecked())
        {
            avail = "yes";
        }

        if(off_radio.isChecked())
        {
            avail = "no";
        }

        EditText parksp_n = findViewById(R.id.space);
        String parksp_s = parksp_n.getText().toString();


        EditText car_n =findViewById(R.id.car_id);
            String car_s = car_n.getText().toString();
            if(car_n.length()>=2)
            {
               car_n.setError("must contain 2 digits");
            }


            EditText bike_n = findViewById(R.id.bike_id);
            String bike_s = bike_n.getText().toString();
            if(bike_n.length()>=2)
            {
                bike_n.setError("must contain 2 digits");
            }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".","");

        parking_detail_model data = new parking_detail_model(avail , parksp_s , car_s , bike_s);

        database.getReference().child("parking_details").child(email).setValue(data);

        Intent i = new Intent(Sp_home.this,sp_end.class);
        startActivity(i);

        }
    }

