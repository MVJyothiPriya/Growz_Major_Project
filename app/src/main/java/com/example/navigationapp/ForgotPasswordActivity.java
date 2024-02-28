package com.example.navigationapp;


import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText aadharNumberEditText, newPasswordEditText, confirmPasswordEditText;
    private Button submitButton;
    private TextView backToLoginText;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        mAuth = FirebaseAuth.getInstance();

        aadharNumberEditText = findViewById(R.id.AadharNumber);
        newPasswordEditText = findViewById(R.id.NewPassword);
        confirmPasswordEditText = findViewById(R.id.ConfirmPassword);
        submitButton = findViewById(R.id.SubmitButton);
        backToLoginText = findViewById(R.id.BackToLoginText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

        backToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the login activity
                Intent intent = new Intent(ForgotPasswordActivity.this, LOGIN.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }

    private void updatePassword() {
        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(ForgotPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.getCurrentUser().updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                            // Display reset successful message
                            Toast.makeText(ForgotPasswordActivity.this, "Reset successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}