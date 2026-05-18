package com.example.mitecmm.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitecmm.R;
import com.example.mitecmm.model.Profesor;

import java.util.ArrayList;
import java.util.List;

public class ProfesorAdapter extends RecyclerView.Adapter<ProfesorAdapter.ProfesorViewHolder> {

    private List<Profesor> listaProfesores;
    private List<Profesor> listaOriginal;

    public interface OnProfesorClick{
        void onClick(Profesor profesor);
    }
    private OnProfesorClick listener;
    public ProfesorAdapter(List<Profesor> listaProfesores, OnProfesorClick listener) {
        this.listaProfesores = listaProfesores;
        this.listaOriginal = new ArrayList<>();
        this.listaOriginal.addAll(listaProfesores);
        this.listener = listener;
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

        if (profesorActual.getNombre() != null) {
            String nombreCompleto = profesorActual.getNombre().trim();
            holder.tvNombre.setText(nombreCompleto);

            if (!nombreCompleto.isEmpty()) {
                String[] palabras = nombreCompleto.split("\\s+");
                StringBuilder iniciales = new StringBuilder();

                iniciales.append(palabras[0].substring(0, 1).toUpperCase());

                if (palabras.length > 1) {
                    iniciales.append(palabras[1].substring(0, 1).toUpperCase());
                }
                holder.tvAvatarProfesor.setText(iniciales.toString());
            } else {
                holder.tvAvatarProfesor.setText("?");
            }
        }

        holder.itemView.setOnClickListener(v->{
            //listener.onClick(listaProfesores.get(position));

            //Intent intent = new Intent(v.getContext(), Hora);
        });

        holder.btnVerHorario.setOnClickListener(v -> {
            String linkPdf = profesorActual.getUrlHorario();

            if (linkPdf != null && !linkPdf.trim().isEmpty()) {
                android.app.Dialog dialog = new android.app.Dialog(v.getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.dialog_visor_pdf);

                android.webkit.WebView webView = dialog.findViewById(R.id.webViewPdf);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.getSettings().setSupportZoom(true);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setDisplayZoomControls(false);
                String urlVisor = "https://docs.google.com/gview?embedded=true&url=" + linkPdf;
                webView.loadUrl(urlVisor);

                String urlFinal;
                String linkLimpio = linkPdf.toLowerCase().trim();

                if(linkLimpio.contains("drive.google.com")){
                    urlFinal = linkPdf.replace("/view", "/preview");
                    if (urlFinal.contains("?")) {
                        urlFinal = urlFinal.substring(0, urlFinal.indexOf("?"));                    }
                }else if(linkLimpio.endsWith(".pdf")) {
                    urlFinal = "https://docs.google.com/gview?embedded=true&url=" + linkPdf;
                }else{
                    urlFinal = linkPdf;
                }

                webView.loadUrl(urlFinal);
                dialog.findViewById(R.id.btnCerrarVisor).setOnClickListener(view -> dialog.dismiss());
                dialog.show();
            } else {
                android.widget.Toast.makeText(v.getContext(), "Horario no disponible", android.widget.Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaProfesores.size();
    }

    public static class ProfesorViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvAvatarProfesor;
        com.google.android.material.button.MaterialButton btnVerHorario;

        public ProfesorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvAvatarProfesor = itemView.findViewById(R.id.tvAvatarProfesor);
            btnVerHorario = itemView.findViewById(R.id.btnVerHorario);
        }
    }
    public void busqueda(String textoBus){
        int longitud = textoBus.length();
        if(longitud == 0){
            listaProfesores.clear();
            listaProfesores.addAll(listaOriginal);

        }else {
            listaProfesores.clear();
            for(int i=0; i<listaOriginal.size(); i++){
                Profesor p = listaOriginal.get(i);
                if (p.getNombre().toLowerCase().contains(textoBus.toLowerCase())) {
                    listaProfesores.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }
}