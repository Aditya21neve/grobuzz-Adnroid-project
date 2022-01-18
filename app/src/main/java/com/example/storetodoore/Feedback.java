package com.example.storetodoore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {
    ImageButton back;
    Button send;
    EditText edfeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        back =findViewById(R.id.btnfeedbackback);
        send = findViewById(R.id.send_feedback);
        edfeedback =findViewById(R.id.edtxtfeedback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Feedback.this,Dashboard.class));

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Feed = edfeedback.getText().toString().trim();
                Feed feed = new Feed(Feed);
                FirebaseDatabase.getInstance().getReference("Feedback")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(feed).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Feedback.this,"Feedback is Submitted",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Feedback.this,Dashboard.class));
                    }
                });


            }
        });
    }
}