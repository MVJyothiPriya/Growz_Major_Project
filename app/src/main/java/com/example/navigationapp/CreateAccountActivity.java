package com.example.navigationapp;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText aadharNumberEditText, passwordEditText;
    private Button createAccountButton;
    private TextView loginText;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

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
        final String aadharNumber = aadharNumberEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(aadharNumber) || TextUtils.isEmpty(password)) {
            Toast.makeText(CreateAccountActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Create user with email and password
        firebaseAuth.createUserWithEmailAndPassword(aadharNumber + "@example.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-up user's information
                            Toast.makeText(CreateAccountActivity.this, "Account created successfully.", Toast.LENGTH_SHORT).show();

                            // Store Aadhar number in Firebase Database
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                String userId = ((FirebaseUser) user).getUid();
                                databaseReference.child(userId).child("aadharNumber").setValue(aadharNumber);
                            }

                            // Redirect to the login activity
                            Intent intent = new Intent(CreateAccountActivity.this, LOGIN.class);
                            startActivity(intent);
                            finish(); // Close the current activity
                        } else {
                            // If sign-up fails, display a message to the user.
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Firebase Auth", "Authentication failed: " + task.getException().getMessage());
                        }
                    }
                });
    }
}
