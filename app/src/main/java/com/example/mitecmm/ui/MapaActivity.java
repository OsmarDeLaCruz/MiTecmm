package com.example.mitecmm.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mitecmm.R;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class  MapaActivity extends AppCompatActivity {

    LinearLayout filaA1, filaA2, filaB1;
    LinearLayout panelInfo;
    TextView tvNombreSalon, tvTipo, tvProfesor, tvClase;
    View selectedCard = null;
    Spinner spinnerEdificios, spinnerPiso, spinnerTipoEdificios;
    EditText etBuscar;

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
        spinnerEdificios = findViewById(R.id.spinnerEdificios);
        spinnerPiso = findViewById(R.id.spinnerPiso);
        spinnerTipoEdificios = findViewById(R.id.spinnerTipoEdificio);
        etBuscar = findViewById(R.id.etBuscar);

        agregarSalones(filaA1,salonesA1);
        agregarSalones(filaA2, salonesA2);
        agregarSalones(filaB1,salonesB1);

        findViewById(R.id.contenedorMapa).setOnClickListener(v ->{
            EditText etBuscar = findViewById(R.id.etBuscar);
            etBuscar.clearFocus();
        });

        //Spinner edificios
        ArrayAdapter<String> adapterEdifcios = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                new String[]{"Todos los edificios", "Edificio A", "Edificio B"});
        adapterEdifcios.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerEdificios.setAdapter(adapterEdifcios);

        //Spinner pisos
        ArrayAdapter<String> adapterPisos = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                new String[]{"Todos los pisos", "Piso 1", "Piso 2", "Piso 3"});
        adapterPisos.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerPiso.setAdapter(adapterPisos);

        //Spinner tipos
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                new String[]{"Todos los tipos", "Aula", "Laboratorio", "Taller"});
        adapterTipos.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTipoEdificios.setAdapter(adapterTipos);

        //Buscar
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarSalones();
            }
        });

        spinnerEdificios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtrarSalones();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerPiso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtrarSalones();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerTipoEdificios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtrarSalones();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
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

    //Buscador
    private  void filtrarSalones() {

        String busqueda = etBuscar.getText().toString().toLowerCase().trim();
        String edificio = spinnerEdificios.getSelectedItem().toString();
        String tipo = spinnerTipoEdificios.getSelectedItem().toString();

        //limapia las filas
        filaA1.removeAllViews();
        filaA2.removeAllViews();
        filaB1.removeAllViews();

        //se filtra y agrega (re-agrega)
        if(edificio.equals("Todos los edificios") || edificio.equals("Edificio A")){
            String[][] filtradoA1 = filtrar(salonesA1, busqueda, tipo);
            String[][] filtradoA2 = filtrar(salonesA2,busqueda, tipo);
            agregarSalones(filaA1, filtradoA1);
            agregarSalones(filaA2, filtradoA2);
        }
        if(edificio.equals("Todos los edificios") || edificio.equals("Edificio B")){
            String[][] filtradosB1 = filtrar(salonesB1, busqueda, tipo);
            agregarSalones(filaB1, filtradosB1);
        }
    }

    private String[][] filtrar(String[][] salones, String busqueda, String tipo){
        List<String[]> resultdo = new ArrayList<>();
        for(String[] salon : salones){
            boolean coincideBusqueda = busqueda.isEmpty() || salon[0].toLowerCase().contains(busqueda);
            boolean coincideTipo = tipo.equals("Todos los tipos") || salon[1].equals(tipo);
            if(coincideBusqueda && coincideTipo){
                resultdo.add(salon);
            }
        }
        return resultdo.toArray(new String[0][]);
    }
}
