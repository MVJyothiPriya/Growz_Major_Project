package com.example.navigationapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        EditText title= findViewById(R.id.titleId);
        EditText message= findViewById(R.id.messageId);
        findViewById(R.id.alldeviceId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().toString().isEmpty() && !message.getText().toString().isEmpty()){
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",title.getText().toString(), message.getText().toString(), getApplicationContext(), Notification.this);
                    notificationsSender.SendNotifications();
                }else{
                    Toast.makeText(Notification.this, "Write some text",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}