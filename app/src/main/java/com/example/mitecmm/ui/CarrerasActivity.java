package com.example.mitecmm.ui;

import android.os.Bundle;
import com.example.mitecmm.R;
import com.example.mitecmm.dao.CarreraDAO;
import com.example.mitecmm.model.Carrera;
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

        CarreraDAO carreraDAO = new CarreraDAO(this);

        List<Carrera> listaReal = carreraDAO.showAll();

        com.example.mitecmm.adapter.CarrerasAdapter adaptador = new com.example.mitecmm.adapter.CarrerasAdapter(listaReal);
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