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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class user_createacc extends AppCompatActivity {

    EditText address_n ;

    private String lat , lng ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_createacc);

        address_n = findViewById(R.id.add_id);

        address_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult( new Intent(user_createacc.this , PlacePickerActivity.class) , 100);
            }
        });
    }

    public void createacc(View view)
    {
        EditText name_n = findViewById(R.id.name);
        final String name_s = name_n.getText().toString();
        if (name_s.length() <= 3) {
            name_n.setError("must contain 4 characters");
            return;
        }

        EditText mob_n = findViewById(R.id.mob_id);
        final String mob_s = mob_n.getText().toString();
        if (mob_s.length() < 9) {
            mob_n.setError("must contain 10 characters");
            return;
        }



        final String address_s = address_n.getText().toString();
        if (address_s.length() <= 3 ) {
            address_n.setError("must contain 4 characters");
            return;
        }


        EditText pass_n = findViewById(R.id.pass_id);
        final String pass_s = pass_n.getText().toString();
        if (pass_s.length()<7)
        {
            pass_n.setError("must contain 8 characters");
            return;
        }


        EditText email_id = findViewById(R.id.email);
        final String email_s = email_id.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email_s).matches()) {
            email_id.setError("please enter valid email");
            return;
        }



        final ProgressDialog progress_bar = new ProgressDialog(user_createacc.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Create account");
        progress_bar.show();

        FirebaseAuth f_auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.hide();

                if (task.isSuccessful()) {
                    user_create_account data = new user_create_account(name_s, address_s,mob_s , lat , lng);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("user_details").child(email_s.replace(".","")).setValue(data);
                    Toast.makeText(user_createacc.this, "done", Toast.LENGTH_SHORT).show();

                    send_link();

                } else {
                    Toast.makeText(user_createacc.this, "error try again", Toast.LENGTH_SHORT).show();
                }
            }
        };

        f_auth.createUserWithEmailAndPassword(email_s, pass_s).addOnCompleteListener(listener);

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100)
        {
            address_n.setText(data.getStringExtra("place"));

            lat = data.getStringExtra("lat");

            lng = data.getStringExtra("lng");

        }
    }

    private void send_link()
    {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();

                            finish();

                        }

                    }
                });
    }


    }

