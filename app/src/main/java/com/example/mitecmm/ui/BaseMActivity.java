package com.example.mitecmm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.mitecmm.R;
import com.google.android.material.navigation.NavigationView;

public class BaseMActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    @Override
    public void setContentView(int layoutResID) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_basem, null);

        FrameLayout contenedor = drawerLayout.findViewById(R.id.contenedor_pantallas);

        getLayoutInflater().inflate(layoutResID, contenedor, true);

        super.setContentView(drawerLayout);

        NavigationView navView = drawerLayout.findViewById(R.id.nav_view_base);
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nv_inicio) {
                    android.content.Intent intent = new android.content.Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

            }else if  (id == R.id.nv_admin) {
                android.content.Intent intent = new android.content.Intent(this, LoginActivity.class);
                startActivity(intent);
            }else if ( id == R.id.nv_owners){

            }


            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    public void abrirMenuLateral() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}