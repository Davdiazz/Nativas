package com.example.taller2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taller2.R;
import com.example.taller2.database.DatabaseHelper;

public class RecuperarContrasenaActivity extends AppCompatActivity {

    EditText editCorreo, editNuevaContrasena;
    Button btnActualizarContrasena;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        editCorreo = findViewById(R.id.editCorreo);
        editNuevaContrasena = findViewById(R.id.editNuevaContrasena);
        btnActualizarContrasena = findViewById(R.id.btnActualizarContrasena);

        dbHelper = new DatabaseHelper(this);

        btnActualizarContrasena.setOnClickListener(v -> {
            String correo = editCorreo.getText().toString().trim();
            String nuevaContrasena = editNuevaContrasena.getText().toString().trim();

            if (correo.isEmpty() || nuevaContrasena.isEmpty()) {
                Toast.makeText(this, "Completa ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean usuarioExiste = dbHelper.existeCorreo(correo);
            if (usuarioExiste) {
                boolean actualizado = dbHelper.actualizarContrasena(correo, nuevaContrasena);
                if (actualizado) {
                    Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Error al actualizar contraseña", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Correo no encontrado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
