package com.example.mitecmm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mitecmm.R;
import com.example.mitecmm.dao.AdminDAO;

public class LoginActivity extends BaseMActivity {

    ImageView btnMenu;
    EditText userLo, passLo;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnMenu = findViewById(R.id.menuLo);
        btnMenu.setOnClickListener(v -> abrirMenuLateral());

        userLo = findViewById(R.id.userLo);
        passLo = findViewById(R.id.passLo);
        login = findViewById(R.id.buttonLo);

        login.setOnClickListener(v -> {
            String usuario = userLo.getText().toString().trim();
            String password = passLo.getText().toString().trim();

            AdminDAO adminDAO = new AdminDAO(LoginActivity.this);

            if(adminDAO.validarAdmin(usuario, password)){
                Toast.makeText(LoginActivity.this, "Bienvenido Jefe", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}