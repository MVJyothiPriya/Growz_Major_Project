package com.example.navigationapp;

import static com.example.navigationapp.MainActivity.closeDrawer;
import static com.example.navigationapp.MainActivity.openDrawer;
import static com.example.navigationapp.MainActivity.redirectActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class CropYield extends AppCompatActivity {
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropyield);
        drawerLayout =findViewById(R.id.drawer_layout);
        WebView webview=findViewById(R.id.webyield);
        WebSettings webSettings = webview.getSettings();
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("http://16.171.70.35:8080/yield");

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