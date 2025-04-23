package com.example.taller2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taller2.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }
}
