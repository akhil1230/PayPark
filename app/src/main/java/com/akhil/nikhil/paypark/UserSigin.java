package com.akhil.nikhil.paypark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class UserSigin extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;

    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sign);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void createacc(View view) {

        Intent i = new Intent(UserSigin.this,user_createacc.class);
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
        if (password_s.length()< 7) {
            password_pt.setError("password must contain 8 characters");
            return;
        }

        final ProgressDialog progress_bar = new ProgressDialog(UserSigin.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Loading");
        progress_bar.show();

        FirebaseAuth f_auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.hide();

                if(task.isSuccessful()) {
                    Intent i = new Intent(UserSigin.this, user_home_page.class);
                    startActivity(i);
                    finish();
                }

                else {
                    Toast.makeText(UserSigin.this , "invalid credentials" , Toast.LENGTH_SHORT).show();
                }

            }
        };

        f_auth.signInWithEmailAndPassword( email_s , password_s ).addOnCompleteListener(listener);



    }

    public void googlein(View view)
    {
        FirebaseUser user = mAuth.getCurrentUser();

        Intent i = new Intent(UserSigin.this,user_home_page.class);
        startActivity(i);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("16631950796-24ddnuu116b07fi5755jp7l7fvlvjjv0.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this , gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }
    private void signIn() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential;
        credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent i = new Intent(UserSigin.this,user_home_page.class);
                            startActivity(i);
                        }
                        else
                            {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }

    public void forget_pass(View view) {

        EditText email = findViewById(R.id.email);
        String email_s =email.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email_s).matches()) {
            email.setError("please enter valid email");
            return;}
        FirebaseAuth Auth = FirebaseAuth.getInstance();
        Auth.sendPasswordResetEmail(email_s).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(UserSigin.this,"password link sent",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
