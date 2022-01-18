package com.example.storetodoore;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {
    EditText EnterOTP;
    Button verifyPhone,resendOTP;
    Boolean otpValid = true;
    FirebaseAuth firebaseAuth;
    PhoneAuthCredential phoneAuthCredential;
    PhoneAuthProvider.ForceResendingToken token;
    String verificationId;
    String  phone;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        Intent data = getIntent();
        phone = data.getStringExtra("phone");
        firebaseAuth = FirebaseAuth.getInstance();
        EnterOTP = findViewById(R.id.EnterOTP);
        verifyPhone = findViewById(R.id.verifyPhoneBTn);
        resendOTP = findViewById(R.id.resendOTP);


        requestSMSPermission();
        new OTPReceiver().setEditText_otp(EnterOTP);


        verifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateField(EnterOTP);

                if(otpValid){
                    // send otp to the user
                    String otp = EnterOTP.getText().toString();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,otp);
                  //  Toast.makeText(VerifyPhone.this, "User Account is Created.", Toast.LENGTH_SHORT).show();


                    verifyAuthentication(credential);

                }
            }


        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;
                token = forceResendingToken;
                resendOTP.setVisibility(View.GONE);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendOTP.setVisibility(View.VISIBLE);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuthentication(phoneAuthCredential);

                resendOTP.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(VerifyPhone.this, "OTP Verification Failed.", Toast.LENGTH_SHORT).show();
            }
        };

        sendOTP(phone);


        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTP(phone);
            }
        });

    }

    private void requestSMSPermission() {
    if (ContextCompat.checkSelfPermission(VerifyPhone.this,Manifest.permission.RECEIVE_SMS)
    != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(VerifyPhone.this,new String[]{
                Manifest.permission.RECEIVE_SMS
        },100);
    }
        }


    public void sendOTP(String phoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS,this,mCallbacks);
    }

    public void resendOTP(String phoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS,this,mCallbacks,token);
    }


    public void validateField(EditText field){
        if(field.getText().toString().isEmpty()){
            field.setError("Required");
            otpValid = false;
        }else {
            otpValid = true;
        }
    }

    public void verifyAuthentication(PhoneAuthCredential credential){
        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
               Toast.makeText(VerifyPhone.this,"User Account is Created. ",Toast.LENGTH_LONG).show();
                startActivity(new Intent(VerifyPhone.this,Dashboard.class));
            }
        });

    }


}