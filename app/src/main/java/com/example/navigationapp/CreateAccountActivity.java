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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText aadharNumberEditText, passwordEditText;
    private Button createAccountButton;
    private TextView loginText;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        aadharNumberEditText = findViewById(R.id.AadharNumber);
        passwordEditText = findViewById(R.id.CreatePassword);
        createAccountButton = findViewById(R.id.SubmitButton);
        loginText = findViewById(R.id.BackToLoginText);
        progressBar = findViewById(R.id.progressBar);

        // Set click listeners
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the login activity
                Intent intent = new Intent(CreateAccountActivity.this, LOGIN.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }

    private void createAccount() {
        String aadharNumber = aadharNumberEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate input fields
        if (aadharNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(CreateAccountActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Create user with email and password
        firebaseAuth.createUserWithEmailAndPassword(aadharNumber + "@example.com", password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Sign up success, update UI with the signed-up user's information
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(CreateAccountActivity.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                            // Redirect to the home screen or another activity
                            // For now, let's display a toast message to indicate the user is signed up
                            Toast.makeText(CreateAccountActivity.this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "User is null.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // If sign-up fails, display a message to the user.
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
