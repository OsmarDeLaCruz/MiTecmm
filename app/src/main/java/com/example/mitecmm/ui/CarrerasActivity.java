package com.example.mitecmm.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mitecmm.R;

public class CarrerasActivity extends AppCompatActivity{
    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_carreas);

        android.widget.TextView tCarreras = findViewById(R.id.tituloCarreras);
        if (tCarreras != null) {
            String textoConColor = "Seleccione <font color='#8A2BE2'>Carrera</font>";
            tCarreras.setText(android.text.Html.fromHtml(textoConColor, android.text.Html.FROM_HTML_MODE_LEGACY));
        }
        // 1. Encontrar tu Scrollbar (RecyclerView) en el diseño XML
        androidx.recyclerview.widget.RecyclerView rvCarreras = findViewById(R.id.rvCarreras);
        rvCarreras.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        // 2. Instanciar tu base de datos REAL
        com.example.mitecmm.database.DatabaseHelper dbHelper = new com.example.mitecmm.database.DatabaseHelper(this);

        // 3. Pedirle al archivero que nos dé la lista de carreras que guardó
        java.util.List<com.example.mitecmm.model.Carrera> listaReal = dbHelper.obtenerTodasLasCarreras();

        android.widget.Toast.makeText(this, "Carreras encontradas: " + listaReal.size(), android.widget.Toast.LENGTH_LONG).show();

        // 4. Entregarle los datos verdaderos a tu Adapter (El mesero)
        com.example.mitecmm.adapter.CarrerasAdapter adaptador = new com.example.mitecmm.adapter.CarrerasAdapter(listaReal);
        rvCarreras.setAdapter(adaptador);


    }

}
