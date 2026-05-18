package com.example.mitecmm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitecmm.R;
import com.example.mitecmm.model.Carrera;
import com.example.mitecmm.model.Horario;

import java.util.ArrayList;
import java.util.List;

public class HorarioCardAdapter extends RecyclerView.Adapter<HorarioCardAdapter.ViewHolder> {

    List<Horario> lista;
    private  List<Horario> listaOriginal;

    public HorarioCardAdapter(List<Horario> lista){
        this.lista = lista;
        this.listaOriginal = new ArrayList<>(lista);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_horario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Horario h = lista.get(position);

        // Iniciales del docente para el avatar
        String nombre = h.getDocente() != null ? h.getDocente() : "";
        String[] partes = nombre.trim().split(" ");
        String iniciales = "";
        if (partes.length >= 2) {
            iniciales = String.valueOf(partes[0].charAt(0)) + String.valueOf(partes[1].charAt(0));
        } else if (partes.length == 1 && !partes[0].isEmpty()) {
            iniciales = String.valueOf(partes[0].charAt(0));
        }
        holder.tvIniciales.setText(iniciales.toUpperCase());

        holder.tvDocente.setText(h.getDocente());
        holder.tvGrupo.setText("GRUPO  " + h.getGrupo());
        holder.tvMateria.setText(h.getMateria());
        holder.tvDia.setText(h.getDia());
        holder.tvHora.setText(h.getHoraInicio() + "  →  " + h.getHoraFin());
        holder.tvAula.setText(h.getAula());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvIniciales;
        TextView tvDocente;
        TextView tvGrupo;
        TextView tvMateria;
        TextView tvDia;
        TextView tvHora;
        TextView tvAula;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIniciales = itemView.findViewById(R.id.tvIniciales);
            tvDocente = itemView.findViewById(R.id.tvDocente);
            tvGrupo = itemView.findViewById(R.id.tvGrupo);
            tvMateria = itemView.findViewById(R.id.tvMateria);
            tvDia = itemView.findViewById(R.id.tvDia);
            tvHora = itemView.findViewById(R.id.tvHora);
            tvAula = itemView.findViewById(R.id.tvAula);
        }
    }

    public void buscar(String buscarH){
        lista.clear();
        if(buscarH.length() == 0){
            lista.addAll(listaOriginal);
        }else{
            String textoMinusculas = buscarH.toLowerCase();
            for(int i=0; i<listaOriginal.size(); i++){
                Horario h = listaOriginal.get(i);
                if (h.getDocente() != null && h.getDocente().toLowerCase().contains(textoMinusculas) ||
                        h.getMateria() != null && h.getMateria().toLowerCase().contains(textoMinusculas)) {
                    lista.add(h);
                }
            }
        }
        notifyDataSetChanged();
    }
}
