package com.example.mitecmm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mitecmm.database.DatabaseHelper;
import com.example.mitecmm.model.Profesor;

import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {
    private DatabaseHelper dbHelper;

    public ProfesorDAO(Context context){
        this.dbHelper = new DatabaseHelper(context);
    }

    public long insertarP(String nombre, int idCarrera){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("idCarrera", idCarrera);

        long resultado = db.insert("profesores", null, valores );
        db.close();

        return resultado;
    }

    public List<com.example.mitecmm.model.Profesor> showAll(){
        List<Profesor> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM profesores", null);

        if(cursor.moveToFirst()){
            do{
                lista.add(new Profesor(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lista;


    }
}
