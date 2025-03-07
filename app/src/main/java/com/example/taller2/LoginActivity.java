package com.example.taller2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonEnter;
    private TextView buttonGetPassword;
    private Button buttonGoogle;
    private TextView buttonRegister;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingSuperCall")
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.etUsername_Login);
        editTextPassword = findViewById(R.id.etPassword_Login);
        buttonEnter = findViewById(R.id.btnEnter_Login);
        buttonGetPassword = findViewById(R.id.tvGetPassword_Login);
        buttonGoogle = findViewById(R.id.btnGoogle_Login);
        buttonRegister = findViewById(R.id.tvRegister_Login);

        // Archivo de almacenamiento local
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        buttonGetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this, GetPasswordActivity.class);
            startActivity(intent);
            Log.d("LoginActivity","onClik: Redirigiendo a GetPasswordActivity");
            finish();
        });

        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            Log.d("LoginActivity","onClik: Redirigiendo a RegisterActivity");
            finish();
        });

        buttonEnter.setOnClickListener(v -> {
            if(validateLogin()){
                new Handler().postDelayed(() ->{
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                    Log.d("LoginActivity","onClik: Redirigiendo a ProfileActivity");
                    finish();
                },1000);

                MotionToast.Companion.darkColorToast(
                        this,
                        "¡Inicio de Sesion Exitoso!",
                        "Tu usuario y contraseña son correctas",
                        MotionToastStyle.SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        null
                );
            }else{
                editTextUsername.setText("");
                editTextPassword.setText("");
                MotionToast.Companion.darkColorToast(
                        this,
                        "Error",
                        "El usuario o contraseña es incorrecto, Intenta de Nuevo",
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        null
                );
                Log.d("LoginActivity","onClik: Error al validar usuario y contraseña");
            }
        });
    }
    private Boolean validateLogin(){
        String email = sharedPreferences.getString("Email","");
        String passwordsave = sharedPreferences.getString("Password","");
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        Log.d("LoginActivity","validateLogin: Validando usuario y contraseña");
        if(username.equals(email) && password.equals(passwordsave)){
            return true;
        }
        return false;
    };
}
