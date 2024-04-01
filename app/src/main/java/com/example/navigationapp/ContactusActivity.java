package com.example.navigationapp;

import static com.example.navigationapp.MainActivity.closeDrawer;
import static com.example.navigationapp.MainActivity.openDrawer;
import static com.example.navigationapp.MainActivity.redirectActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ContactusActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        drawerLayout = findViewById(R.id.drawer_layout);
        webView = findViewById(R.id.webview_contact_us);

        // Enable JavaScript (if needed)
        webView.getSettings().setJavaScriptEnabled(true);

        // Load HTML content or webpage URL
        webView.loadUrl("file:///android_asset/contact_us.html");

        // Set WebView client to handle page navigation within the WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle error
                Toast.makeText(ContactusActivity.this, "Error loading page", Toast.LENGTH_SHORT).show();
            }
        });


    }
}