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
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.taller2.R;
    import com.google.gson.Gson;
    import com.google.gson.reflect.TypeToken;

    import java.lang.reflect.Type;
    import java.util.ArrayList;
    import java.util.List;

    public class ProductosFragment extends Fragment {

        private RecyclerView recyclerView;
        private ProductosAdapter adapter;
        private String categoriaActual = "all";


        // Clase Producto mejorada con métodos getter
        public static class Producto {
            private String id;
            private String nombre;
            private String descripcion;
            private double precio;
            private int stock;
            private int imagenResId;
            private String categoria;

            public Producto(String id, String nombre, String descripcion, double precio,
                            int stock, int imagenResId, String categoria) {
                this.id = id;
                this.nombre = nombre;
                this.descripcion = descripcion;
                this.precio = precio;
                this.stock = stock;
                this.imagenResId = imagenResId;
                this.categoria = categoria;
            }

            // Getters
            public String getId() { return id; }
            public String getNombre() { return nombre; }
            public String getDescripcion() { return descripcion; }
            public double getPrecio() { return precio; }
            public int getStock() { return stock; }
            public int getImagenResId() { return imagenResId; }
            public String getCategoria() { return categoria; }


            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Producto producto = (Producto) o;
                return id.equals(producto.id);
            }

            @Override
            public int hashCode() {
                return id.hashCode();
            }
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                categoriaActual = getArguments().getString("categoria", "all");
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_productos, container, false);

            recyclerView = view.findViewById(R.id.recycler_productos);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

            List<Producto> productos = getProductosFiltrados(categoriaActual);
            adapter = new ProductosAdapter(productos);
            recyclerView.setAdapter(adapter);

            return view;
        }

        private List<Producto> getProductosFiltrados(String categoria) {
            List<Producto> todosProductos = getAllProductos();
            if (categoria.equals("all")) {
                return todosProductos;
            }

            List<Producto> productosFiltrados = new ArrayList<>();
            for (Producto producto : todosProductos) {
                if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                    productosFiltrados.add(producto);
                }
            }
            return productosFiltrados;
        }

        private List<Producto> getAllProductos() {
            List<Producto> productos = new ArrayList<>();

            // Productos de electrónica
            productos.add(new Producto(
                    "P001",
                    "Smartphone X",
                    "Pantalla 6.5\", 128GB almacenamiento",
                    299.99,
                    15,
                    R.drawable.ic_phone,
                    "Electrónica"
            ));

            productos.add(new Producto(
                    "P002",
                    "Laptop Pro",
                    "Intel i7, 16GB RAM, 512GB SSD",
                    899.99,
                    8,
                    R.drawable.ic_android,
                    "Electrónica"
            ));

            // Productos de ropa
            productos.add(new Producto(
                    "P003",
                    "Camisa Casual",
                    "100% algodón, talla M",
                    29.99,
                    20,
                    R.drawable.ic_android,
                    "Ropa"
            ));

            productos.add(new Producto(
                    "P004",
                    "Zapatos Deportivos",
                    "Para running, talla 42",
                    79.99,
                    12,
                    R.drawable.ic_android,
                    "Ropa"
            ));

            // Productos de hogar
            productos.add(new Producto(
                    "P005",
                    "Juego de Sábanas",
                    "Algodón egipcio, rey",
                    49.99,
                    10,
                    R.drawable.ic_android,
                    "Hogar"
            ));

            return productos;
        }

        private class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {

            private final List<Producto> productos;

            ProductosAdapter(List<Producto> productos) {
                this.productos = productos;
            }

            @NonNull
            @Override
            public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_producto, parent, false);
                return new ProductoViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
                Producto producto = productos.get(position);

                try {
                    holder.imagen.setImageResource(producto.getImagenResId());
                } catch (Exception e) {
                    holder.imagen.setImageResource(R.drawable.ic_android);
                }

                holder.nombre.setText(producto.getNombre());
                holder.descripcion.setText(producto.getDescripcion());
                holder.precio.setText(String.format("$%.2f", producto.getPrecio()));
                holder.stock.setText(String.format("Stock: %d", producto.getStock()));

                holder.btnAddToCart.setOnClickListener(v -> agregarAlCarrito(producto));
            }

            @Override
            public int getItemCount() {
                return productos.size();
            }

            class ProductoViewHolder extends RecyclerView.ViewHolder {
                final ImageView imagen;
                final TextView nombre, descripcion, precio, stock;
                final Button btnAddToCart;

                ProductoViewHolder(@NonNull View itemView) {
                    super(itemView);
                    imagen = itemView.findViewById(R.id.producto_imagen);
                    nombre = itemView.findViewById(R.id.producto_nombre);
                    descripcion = itemView.findViewById(R.id.producto_descripcion);
                    precio = itemView.findViewById(R.id.producto_precio);
                    stock = itemView.findViewById(R.id.producto_stock);
                    btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
                }
            }
        }

        private void agregarAlCarrito(Producto producto) {
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("CarritoPrefs", Context.MODE_PRIVATE);
            Gson gson = new Gson();

            // Obtener carrito actual
            String productosJson = sharedPreferences.getString("carrito", "[]");
            Type type = new TypeToken<List<Producto>>(){}.getType();
            List<Producto> productosEnCarrito = gson.fromJson(productosJson, type);

            // Verificar si el producto ya está en el carrito
            for (Producto p : productosEnCarrito) {
                if (p.getId().equals(producto.getId())) {
                    Toast.makeText(getContext(), "Este producto ya está en el carrito", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Agregar producto al carrito
            productosEnCarrito.add(producto);

            // Guardar carrito actualizado
            sharedPreferences.edit()
                    .putString("carrito", gson.toJson(productosEnCarrito))
                    .apply();

            Toast.makeText(getContext(),
                    producto.getNombre() + " añadido al carrito",
                    Toast.LENGTH_SHORT).show();
        }
    }