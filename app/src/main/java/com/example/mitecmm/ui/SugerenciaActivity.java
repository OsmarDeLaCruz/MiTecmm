package com.example.mitecmm.ui;

import android.os.Bundle;

import android.content.Intent;
import android.widget.Toast;

import com.example.mitecmm.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SugerenciaActivity extends BaseMActivity{

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_sugerencia);

        TextInputEditText etTexto = findViewById(R.id.et_sugerencia_texto);
        MaterialButton btnCancelar = findViewById(R.id.btn_cancelar_sugerencia);
        MaterialButton btnEnviar = findViewById(R.id.btn_enviar_sugerencia);

        btnCancelar.setOnClickListener(v -> finish());

        btnEnviar.setOnClickListener(v -> {
            String sugerencia = etTexto.getText().toString();

            if(sugerencia.isEmpty()){
                Toast.makeText(this, "Favor de completar los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(android.net.Uri.parse("mailto"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mitecmm@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Nueva sugerencia para MiTecmm");
            intent.putExtra(Intent.EXTRA_TEXT, "Sugerencia del usuario: \n\n");

            try {
                startActivity(Intent.createChooser(intent, "Enviar sugerencia usando:"));
                finish();
            }catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(this, "No se encontro", Toast.LENGTH_LONG).show();
            }

        });

    }
}
