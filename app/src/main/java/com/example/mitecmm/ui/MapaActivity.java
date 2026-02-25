package com.example.mitecmm.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mitecmm.R;

public class MapaActivity extends AppCompatActivity {

    LinearLayout filaA1, filaA2, filaB1;
    LinearLayout panelInfo;
    TextView tvNombreSalon, tvTipo, tvProfesor, tvClase;
    View selectedCard = null;

    //Datos de salones
    String[][] salonesA1 = {
            {"A-102", "Aula", "Dra. María González", "Cálculo Diferencial", ""},
            {"A-104", "Aula", "Mtro. Carlos López", "Álgebra", ""},
            {"A-105", "Aula", "Dra. Ana Martínez", "Física I", ""}
    };

    String[][] salonesA2 = {
            {"A-201", "Aula", "Ing. Roberto Díaz", "Programación I", ""},
            {"A-203", "Aula", "Mtra. Laura Pérez", "Química", ""},
            {"A-301", "Aula", "Dr. Juan Herrera", "Cálculo Integral", ""}
    };

    String[][] salonesB1 = {
            {"B-102", "Aula", "Mtra. Sofia Ruiz", "Inglés I", ""},
            {"B-201", "Aula", "Ing. Miguel Torres", "Base de Datos", ""}
    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        filaA1 = findViewById(R.id.filaA1);
        filaA2 = findViewById(R.id.filaA2);
        filaB1 = findViewById(R.id.filaB1);
        panelInfo = findViewById(R.id.panelInfo);
        tvNombreSalon = findViewById(R.id.tvNombreSalon);
        tvTipo = findViewById(R.id.tvTipo);
        tvProfesor = findViewById(R.id.tvProfesor);
        tvClase = findViewById(R.id.tvClase);

        agregarSalones(filaA1,salonesA1);
        agregarSalones(filaA2, salonesA2);
        agregarSalones(filaB1,salonesB1);

        findViewById(R.id.contenedorMapa).setOnClickListener(v ->{
            EditText etBuscar = findViewById(R.id.etBuscar);
            etBuscar.clearFocus();
        });
    }

    private void agregarSalones(LinearLayout fila, String[][] salones){

        for(String[] salon : salones){
            //card
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setGravity(Gravity.CENTER);
            card.setBackground(ContextCompat.getDrawable(this, R.drawable.card_salon));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(120), dpToPx(90));
            params.setMargins(dpToPx(6), 0, dpToPx(6), 0);
            card.setLayoutParams(params);
            card.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));

            //nombre
            TextView tvNombre = new TextView(this);
            tvNombre.setText(salon[0]);
            tvNombre.setTextColor(Color.WHITE);
            tvNombre.setTextSize(16);
            tvNombre.setTypeface(null, Typeface.BOLD);
            tvNombre.setGravity(Gravity.CENTER);

            //tipo
            TextView tvTipoCard = new TextView(this);
            tvTipoCard.setText(salon[1]);
            tvTipoCard.setTextColor(Color.parseColor("#888888"));
            tvTipoCard.setTextSize(12);
            tvTipoCard.setGravity(Gravity.CENTER);

            card.addView(tvNombre);
            card.addView(tvTipoCard);

            card.setOnClickListener(v ->{
                //quitar selección anterior
                if(selectedCard != null){
                    selectedCard.setBackground(ContextCompat.getDrawable(this, R.drawable.card_salon));
                }

                //seleccionar
                card.setBackground(ContextCompat.getDrawable(this, R.drawable.card_salon_selected));
                selectedCard = card;

                //mostrar info
                tvNombre.setText(salon[0]);
                tvTipo.setText("Tipo: "+salon[1]);
                tvProfesor.setText("Profesor: "+salon[2]);
                tvClase.setText("Clase: "+salon[3]);
                panelInfo.setVisibility(View.VISIBLE);
            });

            fila.addView(card);
        }
    }

    private int dpToPx(int dp){
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
