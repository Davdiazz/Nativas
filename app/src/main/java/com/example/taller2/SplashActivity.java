package com.example.taller2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @SuppressLint("MissingSuperCall")
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() ->{
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            Log.d("SpalshActivity", "onCreate: Redirigiendo a HomeActivity");
            finish();
        },1500);
    }
}
