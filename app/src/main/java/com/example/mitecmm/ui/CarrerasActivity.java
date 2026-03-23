package com.example.mitecmm.ui;

import android.os.Bundle;
import com.example.mitecmm.R;
import com.example.mitecmm.dao.CarreraDAO;
import com.example.mitecmm.model.Carrera;
import java.util.List;

public class CarrerasActivity extends BaseMActivity{
    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_carreas);


        android.widget.ImageView btnMenu = findViewById(R.id.btnMenuHamburguesa);
        btnMenu.setOnClickListener(v -> abrirMenuLateral());

        android.widget.TextView tCarreras = findViewById(R.id.tituloCarreras);
        if (tCarreras != null) {
            String textoConColor = "Seleccione <font color='#8A2BE2'>Carrera</font>";
            tCarreras.setText(android.text.Html.fromHtml(textoConColor, android.text.Html.FROM_HTML_MODE_LEGACY));
        }

        androidx.recyclerview.widget.RecyclerView rvCarreras = findViewById(R.id.rvCarreras);
        rvCarreras.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        CarreraDAO carreraDAO = new CarreraDAO(this);

        List<Carrera> listaReal = carreraDAO.showAll();

        com.example.mitecmm.adapter.CarrerasAdapter adaptador = new com.example.mitecmm.adapter.CarrerasAdapter(listaReal);
        rvCarreras.setAdapter(adaptador);
    }
}