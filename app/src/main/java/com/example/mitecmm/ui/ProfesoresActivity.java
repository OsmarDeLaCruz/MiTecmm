package com.example.mitecmm.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mitecmm.R;

public class ProfesoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profesores);

        android.widget.TextView tvTitulo = findViewById(R.id.tvTituloProfesores);
        if (tvTitulo != null) {
            String textoConColor = "Nuestros <font color='#8A2BE2'>Profesores</font>";
            tvTitulo.setText(android.text.Html.fromHtml(textoConColor, android.text.Html.FROM_HTML_MODE_LEGACY));
        }
    }
}