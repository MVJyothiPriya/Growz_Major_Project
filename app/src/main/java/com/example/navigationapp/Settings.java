package com.example.navigationapp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import static com.example.navigationapp.MainActivity.closeDrawer;
import static com.example.navigationapp.MainActivity.openDrawer;
import static com.example.navigationapp.MainActivity.redirectActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();

        setContentView(R.layout.activity_settings);
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.app_name));
        }

        Button changeMyLang = findViewById(R.id.changeMyLang);
        changeMyLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"Telugu", "Hindi", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings.this);
        mBuilder.setTitle("Choose language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    // Telugu
                    setLocale("te");
                } else if (i == 1) {
                    // Hindi
                    setLocale("hi");
                } else if (i == 2) {
                    // English
                    setLocale("en");
                }

                // Pass selected language back to the previous activity if necessary
                Intent intent = new Intent();
                intent.putExtra("selected_language", listItems[i]);
                setResult(RESULT_OK, intent);

                recreate();
                dialog.dismiss();
            }
        });

        AlertDialog mdialog = mBuilder.create();
        mdialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }



    public void ClickSettings(View view) {
        // Do nothing or handle the click event as needed
    }

    public void ClickMenu(View view) {

        openDrawer(drawerLayout);
    }

    //LOGO OPENS
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }


    public void Clickhome(View view) {

        redirectActivity(this, MainActivity.class);
    }
    public void ClickSurveyform(View view){
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

    public void ClickDemand(View view){
        redirectActivity(this, Demand.class);

    }

    //Crop yield
    public void ClickCropYield(View view){
        redirectActivity(this, CropYield.class);

    }
    public void ClickLocationBased(View view){
        redirectActivity(this, LocationBased.class);

    }

    //feedback opens
    public void Clickfeedback(View view){
        redirectActivity(this, FeedbackActivity.class);

    }
    public void ClickLogout(View view)
    {
        MainActivity.ClickLogout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }////
}