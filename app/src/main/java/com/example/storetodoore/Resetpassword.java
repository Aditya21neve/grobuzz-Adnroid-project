package com.example.storetodoore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class Resetpassword extends AppCompatActivity {
    EditText registeremail;
    Button resetpasswordbtn;
    TextView goback,emailicontext;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        registeremail = findViewById(R.id.forgot_passwoed_email);
        resetpasswordbtn = findViewById(R.id.forgotPassword_resetbtn);
        goback = findViewById(R.id.forgot_password_gpback);
        fAuth = FirebaseAuth.getInstance();



        resetpasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = registeremail.getText().toString();

                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Resetpassword.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Resetpassword.this,Login.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Resetpassword.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Resetpassword.this, Login.class));

            }
                    });

            }

    }



