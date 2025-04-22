package com.example.taller2.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.taller2.R;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class PerfilFragment extends Fragment {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private MaterialButton buttonEdit;
    private ImageView imageProfile;
    private static final int PICK_IMAGE_REQUEST = 1;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        Log.d("ProfileFragment", "onCreateView: Inicializando el fragment de perfil");

        // Variables
        editTextName = view.findViewById(R.id.etnombre_Profile);
        editTextEmail = view.findViewById(R.id.etemail_Profile);
        editTextPhone = view.findViewById(R.id.etcelular_Profile);
        buttonEdit = view.findViewById(R.id.btnEdit_Profile);
        imageProfile = view.findViewById(R.id.imageProfile);
        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);


        // Cargar la imagen guardada
        String encodedImage = sharedPreferences.getString("ProfileImage", "");
        if (!encodedImage.isEmpty()) {
            byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            imageProfile.setImageBitmap(bitmap);
        }

        // Obtener los datos
        Log.d("ProfileFragment", "onCreateView: Obteniendo datos almacenados");
        String name = sharedPreferences.getString("Name", "");
        String email = sharedPreferences.getString("Email", "");
        String phone = sharedPreferences.getString("Phone", "");

        // Mostrar los datos obtenidos
        Log.d("ProfileFragment", "onCreateView: Mostrando datos almacenados");
        editTextName.setText(name);
        editTextEmail.setText(email);
        editTextPhone.setText(phone);

        buttonEdit.setOnClickListener(v -> toggleEnabledState());
        // aca puede cambiar la imagen segun la galeria del celular
        imageProfile.setOnClickListener(v -> openImageChooser());

        return view;
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                        imageProfile.setImageBitmap(bitmap);

                        // Guardar imagen en SharedPreferences y esta quede siempre asi se cierre
                        saveImageToSharedPreferences(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void saveImageToSharedPreferences(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ProfileImage", encodedImage);
        editor.apply();
    }

    private void toggleEnabledState() {
        String form = buttonEdit.getText().toString().trim();
        // Si está deshabilitado, habilitarlo
        Log.d("ProfileActivity", "toggleEnabledState: Cambiando los campos a enable");
        if(form.equals("Editar")){
            editTextName.setEnabled(true);
            editTextEmail.setEnabled(true);
            editTextPhone.setEnabled(true);
            imageProfile.setEnabled(true);
            buttonEdit.setText("Guardar");
            buttonEdit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));
            buttonEdit.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            buttonEdit.setStrokeColorResource(R.color.black);
            buttonEdit.setIconTintResource(R.color.black);
        }else{
            Log.d("ProfileActivity", "toggleEnabledState: Cambiando los campos a disable");
            if(validateFields()){
                saveData();
                editTextName.setEnabled(false);
                editTextEmail.setEnabled(false);
                editTextPhone.setEnabled(false);
                imageProfile.setEnabled(false);
                buttonEdit.setText("Editar");
                buttonEdit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black));
                buttonEdit.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                buttonEdit.setStrokeColorResource(R.color.white);
                buttonEdit.setIconTintResource(R.color.white);
            }
        }
    }
    private boolean validateFields(){
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        Log.d("ProfileActivity", "validateFields: Validando los campos");
        if (name.isEmpty()){

            MotionToast.Companion.darkColorToast(
                    requireActivity(),
                    "Error",
                    "El campo Nombre no puede estar vacio",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
            );
            return false;
        }
        if (email.isEmpty()){

            MotionToast.Companion.darkColorToast(
                    requireActivity(),
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

            MotionToast.Companion.darkColorToast(
                    requireActivity(),
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
        editor.putString("Email",editTextEmail.getText().toString().trim());
        editor.putString("Phone",editTextPhone.getText().toString().trim());
        editor.apply();
        MotionToast.Companion.darkColorToast(
                requireActivity(),
                "¡Registro Exitoso!",
                "Tus datos se han guardado con exito",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                null
        );
    }

}
