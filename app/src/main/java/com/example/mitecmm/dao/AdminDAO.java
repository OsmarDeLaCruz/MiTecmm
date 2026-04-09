package com.example.mitecmm.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mitecmm.database.DatabaseHelper;

public class AdminDAO {
    private DatabaseHelper dbHelper;

    public AdminDAO(Context context){
        this.dbHelper = new DatabaseHelper(context);

    }

    public boolean validarAdmin(String usuario, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM admin WHERE usuario =? AND password =?", new String[]{usuario, password});
        boolean esValido = cursor.getCount()>0;

        cursor.close();
        db.close();
        return esValido;
    }

}

