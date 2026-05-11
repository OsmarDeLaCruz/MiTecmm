package com.example.mitecmm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitecmm.R;
import com.example.mitecmm.adapter.ProfesorAdapter;
import com.example.mitecmm.dao.ProfesorDAO;
import com.example.mitecmm.model.Profesor;

import java.util.List;

public class ProfesoresActivity extends BaseMActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesores);


        EditText etBuscarProfesor = findViewById(R.id.etBuscadorProfesores);

        ImageView btnMenu = findViewById(R.id.menuham);
        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> abrirMenuLateral());
        }

        RecyclerView recyclerProfesores = findViewById(R.id.recyclerProfesores);
        if (recyclerProfesores != null) {
            recyclerProfesores.setLayoutManager(new LinearLayoutManager(this));
        }

        String carreraFiltro = getIntent().getStringExtra("CARRERA_SELECCIONADA");
        if (carreraFiltro != null) {
            cargarProfesoresPorCarrera(carreraFiltro);
        } else {
            cargarTodosLosProfesores();
        }


        if (etBuscarProfesor != null) {
            etBuscarProfesor.addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(android.text.Editable s) {
                    if (recyclerProfesores != null && recyclerProfesores.getAdapter() != null) {
                        ProfesorAdapter adaptador = (ProfesorAdapter) recyclerProfesores.getAdapter();
                        adaptador.busqueda(s.toString());
                    }
                }
            });
        }
    }

    private void cargarProfesoresPorCarrera(String siglas) {
       ProfesorDAO dao = new ProfesorDAO(this);

        List<Profesor> listaProfesores = dao.obtenerPorCarrera(siglas);
        if(listaProfesores != null && !listaProfesores.isEmpty()){
          ProfesorAdapter adaptador = new ProfesorAdapter(listaProfesores);
          RecyclerView recyclerProfesores = findViewById(R.id.recyclerProfesores);
          recyclerProfesores.setAdapter(adaptador);
        }else{
            Toast.makeText(this, "No hay profesores registrados para " + siglas, Toast.LENGTH_LONG).show();

        }
    }

    private void cargarTodosLosProfesores() {

    }
}