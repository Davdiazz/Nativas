package com.example.taller2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taller2.R;
import java.util.ArrayList;
import java.util.List;

public class CategoriasFragment extends Fragment {

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_categorias);
        recyclerView.setAdapter(new CategoriasAdapter(getCategorias()));

        return view;
    }

    private List<Categoria> getCategorias() {
        List<Categoria> categorias = new ArrayList<>();

        categorias.add(new Categoria(
                "Comedia",
                25,
                R.drawable.comedia,
                R.id.ProductosFragment // Redirige al fragmento de productos
        ));

        categorias.add(new Categoria(
                "Terror",
                42,
                R.drawable.terror,
                R.id.ProductosFragment
        ));

        categorias.add(new Categoria(
                "Fantasia",
                18,
                R.drawable.fantasia,
                R.id.ProductosFragment
        ));

        categorias.add(new Categoria(
                "Ciencia",
                31,
                R.drawable.ciencia,
                R.id.ProductosFragment
        ));

        categorias.add(new Categoria(
                "Infantil",
                27,
                R.drawable.infantil,
                R.id.ProductosFragment
        ));

        return categorias;
    }

    // Clase interna para el modelo de categoría
    private static class Categoria {
        String nombre;
        int cantidadProductos;
        int imagenResId;
        int destinoFragmentId;

        public Categoria(String nombre, int cantidadProductos, int imagenResId, int destinoFragmentId) {
            this.nombre = nombre;
            this.cantidadProductos = cantidadProductos;
            this.imagenResId = imagenResId;
            this.destinoFragmentId = destinoFragmentId;
        }
    }

    // Adaptador como clase interna
    private class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriaViewHolder> {
        private List<Categoria> categorias;

        public CategoriasAdapter(List<Categoria> categorias) {
            this.categorias = categorias;
        }

        @NonNull
        @Override
        public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_categoria, parent, false);
            return new CategoriaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
            Categoria categoria = categorias.get(position);

            holder.imagen.setImageResource(categoria.imagenResId);
            holder.nombre.setText(categoria.nombre);
            holder.cantidad.setText(String.format("%d productos", categoria.cantidadProductos));

            holder.cardView.setOnClickListener(v -> {
                // Pasa la categoría seleccionada como argumento
                Bundle args = new Bundle();
                args.putString("categoria", categoria.nombre);
                navController.navigate(categoria.destinoFragmentId, args);
            });
        }

        @Override
        public int getItemCount() {
            return categorias.size();
        }

        // ViewHolder como clase interna
        class CategoriaViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            ImageView imagen;
            TextView nombre, cantidad;

            public CategoriaViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.card_categoria); // ID del CardView
                imagen = itemView.findViewById(R.id.categoria_imagen);
                nombre = itemView.findViewById(R.id.categoria_nombre);
                cantidad = itemView.findViewById(R.id.categoria_cantidad);
            }
        }
    }
}