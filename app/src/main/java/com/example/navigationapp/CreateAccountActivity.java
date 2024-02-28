package com.example.navigationapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {
    EditText aadharNumberEditText, createPasswordEditText, confirmPasswordEditText;
    Button submitButton;
    TextView backToLoginTextView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        aadharNumberEditText = findViewById(R.id.AadharNumber);
        createPasswordEditText = findViewById(R.id.CreatePassword);
        confirmPasswordEditText = findViewById(R.id.ConfirmPassword);
        submitButton = findViewById(R.id.SubmitButton);
        backToLoginTextView = findViewById(R.id.BackToLoginText);

        // Set click listener for Submit Button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String aadharNumber = aadharNumberEditText.getText().toString();
                String createPassword = createPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Validate input
                if (aadharNumber.length() != 12 || !aadharNumber.matches("[0-9]+")) {
                    Toast.makeText(CreateAccountActivity.this, "Please enter a valid 12-digit Aadhar number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (createPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "Please fill in all password fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!createPassword.equals(confirmPassword)) {
                    Toast.makeText(CreateAccountActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create user with Aadhar number and password
                mAuth.createUserWithEmailAndPassword(aadharNumber + "@aadhar.com", createPassword)
                        .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                    // You can add additional logic here, like saving user details to Firebase Database
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(CreateAccountActivity.this, "Authentication failed." + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Set click listener for Back to Login TextView
        backToLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to login activity
                Intent intent = new Intent(CreateAccountActivity.this, LOGIN.class);
                startActivity(intent);
                finish(); // Close current activity
            }
        });
    }
}