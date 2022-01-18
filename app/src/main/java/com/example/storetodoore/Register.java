package com.example.storetodoore;

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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mPhone,mcountry,mhome,mlandmark,marea;
    Button mRegisterBtn;
    TextView maccount;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.registerPhoneNumber);
        mRegisterBtn = findViewById(R.id.registerBtn);
        maccount = findViewById(R.id.txtalreadylogin);
        mcountry = findViewById(R.id.countryCode);
        mhome = findViewById(R.id.home);
        mlandmark = findViewById(R.id.landmark);
        marea = findViewById(R.id.area);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }



        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");
                String fullname = mFullName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String home = mhome.getText().toString().trim();
                String area = marea.getText().toString().trim();
                String landmark = mlandmark.getText().toString().trim();
                String country = mcountry.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();





                if (TextUtils.isEmpty(fullname)) {
                    mFullName.setError("Name is Required.");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    mPhone.setError("Phone number is Required");
                    return;
                }
                if (phone.length() != 10) {
                    mPhone.setError("Enter Correct Number");
                    return;
                }

                if (TextUtils.isEmpty(home)) {
                    mhome.setError("Address is Required.");
                    return;
                }
                if (TextUtils.isEmpty(landmark)) {
                    mlandmark.setError("Landmark is Required.");
                    return;
                }
                if (TextUtils.isEmpty(area)) {
                    marea.setError("Area is Required.");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                //mRegisterBtn.setEnabled(false);
                mRegisterBtn.setVisibility(View.GONE);
                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    User user = new User( fullname , email , home , area , landmark , phone );
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent phone = new Intent(Register.this, VerifyPhone.class);
                                            phone.putExtra("phone", "+" + mcountry.getText().toString() + mPhone.getText().toString());
                                            startActivity(phone);
                                            Log.d(TAG, "onSuccess: " + "+" + mcountry.getText().toString() + mPhone.getText().toString());
                                            if (task.isSuccessful()){
                                                Toast.makeText(Register.this,"User has Reigster Successfil",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.VISIBLE);
                                                mRegisterBtn.setVisibility(View.GONE);
                                            }
                                            else {
                                                String error = task.getException().getMessage();

                                                Toast.makeText(Register.this,error,Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                //mRegisterBtn.setEnabled(true);
                                                mRegisterBtn.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(Register.this,"User has Reigster Failed",Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    //mRegisterBtn.setEnabled(true);
                                    mRegisterBtn.setVisibility(View.VISIBLE);

                                }

                            }
                        });
                maccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Register.this,Login.class));
                    }
                });
            }
        });
    }
}