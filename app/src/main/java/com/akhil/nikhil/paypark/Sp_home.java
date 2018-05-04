package com.akhil.nikhil.paypark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.akhil.nikhil.paypark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sp_home extends AppCompatActivity {


    RadioGroup on_off_radio ;

    RadioButton on_radio , off_radio ;

    createaccount sp_details ;

    EditText parksp_n;

    EditText car_n;

    EditText bike_n ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_home);

        parksp_n  = findViewById(R.id.space);

        on_off_radio = findViewById(R.id.on_off_group);

        on_radio = findViewById(R.id.on_radio);
        off_radio = findViewById(R.id.off_radio);

        car_n =findViewById(R.id.car_id);

        bike_n = findViewById(R.id.bike_id);


        get_details();
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



        String parking_space_left = parksp_n.getText().toString();



            String car_s = car_n.getText().toString();
            if(car_n.length()<1)
            {
               car_n.setError("must contain 1 digits");
               return;
            }


            String bike_s = bike_n.getText().toString();
            if(bike_n.length()<1)
            {
                bike_n.setError("must contain 1 digits");
                return;
            }

        final ProgressDialog progress_bar = new ProgressDialog(Sp_home.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("updating");
        progress_bar.show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".","");

        createaccount data = new createaccount(sp_details.name, sp_details.address,sp_details.mobile, sp_details.parking_capacity, sp_details.lat , sp_details.lng , car_s , bike_s , avail , parking_space_left);

        database.getReference().child("parking_details").child(email).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progress_bar.hide();

                Intent i = new Intent(Sp_home.this,sp_end.class);
                startActivity(i);

            }
        });





        }

        private void get_details()
        {

            final ProgressDialog progress_bar = new ProgressDialog(Sp_home.this);
            progress_bar.setTitle("please wait");
            progress_bar.setMessage("fetching..");
            progress_bar.show();


            FirebaseAuth auth = FirebaseAuth.getInstance();

            String email = auth.getCurrentUser().getEmail().replace(".","");

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            database.getReference().child("parking_details").child(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    progress_bar.hide();

                    sp_details = dataSnapshot.getValue(createaccount.class);

                    if(sp_details.available.equals("yes"))
                    {
                        on_radio.setChecked(true);
                    }

                    else {
                        off_radio.setChecked(true);
                    }

                    parksp_n.setText(sp_details.space_left);

                    car_n.setText(sp_details.car_charges);

                    bike_n.setText(sp_details.bike_charges);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
    }

