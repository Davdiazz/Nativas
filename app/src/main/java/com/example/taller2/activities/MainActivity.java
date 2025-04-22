package com.example.taller2.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.taller2.R;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Esto obtiene la info de DrawerLayout y NavigationView para asi mas adelante realizar su navegacion fluida
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        // Configuracion Navigation Component
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // Configurar AppBarConfiguration segun sea el destino que serian los 6 fragments
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.InicioFragment,
                    R.id.ProductosFragment,
                    R.id.CarritoFragment,
                    R.id.PerfilFragment,
                    R.id.CategoriasFragment
            ).setOpenableLayout(drawerLayout).build();

            // Configurar el toolbar con NavController
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

            // Revise documentacion en internet e indican que este metodo es un poco mas sencillo y permite una navegacion mas fluida que utilizar  ActionBarDrawerToggle sin embargo escribe el codigo abajo
            NavigationUI.setupWithNavController(navView, navController);

            // Configuracion de ActionBarDrawerToggle (opcional)
            drawerToggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
            );
            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
