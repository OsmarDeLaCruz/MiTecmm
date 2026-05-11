package com.example.mitecmm.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitecmm.R;
import com.example.mitecmm.model.Carrera;
import com.example.mitecmm.ui.ProfesoresActivity;

import java.util.ArrayList;
import java.util.List;

public class CarrerasAdapter extends RecyclerView.Adapter<CarrerasAdapter.CarreraViewHolder> {

    private List<Carrera> listaCarreras;
    private List<Carrera> listaOriginal;

    public CarrerasAdapter(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;

        this.listaOriginal = new ArrayList<>();
        this.listaOriginal.addAll(listaCarreras);
    }

    @NonNull
    @Override
    public CarreraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carreras, parent, false);
        return new CarreraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarreraViewHolder holder, int position) {
        Carrera carreraActual = listaCarreras.get(position);

        holder.tvSiglas.setText(carreraActual.getSiglas());
        holder.tvNombre.setText(carreraActual.getNombre());

        holder.btnVerMaestros.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProfesoresActivity.class);
            intent.putExtra("CARRERA_SELECCIONADA", carreraActual.getSiglas());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaCarreras.size();
    }

    public static class CarreraViewHolder extends RecyclerView.ViewHolder {
        TextView tvSiglas;
        TextView tvNombre;
        TextView btnVerMaestros;

        public CarreraViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSiglas = itemView.findViewById(R.id.tvSiglasCarrera);
            tvNombre = itemView.findViewById(R.id.tvNombreCarrera);
            btnVerMaestros = itemView.findViewById(R.id.btnVerMaestros);
        }
    }

    public void buscar(String buscarp){
        listaCarreras.clear();
        if(buscarp.length() == 0){
            listaCarreras.addAll(listaOriginal);
        }else{
            String textoMinusculas = buscarp.toLowerCase();
            for(int i=0; i<listaOriginal.size(); i++){
                Carrera c = listaOriginal.get(i);
                if (c.getNombre().toLowerCase().contains(textoMinusculas) ||
                        c.getSiglas().toLowerCase().contains(textoMinusculas)) {
                    listaCarreras.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }
}