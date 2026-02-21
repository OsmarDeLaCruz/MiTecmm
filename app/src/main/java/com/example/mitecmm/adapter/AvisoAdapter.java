package com.example.mitecmm.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.mitecmm.R;
import com.example.mitecmm.model.Aviso;

public class AvisoAdapter extends RecyclerView.Adapter<AvisoAdapter.AvisoViewHolder>{
    private List<Aviso> listAvisos;

    public AvisoAdapter(List<Aviso> listAvisos){
        this.listAvisos = listAvisos;
    }

    @NonNull
    @Override
    public AvisoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aviso, parent, false);
        return new AvisoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvisoViewHolder holder, int position) {
        Aviso aviso = listAvisos.get(position);

        holder.tvTitulo.setText(aviso.getTitulo());
        holder.tvDescripcion.setText(aviso.getDescripcion());
        holder.tvFecha.setText(aviso.getFecha());
        holder.tvCategoria.setText(aviso.getCategoria());

        //color de categoría
        switch (aviso.getCategoria().toLowerCase()){
            case "urgente":
                holder.tvCategoria.setBackgroundColor(Color.parseColor("#C0392B"));
                break;
            case "evento":
                holder.tvCategoria.setBackgroundColor(Color.parseColor("#6A1B9A"));
                break;
            case "tramite":
                holder.tvCategoria.setBackgroundColor(Color.parseColor("#1565C0"));
                break;
            default:
                holder.tvCategoria.setBackgroundColor(Color.parseColor("#333333"));
        }
    }

    @Override
    public int getItemCount(){
        return listAvisos.size();
    }

    public static class AvisoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion, tvFecha, tvCategoria;

        public AvisoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);

        }
    }
}
