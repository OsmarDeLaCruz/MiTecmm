package com.example.mitecmm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mitecmm.database.DatabaseHelper;
import com.example.mitecmm.model.Carrera;

import java.util.ArrayList;
import java.util.List;

public class CarreraDAO {
    private DatabaseHelper dbHelper;

    public CarreraDAO(Context context){
        this.dbHelper=new DatabaseHelper(context);
    }

    public long insertarC(String nombre, String siglas){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("siglas", siglas);
        long id = db.insert("carreras", null, valores);
        db.close();

        return id;

    }

    public List<com.example.mitecmm.model.Carrera> showAll(){
        List<Carrera> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM carreras", null);

        if(cursor.moveToFirst()){
            do{
                lista.add(new Carrera(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lista;
    }



}
