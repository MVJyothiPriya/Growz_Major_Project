package com.example.navigationapp;

import static com.example.navigationapp.MainActivity.closeDrawer;
import static com.example.navigationapp.MainActivity.openDrawer;
import static com.example.navigationapp.MainActivity.redirectActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class Registration extends AppCompatActivity {
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        drawerLayout =findViewById(R.id.drawer_layout);

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
        recreate();
    }
    public void ClickSurveyform(View view){
        redirectActivity(this, SurveyformActivity.class);

    }
    // check status opens
    public void ClickCheckstatus(View view){
        redirectActivity(this, CheckstatusActivity.class);

    }
    //complaintbox opens
    public void ClickComplaintbox(View view){
        redirectActivity(this, ComplaintboxActivity.class);

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