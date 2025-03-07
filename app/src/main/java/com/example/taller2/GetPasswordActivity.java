package com.example.taller2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class GetPasswordActivity extends AppCompatActivity {

    private ImageView imageViewBack;
    private EditText editTextEmail;
    private Button buttonSent;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingSuperCall")
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_getpassword);

        imageViewBack = findViewById(R.id.ivBackIcon_getPassword);
        editTextEmail = findViewById(R.id.etEmail_GetPassword);
        buttonSent = findViewById(R.id.btnSent_GetPassword);

        // Archivo de almacenamiento local
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        buttonSent.setOnClickListener(v -> {
            if(validateEmail()){
                new Handler().postDelayed(() ->{
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    Log.d("GetPasswordActivity","onClick: Redirigiendo a LoginActivity");
                    finish();
                },1000);

                MotionToast.Companion.darkColorToast(
                        this,
                        "¡Envio Exitoso!",
                        "Se ha enviado el mensaje para que recuperes tu contraseña",
                        MotionToastStyle.SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        null
                );
            }else{
                MotionToast.Companion.darkColorToast(
                        this,
                        "Error",
                        "El email ingresado no esta registrado, Intenta de Nuevo",
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        null
                );
            }
        });

        imageViewBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            Log.d("GetPasswordActivity","onClick: Redirigiendo a LoginActivity");
            finish();
        });

    }

    //Ejemplo de como llamar a los datos
    private Boolean validateEmail(){
        String email = sharedPreferences.getString("Email","");
        String emailsave = editTextEmail.getText().toString().trim();
        Log.d("GetPasswordActivity","validateEmail: Validando si existe el email");
        if(emailsave.equals(email)){
            return true;
        }
        return false;
    };
}
