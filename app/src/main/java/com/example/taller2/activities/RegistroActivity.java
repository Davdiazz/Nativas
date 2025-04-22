package com.example.taller2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taller2.R;
import com.example.taller2.database.DatabaseHelper;

public class RegistroActivity extends AppCompatActivity {

    EditText editNombre, editCorreo, editContrasena;
    Switch switchRol;
    Button btnCrearCuenta;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        editNombre = findViewById(R.id.editNombre);
        editCorreo = findViewById(R.id.editCorreo);
        editContrasena = findViewById(R.id.editContrasena);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        dbHelper = new DatabaseHelper(this);

        btnCrearCuenta.setOnClickListener(v -> {
            String nombre = editNombre.getText().toString().trim();
            String correo = editCorreo.getText().toString().trim();
            String contrasena = editContrasena.getText().toString().trim();

            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                boolean existe = dbHelper.verificarUsuario(correo, contrasena);
                if (existe) {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                } else {
                    boolean insertado = dbHelper.registrarUsuario(nombre, correo, contrasena);
                    if (insertado) {
                        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
