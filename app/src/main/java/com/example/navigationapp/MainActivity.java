package com.example.navigationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
//
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);

    }
    //
    public void ClickMenu(View view) {

        openDrawer(drawerLayout);
    }
//  drawer OPENS WHEN METHOD USED
    public static  void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    //CLOSE DRAWER WHEN CLICKED ON LOGO

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){  // checks open or not
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        //when drawer is closed automatically it opens the home page



    }
    public void Clickhome(View view) {

        redirectActivity(this, MainActivity.class);
    }// survey form opens
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
    public void ClickLocationBased(View view){
        redirectActivity(this, LocationBased.class);

    }
    //Crop yield
    public void ClickCropYield(View view){
        redirectActivity(this, CropYield.class);

    }

    //feedback opens
    public void Clickfeedback(View view){
        redirectActivity(this, FeedbackActivity.class);

    }
//Contact us
    public void openContactUs(View view){
        redirectActivity(this, ContactusActivity.class);
    }

    //About usopenAboutUs
    public void openAboutUs(View view){
        redirectActivity(this, AboutusActivity.class);
    }

//openUserLogin
    public void openUserLogin(View view){
        redirectActivity(this, Userlogin.class);
    }
    //openOfficerDesk
    public void openOfficerDesk(View view){
        redirectActivity(this, Officerdesk.class);
    }
    //registration




    //LOGOUT


    ///////////

    public static void ClickLogout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    // redirected to pages
    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


@Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}