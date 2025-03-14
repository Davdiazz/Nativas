package com.example.taller2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

import androidx.appcompat.app.AppCompatActivity;


public class CenterActivity extends AppCompatActivity {

    private ImageView   imageViewBack;
    private EditText editTextName;
    private EditText editTextLastname;
    private EditText editTextEmail;

    private EditText editTextPhone;

    private EditText editTextPassword;

    private EditText editTextRepeatPassword;

    private CheckBox checkBoxConditions;
    private Button buttonCenter;
    private TextView buttonLogin;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingSuperCall")
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_central);

        Log.d("RegistroActivity","onCreate: Inicializando el activity de registro");

        // Variabless
        imageViewBack = findViewById(R.id.ivBackIcon_Central);
        editTextName = findViewById(R.id.etnombre_Central);
        editTextLastname = findViewById(R.id.etapellido_Central);
        editTextEmail = findViewById(R.id.etemail_Central);
        editTextPhone = findViewById(R.id.etcelular_Central);
        editTextPassword = findViewById(R.id.etpassword_Central);
        editTextRepeatPassword = findViewById(R.id.etRepeatPassword_Central);
        checkBoxConditions = findViewById(R.id.cbConditions_Central);
        buttonLogin = findViewById(R.id.tvLogin_Central);
        buttonCenter = findViewById(R.id.btn_registro);

        // Archivo de almacenamiento local
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Llamado de Metodos
        buttonCenter.setOnClickListener(v -> {
            if(validateFields()){
                // Guardar los datos
                saveData();
                // Redireccionar a login
                new Handler().postDelayed(() ->{
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    Log.d("RegistroActivity","onClick: Redirigiendo a Login");
                    finish();
                },1000);
            }
        });

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            Log.d("RegistroActivity","onClick: Redirigiendo a Login");
            finish();
        });

        imageViewBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            Log.d("RegistroActivity","onClick: Redirigiendo a Home");
            finish();
        });

    }

    //Metodo para validar Campos
    private boolean validateFields(){
        String name = editTextName.getText().toString().trim();
        String lastname = editTextLastname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        Log.d("RegistroActivity","validateFileds: Obteniendo Datos almacenados");

        if (name.isEmpty()){
            //Mostrar Error al usuario}
            MotionToast.Companion.darkColorToast(
                    this,
                    "Error",
                    "El campo Nombre no puede estar vacio",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
            Log.d("RegistroActivity","validateFields: Error al validar Nombre");
            return false;
        }
        if (lastname.isEmpty()){
            //Mostrar Error al usuario}
            //Toast.makeText(this,"El campo nombres es requerido",Toast.LENGTH_SHORT).show();
            MotionToast.Companion.darkColorToast(
                    this,
                    "Error",
                    "El campo Apellidos no puede estar vacio",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
            Log.d("RegistroActivity","validateFields: Error al validar Apellido");
            return false;
        }
        if (email.isEmpty()){
            //Mostrar Error al usuario}
            //Toast.makeText(this,"El campo nombres es requerido",Toast.LENGTH_SHORT).show();
            MotionToast.Companion.darkColorToast(
                    this,
                    "Error",
                    "El campo Email no puede estar vacio",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
            Log.d("RegistroActivity","validateFields: Error al validar Email");
            return false;
        }

        if (phone.isEmpty()){
            //Mostrar Error al usuario}
            //Toast.makeText(this,"El campo nombres es requerido",Toast.LENGTH_SHORT).show();
            MotionToast.Companion.darkColorToast(
                    this,
                    "Error",
                    "El campo Celular no puede estar vacio",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
            Log.d("RegistroActivity","validateFields: Error al validar Telefono");
            return false;
        }

        if (password.isEmpty()){
            //Mostrar Error al usuario}
            //Toast.makeText(this,"El campo nombres es requerido",Toast.LENGTH_SHORT).show();
            MotionToast.Companion.darkColorToast(
                    this,
                    "Error",
                    "El campo Contraseña no puede estar vacio",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
            Log.d("RegistroActivity","validateFields: Error al validar Primera contraseña");
            return false;
        }

        if (repeatPassword.isEmpty()){
            //Mostrar Error al usuario}
            //Toast.makeText(this,"El campo nombres es requerido",Toast.LENGTH_SHORT).show();
            MotionToast.Companion.darkColorToast(
                    this,
                    "Error",
                    "El campo Repetir Contraseña no puede estar vacio",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
            Log.d("RegistroActivity","validateFields: Error al validar Segunda Contraseña");
            return false;
        }

        if(!checkBoxConditions.isChecked()){
            MotionToast.Companion.darkColorToast(
                    this,
                    "Error",
                    "Debe Aceptar los Terminos Y Condiciones",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
            Log.d("RegistroActivity","validateFields: Error al validar Terminos y condiciones");
            return false;
        }

        if(!password.equals(repeatPassword)){
            MotionToast.Companion.darkColorToast(
                    this,
                    "Error",
                    "Las contraseñas no son iguales, Intente de nuevo",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
            Log.d("RegistroActivity","validateFields: Error al validar ambas contraseñas");
            return false;
        }
        Log.d("RegistroActivity","validateFields: Datos Validados Correctamente");
        return true;
    }

    private void saveData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name",editTextName.getText().toString().trim());
        editor.putString("Lastname",editTextLastname.getText().toString().trim());
        editor.putString("Email",editTextEmail.getText().toString().trim());
        editor.putString("Phone",editTextPhone.getText().toString().trim());
        editor.putString("Password",editTextPassword.getText().toString().trim());
        editor.apply();
        //Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
        MotionToast.Companion.darkColorToast(
                this,
                "¡Registro Exitoso!",
                "Tus datos se han guardado con exito",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                null
        );
        Log.d("RegistroActivity","saveData: Datos guardados con exito");
    }

}
