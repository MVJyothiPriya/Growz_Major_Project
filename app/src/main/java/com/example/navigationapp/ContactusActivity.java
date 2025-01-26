package com.example.navigationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Locale;

public class ContactusActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();

        setContentView(R.layout.activity_contactus);
        drawerLayout = findViewById(R.id.drawer_layout);
        webView = findViewById(R.id.webview_contact_us);

        // Enable JavaScript (if needed)
        webView.getSettings().setJavaScriptEnabled(true);

        // Load HTML content based on selected language
        String language = getLanguagePreference();
        loadHtmlContent(language);
    }

    private String getLanguagePreference() {
        // Retrieve the language preference from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        // Default language is English if no preference is set
        return sharedPreferences.getString("My_Lang", "en");
    }

    private void loadHtmlContent(String language) {
        String htmlFileName;
        switch (language) {
            case "te":
                htmlFileName = "contactuste.html"; // Telugu
                break;
            case "hi":
                htmlFileName = "contactuss.html"; // Hindi
                break;
            default:
                htmlFileName = "contact_us.html"; // Default to English
        }
        String htmlFilePath = "file:///android_asset/" + htmlFileName;
        // Load the HTML file into the WebView
        webView.loadUrl(htmlFilePath);

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

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
