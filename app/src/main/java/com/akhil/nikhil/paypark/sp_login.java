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
import com.google.firebase.database.FirebaseDatabase;

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

        EditText email_et = findViewById(R.id.email);

        String email_s = email_et.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email_s).matches()) {
            email_et.setError("please enter valid email");
            return;
        }

            EditText password_pt = findViewById(R.id.pass_id);
            String password_s = password_pt.getText().toString();
            if (password_s.length()<= 8) {
            password_pt.setError("password must contain 8 characters");
            }

            final ProgressDialog progress_bar = new ProgressDialog(sp_login.this);
            progress_bar.setTitle("please wait");
            progress_bar.setMessage("Create account");
            progress_bar.show();

            FirebaseAuth f_auth = FirebaseAuth.getInstance();

            OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progress_bar.hide();

                    if(task.isSuccessful()) {
                        Intent i = new Intent(sp_login.this, Sp_home.class);
                        startActivity(i);
                    }

                }
            };

            f_auth.signInWithEmailAndPassword( email_s, password_s).addOnCompleteListener(listener);



        }
}


