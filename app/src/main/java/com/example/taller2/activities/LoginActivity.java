package com.example.taller2.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taller2.R;
import com.example.taller2.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText editCorreo, editContrasena;
    Button btnLogin, btnRegistrarse;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editCorreo = findViewById(R.id.editCorreo);
        editContrasena = findViewById(R.id.editContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        dbHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(v -> {
            String correo = editCorreo.getText().toString().trim();
            String contrasena = editContrasena.getText().toString().trim();

            if (dbHelper.verificarUsuario(correo, contrasena)) {
                boolean esAdmin = dbHelper.obtenerRol(correo);

                if (esAdmin) {
                    Toast.makeText(LoginActivity.this, "Bienvenido administrador", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Bienvenido usuario", Toast.LENGTH_SHORT).show();
                }

                // Ir al MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistrarse.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
        Button btnRecuperarContrasena = findViewById(R.id.btnRecuperarContrasena);
        btnRecuperarContrasena.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RecuperarContrasenaActivity.class));
        });
    }
}