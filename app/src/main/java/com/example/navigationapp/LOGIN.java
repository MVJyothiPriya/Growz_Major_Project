package com.example.navigationapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navigationapp.ForgotPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class LOGIN extends AppCompatActivity {

    private EditText aadharNumberEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordText;
    private TextView createAccountText;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        aadharNumberEditText = findViewById(R.id.AadharNumber);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.LoginButton);
        forgotPasswordText = findViewById(R.id.ForgotPasswordText);
        createAccountText = findViewById(R.id.CreateAccountText);
        progressBar = findViewById(R.id.progressBar);

        // Set click listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LOGIN.this, ForgotPasswordActivity.class));
            }
        });

        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LOGIN.this, CreateAccountActivity.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, redirect to the home screen or another activity
            // For now, let's display a toast message to indicate the user is already logged in
            Toast.makeText(LOGIN.this, "Already logged in as " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        String aadharNumber = aadharNumberEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate input fields
        if (aadharNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(LOGIN.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Check if the user exists with the given Aadhar number
        firebaseAuth.signInWithEmailAndPassword(aadharNumber + "@aadhar.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                Toast.makeText(LOGIN.this, "Login successful.", Toast.LENGTH_SHORT).show();


                                if (aadharNumber.equals("258025802580") && password.equals("#Hello1")) {
                                    // Redirect to AyzActivity
                                    startActivity(new Intent(LOGIN.this, Notification.class));
                                    finish(); // Finish the MainActivity to prevent going back to it when pressing back button from AyzActivity
                                }
                                else {
                                    Intent intent = new Intent(LOGIN.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    // Optional: finish the current activity if you don't want to come back to it with the back button

                                }


                            } else {
                                Toast.makeText(LOGIN.this, "User is null.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LOGIN.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}