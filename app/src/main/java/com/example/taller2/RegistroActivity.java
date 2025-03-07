package com.example.taller2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private EditText editTextNombres;
    private Button buttonRegistro;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        android.util.Log.d("RegistroActivity", "onCreate: Inicializando el Activity de Registro");

        editTextNombres = findViewById(R.id.etnombre_Register);
        buttonRegistro = findViewById(R.id.btn_registro);

        sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        buttonRegistro.setOnClickListener(view -> {
            if (validarCampos()) {
                guardarDatos();
            }
        });
    }

    private boolean validarCampos() {
        String nombres = editTextNombres.getText().toString().trim();

        if (nombres.isEmpty()) {
            Toast.makeText(this, "El campo nombres es requerido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void guardarDatos() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombres", editTextNombres.getText().toString().trim());
        editor.apply();
        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();
    }
}