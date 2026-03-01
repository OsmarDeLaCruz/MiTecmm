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


    }

}
