package com.example.taller2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.taller2.R;
import com.example.taller2.database.DatabaseHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleAuth";


    // Elementos de la UI
    private EditText editCorreo, editContrasena;
    private Button btnLogin, btnRegistrarse;
    private TextView btnRecuperarContrasena;
    private SignInButton btnGoogle;

    // Base de datos y Google
    private DatabaseHelper dbHelper;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarVistas();
        configurarGoogleSignIn();
        configurarEventos();
    }

    private void inicializarVistas() {
        editCorreo = findViewById(R.id.editCorreo);
        editContrasena = findViewById(R.id.editContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnGoogle = findViewById(R.id.btn_google);
        btnRecuperarContrasena = findViewById(R.id.btnRecuperarContrasena);
        dbHelper = new DatabaseHelper(this);
    }

    private void configurarGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void configurarEventos() {
        // Login con Google
        btnGoogle.setOnClickListener(v -> signInWithGoogle());

        // Login tradicional
        btnLogin.setOnClickListener(v -> validarUsuarioLocal());

        btnRegistrarse.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));

        btnRecuperarContrasena.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperarContrasenaActivity.class)));
    }

    private void validarUsuarioLocal() {
        String correo = editCorreo.getText().toString().trim();
        String contrasena = editContrasena.getText().toString().trim();

        if (dbHelper.verificarUsuario(correo, contrasena)) {
            boolean esAdmin = dbHelper.obtenerRol(correo);
            String mensaje = esAdmin ? "Bienvenido administrador" : "Bienvenido usuario";
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            iniciarMainActivity();
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            manejarResultadoGoogle(data);
        }
    }

    private void manejarResultadoGoogle(Intent data) {
        try {
            GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
            if (account != null) {
                registrarUsuarioGoogle(account);
                iniciarMainActivity();
            }
        } catch (ApiException e) {
            Log.e(TAG, "Error Google Sign-In: ", e);
            Toast.makeText(this, "Error: " + e.getStatusCode(), Toast.LENGTH_LONG).show();
        }
    }

    private void registrarUsuarioGoogle(GoogleSignInAccount cuenta) {
        String correo = cuenta.getEmail();
        String nombre = cuenta.getDisplayName();

        if (!dbHelper.existeUsuario(correo)) {
            dbHelper.agregarUsuario(nombre, correo, "", false); // Password vacío para usuarios Google
        }
        Toast.makeText(this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();
    }

    private void iniciarMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount cuenta = GoogleSignIn.getLastSignedInAccount(this);
        if (cuenta != null) iniciarMainActivity();
    }
}