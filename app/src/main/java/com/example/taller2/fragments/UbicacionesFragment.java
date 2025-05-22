package com.example.taller2.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taller2.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionesFragment extends Fragment implements OnMapReadyCallback {

    public UbicacionesFragment() {
        super(R.layout.fragment_ubicaciones); // Asegúrate que este sea tu layout correcto
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng ubicacion = new LatLng(4.6097100, -74.0817500); // Bogotá, por ejemplo
        googleMap.addMarker(new MarkerOptions().position(ubicacion).title("Estoy aquí"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
    }
}
