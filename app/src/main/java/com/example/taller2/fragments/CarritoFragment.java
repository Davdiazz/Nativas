package com.example.taller2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taller2.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarritoFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private TextView totalItems, totalPrecio;
    private RecyclerView recyclerView;
    private CarritoAdapter adapter;
    private List<ProductosFragment.Producto> productosCarrito = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        // Inicializador SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("CarritoPrefs", Context.MODE_PRIVATE);

        totalItems = view.findViewById(R.id.total_items);
        totalPrecio = view.findViewById(R.id.total_precio);
        recyclerView = view.findViewById(R.id.recycler_carrito);

        // Configuracion RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CarritoAdapter(productosCarrito);
        recyclerView.setAdapter(adapter);

        cargarCarrito();
        return view;
    }

    private void cargarCarrito() {
        Gson gson = new Gson();
        String productosJson = sharedPreferences.getString("carrito", "[]");
        Type type = new TypeToken<List<ProductosFragment.Producto>>() {}.getType();
        List<ProductosFragment.Producto> productos = gson.fromJson(productosJson, type);

        productosCarrito.clear();
        productosCarrito.addAll(productos);
        adapter.notifyDataSetChanged();

        calcularTotales();
    }

    private void calcularTotales() {
        int totalProductos = productosCarrito.size();
        double sumaPrecios = 0;

        for (ProductosFragment.Producto p : productosCarrito) {
            sumaPrecios += p.getPrecio();
        }

        totalItems.setText("Total de productos: " + totalProductos);
        totalPrecio.setText("Suma total: $" + String.format("%.2f", sumaPrecios));
    }

    private void eliminarProducto(ProductosFragment.Producto producto) {
        Gson gson = new Gson();
        String productosJson = sharedPreferences.getString("carrito", "[]");
        Type type = new TypeToken<List<ProductosFragment.Producto>>() {}.getType();
        List<ProductosFragment.Producto> productos = gson.fromJson(productosJson, type);

        productos.remove(producto);
        sharedPreferences.edit().putString("carrito", gson.toJson(productos)).apply();

        cargarCarrito();
        Toast.makeText(getContext(), producto.getNombre() + " eliminado del carrito", Toast.LENGTH_SHORT).show();
    }

    // Adaptador para el carrito
    private class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

        private List<ProductosFragment.Producto> productos;

        CarritoAdapter(List<ProductosFragment.Producto> productos) {
            this.productos = productos;
        }

        @NonNull
        @Override
        public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_carrito, parent, false);
            return new CarritoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
            ProductosFragment.Producto producto = productos.get(position);

            holder.nombre.setText(producto.getNombre());
            holder.precio.setText(String.format("$%.2f", producto.getPrecio()));
            holder.imagen.setImageResource(producto.getImagenResId());


            holder.btnEliminar.setOnClickListener(v -> eliminarProducto(producto));
        }

        @Override
        public int getItemCount() {
            return productos.size();
        }

        class CarritoViewHolder extends RecyclerView.ViewHolder {
            ImageView imagen;
            TextView nombre, precio;
            Button btnEliminar;

            CarritoViewHolder(@NonNull View itemView) {
                super(itemView);
                imagen = itemView.findViewById(R.id.imagen_producto);
                nombre = itemView.findViewById(R.id.nombre_producto);
                precio = itemView.findViewById(R.id.precio_producto);
                btnEliminar = itemView.findViewById(R.id.btn_eliminar);
            }
        }
    }
}
