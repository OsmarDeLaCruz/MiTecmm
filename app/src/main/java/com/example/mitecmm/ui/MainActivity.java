package com.example.mitecmm.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.mitecmm.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitecmm.dao.AvisoDAO;
import com.example.mitecmm.model.Aviso;

import java.util.List;

public class MainActivity extends BaseMActivity {

    CardView btnMapa, btnProfesores;
    LinearLayout btnVerTodos;
    ImageView btnMenu;
    TextView tvAvisoBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvFechaHeader = findViewById(R.id.tvFecha);
        TextView tvAvisoTicker = findViewById(R.id.tvAvisoBanner);

        btnMapa = findViewById(R.id.cardMapa);
        btnProfesores = findViewById(R.id.cardProfesores);
        btnVerTodos = findViewById(R.id.btnVerTodos);
        btnMenu = findViewById(R.id.menuham);

        tvAvisoBanner = findViewById(R.id.tvAvisoBanner);

        String fechaHoy = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(new java.util.Date());
        tvFechaHeader.setText(fechaHoy);

        AvisoDAO avisoDAO = new AvisoDAO(this);
        List<Aviso> listaAvisos = avisoDAO.showAll();
        if (listaAvisos != null && !listaAvisos.isEmpty()){
            Aviso ultimoAviso = listaAvisos.get(listaAvisos.size() - 1);
            tvAvisoTicker.setText(ultimoAviso.getTitulo());
        }else {
            tvAvisoTicker.setText("No hay avisos recientes");
        }

        btnMapa.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MapaActivity.class));
        });

        btnProfesores.setOnClickListener(v ->{
            startActivity(new Intent(MainActivity.this, CarrerasActivity.class));
        });

        btnMenu.setOnClickListener(v -> {
            abrirMenuLateral();
        });

        if (btnVerTodos != null) {
            btnVerTodos.setOnClickListener(v -> {
                mostrarDialogoAvisos();
            });
        }
    }

    private void actualizarBanner() {
        if (tvAvisoBanner != null) {
            AvisoDAO avisoDAO = new AvisoDAO(this);
            List<Aviso> lista = avisoDAO.showAll();

            if (lista != null && !lista.isEmpty()) {
                // Tomamos el último aviso ingresado a la base de datos
                Aviso ultimo = lista.get(lista.size() - 1);
                tvAvisoBanner.setText(ultimo.getCategoria() + ": " + ultimo.getTitulo());
            } else {
                tvAvisoBanner.setText("Sin avisos recientes");
            }
        }
    }

    private void mostrarDialogoAvisos(){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_ver_avisos, null);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        android.app.AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        ImageView btnCerrar = dialogView.findViewById(R.id.btnCerrarAvisos);
        if (btnCerrar != null) {
            btnCerrar.setOnClickListener(v -> dialog.dismiss());
        }

        RecyclerView recycler = dialogView.findViewById(R.id.recyclerHistorialAvisos);

        if (recycler != null) {
            recycler.setLayoutManager(new LinearLayoutManager(this));

            AvisoDAO avisoDAO = new AvisoDAO(this);
            List<Aviso> listaAvisos = avisoDAO.showAll();

            RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aviso_alumno, parent, false);
                    return new RecyclerView.ViewHolder(v) {};
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                    Aviso a = listaAvisos.get(position);
                    View v = holder.itemView;

                    TextView tvCat = v.findViewById(R.id.tvCategoria);
                    TextView tvFecha = v.findViewById(R.id.tvFecha);
                    TextView tvTitulo = v.findViewById(R.id.tvTitulo);
                    TextView tvDesc = v.findViewById(R.id.tvDescripcion);
                    View barra = v.findViewById(R.id.barraCategoria);

                    tvFecha.setText(a.getFecha());
                    tvTitulo.setText(a.getTitulo());
                    tvDesc.setText(a.getDescripcion());

                    if (a.getCategoria() != null && a.getCategoria().equalsIgnoreCase("Urgente")) {
                        tvCat.setText("⚠️ U R G E N T E");
                        tvCat.setTextColor(Color.parseColor("#D32F2F"));
                        barra.setBackgroundColor(Color.parseColor("#D32F2F"));
                    } else {
                        String catStr = a.getCategoria() != null ? a.getCategoria().toUpperCase().replace("", " ").trim() : "A V I S O";
                        tvCat.setText(catStr);
                        tvCat.setTextColor(Color.parseColor("#512DA8"));
                        barra.setBackgroundColor(Color.parseColor("#512DA8"));
                    }
                }

                @Override
                public int getItemCount() {
                    return listaAvisos.size();
                }
            };

            recycler.setAdapter(adapter);
        }

        dialog.show();
    }
}