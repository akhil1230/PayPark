package com.akhil.nikhil.paypark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class create_sp extends AppCompatActivity {

    EditText address_ad ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sp);

        address_ad = findViewById(R.id.add_id);

        address_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(create_sp.this , PlacePickerActivity.class));
            }
        });
    }

    public void createacc(View view) {
        EditText name_nm = findViewById(R.id.name);
        final String name_s = name_nm.getText().toString();
        if (name_s.length() <= 4) {
            name_nm.setError("name must contain 4 characters");
            return;
        }

        EditText address_ad = findViewById(R.id.add_id);
        final String address_s = address_ad.getText().toString();

        EditText email_id = findViewById(R.id.email);
        final String email_s = email_id.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email_s).matches()) {
            email_id.setError("please enter valid email");
            return;
        }

        EditText mob_no = findViewById(R.id.mob_id);
        final String mob_s = mob_no.getText().toString();
        if (mob_s.length() <= 10) {
            mob_no.setError("mob no. must contain 10 digits");
        }

        EditText pass_d = findViewById(R.id.pass_id);
        String pass = pass_d.getText().toString();
        if (pass.length() <= 8)
        {
            pass_d.setError("password must contain 8 characters");
        }

        EditText park_sp = findViewById(R.id.p_space);
        final String park = park_sp.getText().toString();

        final ProgressDialog progress_bar = new ProgressDialog(create_sp.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Create account");
        progress_bar.show();

        FirebaseAuth f_auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.hide();

                if (task.isSuccessful()) {
                    createaccount data = new createaccount(name_s, address_s,mob_s, park);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("sp").child(email_s.replace(".","")).setValue(data);
                    Toast.makeText(create_sp.this, "done", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(create_sp.this, Sp_home.class);
                    startActivity(i);
                } else {
                    Toast.makeText(create_sp.this, "error try again", Toast.LENGTH_SHORT).show();
                }
            }
        };

        f_auth.createUserWithEmailAndPassword(email_s, pass).addOnCompleteListener(listener);





    }
}

