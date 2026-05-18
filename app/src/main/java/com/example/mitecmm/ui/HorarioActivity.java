package com.example.mitecmm.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitecmm.R;
import com.example.mitecmm.adapter.HorarioCardAdapter;
import com.example.mitecmm.dao.HorarioDAO;
import com.example.mitecmm.model.Horario;

import java.util.List;

public class HorarioActivity extends BaseMActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_profesor);
        HorarioDAO horarioDAO = new HorarioDAO(this);

        String docente = getIntent().getStringExtra("DOCENTE");
        List<Horario> horarios = (docente != null) ? horarioDAO.obtenerPorDocente(docente) : horarioDAO.obtenerTodos();
        RecyclerView rv = findViewById(R.id.recyclerHorarios);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new HorarioCardAdapter(horarios));
    }
}
