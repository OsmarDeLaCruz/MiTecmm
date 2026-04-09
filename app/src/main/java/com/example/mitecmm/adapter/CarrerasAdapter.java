package com.example.mitecmm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mitecmm.R;
import com.example.mitecmm.model.Carrera;
import java.util.List;


public class CarrerasAdapter extends RecyclerView.Adapter<CarrerasAdapter.CarreraViewHolder> {

    private List<Carrera> listaCarreras;

    public CarrerasAdapter(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
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


    }


    @Override
    public int getItemCount() {
        return listaCarreras.size();
    }

    public static class CarreraViewHolder extends RecyclerView.ViewHolder {
        TextView tvSiglas;
        TextView tvNombre;

        public CarreraViewHolder(@NonNull View itemView) {
            super(itemView);



            tvSiglas = itemView.findViewById(R.id.logoISC);
            tvNombre = itemView.findViewById(R.id.nombreISC);
        }
    }
}