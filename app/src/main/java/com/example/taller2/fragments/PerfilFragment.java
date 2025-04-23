// IMPORTANTE: Asegúrate de que tu layout XML tenga estos IDs: etaddress_Profile y etage_Profile

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
    private EditText editTextAddress;
    private EditText editTextAge;
    private MaterialButton buttonEdit;
    private ImageView imageProfile;
    private static final int PICK_IMAGE_REQUEST = 1;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Referencias UI
        editTextName = view.findViewById(R.id.etnombre_Profile);
        editTextEmail = view.findViewById(R.id.etemail_Profile);
        editTextPhone = view.findViewById(R.id.etcelular_Profile);
        editTextAddress = view.findViewById(R.id.etaddress_Profile);
        editTextAge = view.findViewById(R.id.etage_Profile);
        buttonEdit = view.findViewById(R.id.btnEdit_Profile);
        imageProfile = view.findViewById(R.id.imageProfile);
        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        // Cargar imagen guardada
        String encodedImage = sharedPreferences.getString("ProfileImage", "");
        if (!encodedImage.isEmpty()) {
            byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            imageProfile.setImageBitmap(bitmap);
        }

        // Obtener los datos
        String name = sharedPreferences.getString("Name", "");
        String email = sharedPreferences.getString("Email", "");
        String phone = sharedPreferences.getString("Phone", "");
        String address = sharedPreferences.getString("Address", "");
        String age = sharedPreferences.getString("Age", "");

        // Mostrar los datos
        editTextName.setText(name);
        editTextEmail.setText(email);
        editTextPhone.setText(phone);
        editTextAddress.setText(address);
        editTextAge.setText(age);

        buttonEdit.setOnClickListener(v -> toggleEnabledState());
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
        if(form.equals("Editar")){
            editTextName.setEnabled(true);
            editTextEmail.setEnabled(true);
            editTextPhone.setEnabled(true);
            editTextAddress.setEnabled(true);
            editTextAge.setEnabled(true);
            imageProfile.setEnabled(true);

            buttonEdit.setText("Guardar");
            buttonEdit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));
            buttonEdit.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            buttonEdit.setStrokeColorResource(R.color.black);
            buttonEdit.setIconTintResource(R.color.black);
        } else {
            if(validateFields()){
                saveData();
                editTextName.setEnabled(false);
                editTextEmail.setEnabled(false);
                editTextPhone.setEnabled(false);
                editTextAddress.setEnabled(false);
                editTextAge.setEnabled(false);
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
        String address = editTextAddress.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if (name.isEmpty()) {
            showToast("El campo Nombre no puede estar vacío");
            return false;
        }
        if (email.isEmpty()) {
            showToast("El campo Email no puede estar vacío");
            return false;
        }
        if (phone.isEmpty()) {
            showToast("El campo Celular no puede estar vacío");
            return false;
        }
        if (address.isEmpty()) {
            showToast("El campo Dirección no puede estar vacío");
            return false;
        }
        if (age.isEmpty()) {
            showToast("El campo Edad no puede estar vacío");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        MotionToast.Companion.darkColorToast(
                requireActivity(),
                "Error",
                message,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                null
        );
    }

    private void saveData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", editTextName.getText().toString().trim());
        editor.putString("Email", editTextEmail.getText().toString().trim());
        editor.putString("Phone", editTextPhone.getText().toString().trim());
        editor.putString("Address", editTextAddress.getText().toString().trim());
        editor.putString("Age", editTextAge.getText().toString().trim());
        editor.apply();

        MotionToast.Companion.darkColorToast(
                requireActivity(),
                "¡Registro Exitoso!",
                "Tus datos se han guardado con éxito",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                null
        );
    }
}
