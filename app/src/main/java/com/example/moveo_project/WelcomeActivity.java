package com.example.moveo_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Timer time;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvWelcome = findViewById(R.id.welcome);


        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.activity_welcome);
            mAuth.getCurrentUser().getEmail();

            time = new Timer();
            mAuth = FirebaseAuth.getInstance();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent;
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null) {
            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);

        }
    }

}