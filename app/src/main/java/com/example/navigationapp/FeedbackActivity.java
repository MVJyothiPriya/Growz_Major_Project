package com.example.navigationapp;
import static com.example.navigationapp.MainActivity.closeDrawer;
import static com.example.navigationapp.MainActivity.openDrawer;
import static com.example.navigationapp.MainActivity.redirectActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.os.Bundle;


public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG = "FeedbackActivity";
    private EditText editTextName, editTextEmail, editTextFeedback;
    private Button submitButton;
    private DatabaseReference databaseReference;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        drawerLayout =findViewById(R.id.drawer_layout);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFeedback = findViewById(R.id.editTextFeedback);
        submitButton = findViewById(R.id.submitButton);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

    }

    private void submitFeedback() {
        try {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String feedback = editTextFeedback.getText().toString().trim();

            // Check if any field is empty
            if (name.isEmpty() || email.isEmpty() || feedback.isEmpty()) {
                Log.e(TAG, "One or more fields are empty.");
                return;
            }

            // Push data to Firebase database
            String key = databaseReference.push().getKey();
            if (key != null) {
                DatabaseReference feedbackRef = databaseReference.child(key);
                feedbackRef.child("Name").setValue(name);
                feedbackRef.child("Email").setValue(email);
                feedbackRef.child("Feedback").setValue(feedback);
            }

            // Clear input fields after submission
            editTextName.setText("");
            editTextEmail.setText("");
            editTextFeedback.setText("");
        } catch (Exception e) {
            Log.e(TAG, "Error submitting feedback: " + e.getMessage());
        }
    }
    // MENU OPENS
    public void ClickMenu(View view) {

        openDrawer(drawerLayout);
    }

    //LOGO OPENS
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }


    public void Clickhome(View view) {

        redirectActivity(this, MainActivity.class);
    }    public void ClickSurveyform(View view){
        redirectActivity(this, CropPrediction.class);

    }
    // check status opens
    public void ClickCheckstatus(View view){
        redirectActivity(this, PlantDisease.class);

    }
    //complaintbox opens
    public void ClickComplaintbox(View view){
        redirectActivity(this, FertilizerRecommendation.class);

    }
    //Crop yield
    public void ClickCropYield(View view){
        redirectActivity(this, CropYield.class);

    }
    public void ClickLocationBased(View view){
        redirectActivity(this, LocationBased.class);

    }
    public void ClickDemand(View view){
        redirectActivity(this, Demand.class);

    }

    //feedback opens
    public void Clickfeedback(View view){
        redirectActivity(this, FeedbackActivity.class);

    }
    public void ClickLogout(View view)
    {
        MainActivity.ClickLogout(this);
    }

    /////
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
    ////
}