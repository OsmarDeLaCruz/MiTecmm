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

// 1. Le decimos a Java que esta clase es un Adapter oficial de RecyclerView
public class CarrerasAdapter extends RecyclerView.Adapter<CarrerasAdapter.CarreraViewHolder> {

    private List<Carrera> listaCarreras;

    // 2. CONSTRUCTOR: Aquí el mesero recibe la lista desde la base de datos
    public CarrerasAdapter(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    // 3. DIBUJAR: Aquí toma tu diseño (item_carreras.xml) y lo convierte en una tarjeta real
    @NonNull
    @Override
    public CarreraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carreras, parent, false);
        return new CarreraViewHolder(view);
    }

    // 4. LLENAR DATOS: Aquí saca el texto de SQLite y lo pega en la pantalla
    @Override
    public void onBindViewHolder(@NonNull CarreraViewHolder holder, int position) {
        Carrera carreraActual = listaCarreras.get(position);

        // Pegamos los datos visualmente
        holder.tvSiglas.setText(carreraActual.getSiglas());
        holder.tvNombre.setText(carreraActual.getNombre());

        // (El botón morado de "Ver Maestros" lo programaremos más adelante)
    }

    // 5. CONTEO: Le dice al Scrollbar cuántas tarjetas en total debe dibujar
    @Override
    public int getItemCount() {
        return listaCarreras.size();
    }

    // =====================================================================
    // VIEWHOLDER: Es la "caja" que sostiene los elementos visuales de 1 tarjeta
    // =====================================================================
    public static class CarreraViewHolder extends RecyclerView.ViewHolder {
        TextView tvSiglas;
        TextView tvNombre;

        public CarreraViewHolder(@NonNull View itemView) {
            super(itemView);

            // 🚨 ¡ATENCIÓN AQUÍ! 🚨
            // Debes asegurarte de que estos IDs (tvSiglas y tvNombre)
            // sean exactamente los mismos que pusiste en tu archivo item_carreras.xml
            tvSiglas = itemView.findViewById(R.id.logoISC);
            tvNombre = itemView.findViewById(R.id.nombreISC);
        }
    }
}