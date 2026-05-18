package com.example.mitecmm.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitecmm.R;
import com.example.mitecmm.adapter.ProfesorAdapter;
import com.example.mitecmm.dao.HorarioDAO;
import com.example.mitecmm.dao.ProfesorDAO;
import com.example.mitecmm.model.Horario;
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

        String carreraFiltro = getIntent().getStringExtra("CARRERA_SIGLAS");
        //int idCarrera = getIntent().getIntExtra("CARRERA_ID", -1);
        Log.d("DEBUG", "ID recibido: " + carreraFiltro);
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
        if(listaProfesores == null){
            Log.d("DEBUG","La lista vino NULL");
            return;
        }
        android.util.Log.d("DEBUG", "Total Encontrados: "+ listaProfesores.size());

        if(listaProfesores != null && !listaProfesores.isEmpty()){
          ProfesorAdapter adaptador = new ProfesorAdapter(listaProfesores, profesor -> {
              HorarioDAO horarioDAO = new HorarioDAO(this);
              List<Horario> horarios = horarioDAO.getByDocente(profesor.getNombre());
              String tex = "";

              for (Horario h : horarios){
                  tex += h.getMateria()+"\n"+
                          h.getDia()+" "+
                          h.getHoraInicio()+" - "+
                          h.getHoraFin()+"\n"+
                          h.getAula()+"\n\n";
              }

              new AlertDialog.Builder(this).setTitle(profesor.getNombre()).setMessage(tex.isEmpty() ? "Sin horario" : tex).setPositiveButton("Cerrar", null).show();
          });
          RecyclerView recyclerProfesores = findViewById(R.id.recyclerProfesores);
          recyclerProfesores.setAdapter(adaptador);
        }else{
            Toast.makeText(this, "No hay profesores registrados para " + siglas, Toast.LENGTH_LONG).show();

        }
    }

    private void cargarTodosLosProfesores() {
        ProfesorDAO dao = new ProfesorDAO(this);
        List<Profesor> lista = dao.showAll();
        RecyclerView recyclerProfesores = findViewById(R.id.recyclerProfesores);
        ProfesorAdapter adaptador = new ProfesorAdapter(lista, profesor -> {
            HorarioDAO horarioDAO = new HorarioDAO(this);
            List<Horario> horarios = horarioDAO.getByDocente(profesor.getNombre());
            StringBuilder tex = new StringBuilder();
            for (Horario h : horarios) {
                tex.append(h.getMateria()).append("\n")
                        .append(h.getDia()).append(" ")
                        .append(h.getHoraInicio()).append(" - ")
                        .append(h.getHoraFin()).append("\n")
                        .append(h.getAula()).append("\n\n");
            }
            new AlertDialog.Builder(this)
                    .setTitle(profesor.getNombre())
                    .setMessage(tex.toString().isEmpty() ? "Sin horario" : tex.toString())
                    .setPositiveButton("Cerrar", null).show();
        });
        recyclerProfesores.setAdapter(adaptador);
    }
}