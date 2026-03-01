package com.example.mitecmm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitecmm.R;
import com.example.mitecmm.model.Profesor;

import java.util.List;

public class ProfesorAdapter extends RecyclerView.Adapter<ProfesorAdapter.ProfesorViewHolder> {

    private List<Profesor> listaProfesores;

    public ProfesorAdapter(List<Profesor> listaProfesores) {
        this.listaProfesores = listaProfesores;
    }

    @NonNull
    @Override
    public ProfesorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profesor, parent, false);
        return new ProfesorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfesorViewHolder holder, int position) {
        Profesor profesorActual = listaProfesores.get(position);

        holder.tvNombre.setText(profesorActual.getNombre());
        holder.tvCarrera.setText(profesorActual.getCarrera());

        holder.btnHorario.setOnClickListener(v -> {
            //falta code para ver el horario, pero creo que sera un archivo
        });
    }

    @Override
    public int getItemCount() {
        return listaProfesores.size();
    }

    public static class ProfesorViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCarrera;
        Button btnHorario;

        public ProfesorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProfesor);
            tvCarrera = itemView.findViewById(R.id.tvCarreraP);
            btnHorario = itemView.findViewById(R.id.btnVerHorario);
        }
    }
}