package com.example.storetodoore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mcreate,forgotpassword;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    private Button skip;
    public static  boolean disableCloseBtn = false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
        mcreate = findViewById(R.id.txtcreate);
        forgotpassword = findViewById(R.id.forgotPassword);
        skip = findViewById(R.id.skip);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                if (disableCloseBtn ){
                    skip.setVisibility(View.GONE);
                }else {
                    skip.setVisibility(View.VISIBLE);
                }

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            mainIntent();
                        }else {
                            String problem = task.getException().getMessage();
                            Toast.makeText(Login.this,problem, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            mLoginBtn.setVisibility(View.VISIBLE);
                        }

                    }
                });

            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Resetpassword.class));
            }
        });





        mcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
     
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity( new Intent(Login.this,MainActivity.class));
            }
        });


    }

    private void mainIntent() {
        if (disableCloseBtn){
            disableCloseBtn = false;
        }else {
            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(Login.this,MainActivity.class);
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            startActivity(mainIntent);
        }
        finish();
    }

}