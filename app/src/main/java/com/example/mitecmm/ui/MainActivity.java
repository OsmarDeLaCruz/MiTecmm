package com.example.mitecmm.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mitecmm.R;
import com.example.mitecmm.adapter.AvisoAdapter;
import com.example.mitecmm.model.Aviso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseMActivity {

    Button btnMapa, btnProfesores;
    RecyclerView recyclerAvisos;
    ImageView btnMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMapa = findViewById(R.id.btnMapa);
        btnProfesores = findViewById(R.id.btnProfesores);
        recyclerAvisos = findViewById(R.id.recyclerAvisos);

        btnMenu = findViewById(R.id.btnMenuHamburguesa);

        btnMapa.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MapaActivity.class));
        });

        btnProfesores.setOnClickListener(v ->{
            startActivity(new Intent(MainActivity.this, CarrerasActivity.class));
        });

        btnMenu.setOnClickListener(v -> {
            abrirMenuLateral();
        });

        // --- Todo tu código de Avisos se queda exactamente igual ---
        List<Aviso> avisos = new ArrayList<>();
        avisos.add(new Aviso(
                "Tianguis Gastronómico 2026",
                "Este viernes se llevará a cabo el Tianguis Gastronómico en la explanada principal. Participa con tu equipo y muestra tus habilidades.",
                "20 Feb 2026",
                "Evento"
        ));

        avisos.add(new Aviso(
                "Alerta sanitaria: Dengue",
                "Se informa a la comunidad estudiantil sobre las medidas preventivas contra el dengue. Acude al centro de salud si presentas síntomas.",
                "18 Feb 2026",
                "Urgente"
        ));

        avisos.add(new Aviso(
                "Activación de cuenta EDCORE",
                "Todos los alumnos de nuevo ingreso deberán activar su cuenta EDCORE antes del 28 de febrero para acceder a la plataforma.",
                "15 Feb 2026",
                "Tramite"
        ));

        recyclerAvisos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerAvisos.setAdapter(new AvisoAdapter(avisos));
        recyclerAvisos.setNestedScrollingEnabled(false);
    }
}