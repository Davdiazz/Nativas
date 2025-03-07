package com.example.taller2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView TextViewRegistro;
    private Button ButtonComienza;

    @SuppressLint("MissingSuperCall")
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_home);

        // Variables
        TextViewRegistro = findViewById(R.id.tvRegister_Home);
        ButtonComienza = findViewById(R.id.btnStart_Home);

        // Metodos Redireccion
        TextViewRegistro.setOnClickListener(v -> {
            // Redireccionar a login
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            Log.d("HomeActivity","onClick: Redirigiendo a RegisterActivity");
            finish();
        });

        ButtonComienza.setOnClickListener(v -> {
            // Redireccionar a login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            Log.d("HomeActivity","onClick: Redirigiendo a LoginActivity");
            finish();
        });
    }
}
