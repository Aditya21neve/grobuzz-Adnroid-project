package com.example.storetodoore;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.IInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.storetodoore.Register1.onResetPasswordFragment;


public class SignInFragment extends Fragment {


    public SignInFragment() {

    }

    private TextView dontHaveAnAccount;
    private TextView forgotPassword;

    private FrameLayout prantFrameLayoout;

    private EditText email;
    private EditText password;
    private ImageButton closeBtn;
    private ProgressBar progressBar;
    private Button signInBtn;
    private FirebaseAuth firebaseAuth;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.donthaveanaccount);
        prantFrameLayoout = getActivity().findViewById(R.id.register_Freaelayout);
        email = view.findViewById(R.id.signIn_email);
        password = view.findViewById(R.id.signIn_Password);
        closeBtn = view.findViewById(R.id.sign_in_close_Btn);
        signInBtn = view.findViewById(R.id.SignUp1_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.signIn_progressBar7);
        forgotPassword = view.findViewById(R.id.signIn_forgot_password);
        if (disableCloseBtn) {
            closeBtn.setVisibility(View.GONE);
        } else {
            closeBtn.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResetPasswordFragment = true;
                setFragement(new ResetPasswordFragment());
            }
        });
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragement(new SignUpFragment());

            }

        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputes();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputes();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });
    }

    private void checkEmailAndPassword() {
        if (email.getText().toString().matches(emailpattern)) {
            if (password.length() >= 8) {
                progressBar.setVisibility(View.VISIBLE);
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mainIntent();
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signInBtn.setEnabled(true);
                                    signInBtn.setTextColor(Color.rgb(255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInputes() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(password.getText())) {
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.rgb(255, 255, 255));
            } else {
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signInBtn.setEnabled(true);
            signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void setFragement(Fragment fragement) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_out_from_left);
        fragmentTransaction.replace(prantFrameLayoout.getId(), fragement);
        fragmentTransaction.commit();
    }

    private void mainIntent() {
        if (disableCloseBtn) {
            disableCloseBtn = false;

        } else {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);
        }
        getActivity().finish();

    }
}