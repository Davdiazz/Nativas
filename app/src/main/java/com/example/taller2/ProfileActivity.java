package com.example.taller2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imageViewBack;
    private EditText editTextName;
    private EditText editTextLastname;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private Button buttonEdit;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingSuperCall")
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_profile);

        Log.d("ProfileActivity", "onCreate: Inicializando el activity de profile");

        // Variables
        imageViewBack = findViewById(R.id.ivBackIcon_Profile);
        editTextName = findViewById(R.id.etnombre_Profile);
        editTextLastname = findViewById(R.id.etapellido_Profile);
        editTextEmail = findViewById(R.id.etemail_Profile);
        editTextPhone = findViewById(R.id.etcelular_Profile);
        buttonEdit = findViewById(R.id.btnEdit_Profile);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        //Obtener los datos
        Log.d("ProfileActivity", "onCreate: Obteniendo datos alamacenados");
        String name = sharedPreferences.getString("Name","");
        String lastname = sharedPreferences.getString("Lastname","");
        String email = sharedPreferences.getString("Email","");
        String phone = sharedPreferences.getString("Phone","");

        //Mostrar los datos obtenidos
        Log.d("ProfileActivity", "onCreate: Mostrando datos alamacenados");
        editTextName.setText(name);
        editTextLastname.setText(lastname);
        editTextEmail.setText(email);
        editTextPhone.setText(phone);

        buttonEdit.setOnClickListener(v -> {
            toggleEnabledState();
        });

        imageViewBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            Log.d("ProfileActivity", "onClick: Redireccionando a LoginActivity");
            finish();
        });

    }
    private void toggleEnabledState() {
        String form = buttonEdit.getText().toString().trim();
        // Si está deshabilitado, habilitarlo
        Log.d("ProfileActivity", "toggleEnabledState: Cambiando los campos a enable");
        if(form.equals("Editar")){
            editTextName.setEnabled(true);
            editTextLastname.setEnabled(true);
            editTextEmail.setEnabled(true);
            editTextPhone.setEnabled(true);
            buttonEdit.setText("Guardar");
        }else{
            Log.d("ProfileActivity", "toggleEnabledState: Cambiando los campos a disable");
            if(validateFields()){
                saveData();
                editTextName.setEnabled(false);
                editTextLastname.setEnabled(false);
                editTextEmail.setEnabled(false);
                editTextPhone.setEnabled(false);
                buttonEdit.setText("Editar");
            }
        }

    }

    private boolean validateFields(){
        String name = editTextName.getText().toString().trim();
        String lastname = editTextLastname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        Log.d("ProfileActivity", "validateFields: Validando los campos");
        if (name.isEmpty()){
            //Mostrar Error al usuario}
            //Toast.makeText(this,"El campo nombres es requerido",Toast.LENGTH_SHORT).show();
            MotionToast.Companion.darkColorToast(
                    this,
                    "Error",
                    "El campo Nombre no puede estar vacio",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
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
            return false;
        }
        return true;
    }
    private void saveData(){
        Log.d("ProfileActivity", "saveData: Guardando datos");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name",editTextName.getText().toString().trim());
        editor.putString("Lastname",editTextLastname.getText().toString().trim());
        editor.putString("Email",editTextEmail.getText().toString().trim());
        editor.putString("Phone",editTextPhone.getText().toString().trim());
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
    }
}
