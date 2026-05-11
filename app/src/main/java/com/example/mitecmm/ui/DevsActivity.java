package com.example.mitecmm.ui;

import android.os.Bundle;
import com.example.mitecmm.R;
import com.google.android.material.button.MaterialButton;


public class DevsActivity extends BaseMActivity{

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_devs);

        MaterialButton btnCancelar = findViewById(R.id.btn_cerrar_devs);
        android.widget.ImageView btnMenu = findViewById(R.id.menuLo);

        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> abrirMenuLateral());
        }

        btnCancelar.setOnClickListener(v -> finish());


    }
}
