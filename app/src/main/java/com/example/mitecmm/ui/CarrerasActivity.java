package com.example.mitecmm.ui;

import android.os.Bundle;
import com.example.mitecmm.R;
import com.example.mitecmm.adapter.CarrerasAdapter;
import com.example.mitecmm.adapter.HorarioCardAdapter;
import com.example.mitecmm.dao.CarreraDAO;
import com.example.mitecmm.dao.HorarioDAO;
import com.example.mitecmm.model.Carrera;
import com.example.mitecmm.model.Horario;

import java.util.List;

public class CarrerasActivity extends BaseMActivity{
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreas);

        android.widget.ImageView btnMenu = findViewById(R.id.menuham);
        btnMenu.setOnClickListener(v -> abrirMenuLateral());

        androidx.recyclerview.widget.RecyclerView rvCarreras = findViewById(R.id.recyclerCarreras);
        rvCarreras.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        //muestra las carreas en el carreras_nav
        //CarreraDAO carreraDAO = new CarreraDAO(this);
        //List<Carrera> listaReal = carreraDAO.showAll();
        //CarrerasAdapter adaptador = new CarrerasAdapter(listaReal);
        //rvCarreras.setAdapter(adaptador);

        HorarioDAO horarioDAO = new HorarioDAO(this);
        List<Horario> horarios = horarioDAO.obtenerTodos();
        HorarioCardAdapter adaptador = new HorarioCardAdapter(horarios);
        rvCarreras.setAdapter(adaptador);

        android.widget.EditText etBuscarCarrera = findViewById(R.id.etBuscarCarrera);
        android.widget.TextView tvSinResultados = findViewById(R.id.tvSinResultadosCarreras);


        if (etBuscarCarrera != null) {
            etBuscarCarrera.addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(android.text.Editable s) {
                    adaptador.buscar(s.toString());

                    if (tvSinResultados != null) {
                        if (adaptador.getItemCount() == 0) {
                            tvSinResultados.setVisibility(android.view.View.VISIBLE);
                            rvCarreras.setVisibility(android.view.View.GONE);
                        } else {
                            tvSinResultados.setVisibility(android.view.View.GONE);
                            rvCarreras.setVisibility(android.view.View.VISIBLE);
                        }
                    }
                }
            });
        }
    }
}