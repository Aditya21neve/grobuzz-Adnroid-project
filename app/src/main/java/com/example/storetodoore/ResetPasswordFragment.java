package com.example.storetodoore;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends Fragment {

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    private EditText registedEmail;
    private Button resetPasswordBtn;
    private TextView goBack;
    private FrameLayout prantFrameLayoout;
    private FirebaseAuth firebaseAuth;

    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        registedEmail = view.findViewById(R.id.forgot_passwoed_email);
        resetPasswordBtn = view.findViewById(R.id.forgotPassword_resetbtn);
        goBack = view.findViewById(R.id.forgot_password_gpback);
        prantFrameLayoout = getActivity().findViewById(R.id.register_Freaelayout);
        firebaseAuth = FirebaseAuth.getInstance();
        emailIconContainer = view.findViewById(R.id.forgotPassword_email_icon_container);
        emailIcon = view.findViewById(R.id.forgotpassword_email_icon);
        emailIconText = view.findViewById(R.id.forgotPassword_emailIcon_text);
        progressBar = view.findViewById(R.id.forgotPassword_progressBar);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registedEmail.addTextChangedListener(new TextWatcher() {
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
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragement(new SignInFragment());
            }
        });
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIconText.setVisibility(View.GONE);
                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                resetPasswordBtn.setEnabled(false);
                resetPasswordBtn.setTextColor(Color.argb(50, 255, 255, 255));
                firebaseAuth.sendPasswordResetEmail(registedEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, emailIcon.getWidth() / 2, emailIcon.getHeight() / 2);
                                    scaleAnimation.setDuration(100);
                                    scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                                    scaleAnimation.setRepeatMode(Animation.REVERSE);
                                    scaleAnimation.setRepeatCount(1);
                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            emailIconText.setVisibility(View.VISIBLE);
                                            emailIconText.setText("Recovery email sent successfully ! check your inbox");
                                            emailIconText.setTextColor(getResources().getColor(R.color.Green));

                                            TransitionManager.beginDelayedTransition(emailIconContainer);
                                            emailIcon.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                            emailIcon.setImageResource(R.drawable.greenemail);

                                        }
                                    });
                                    emailIcon.startAnimation(scaleAnimation);
                                    //Toast.makeText(getActivity(), "Email Send Successfull", Toast.LENGTH_LONG).show();
                                } else {
                                    String error = task.getException().getMessage();

                                    emailIconText.setText(error);
                                    emailIconText.setTextColor(getResources().getColor(R.color.Red));
                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    emailIconText.setVisibility(View.VISIBLE);

                                }
                                progressBar.setVisibility(View.GONE);
                                resetPasswordBtn.setEnabled(true);
                                resetPasswordBtn.setTextColor(Color.rgb(255, 255, 255));
                            }

                        });
            }
        });
    }

    private void checkInputes() {
        if (TextUtils.isEmpty(registedEmail.getText())) {
            resetPasswordBtn.setEnabled(false);
            resetPasswordBtn.setTextColor(Color.argb(50, 255, 255, 255));
        } else {
            resetPasswordBtn.setEnabled(true);
            resetPasswordBtn.setTextColor(Color.rgb(255, 255, 255));

        }
    }

    private void setFragement(Fragment fragement) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_form_left, R.anim.slide_out_from_right);
        fragmentTransaction.replace(prantFrameLayoout.getId(), fragement);
        fragmentTransaction.commit();
    }

}