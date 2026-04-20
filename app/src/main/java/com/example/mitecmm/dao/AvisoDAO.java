package com.example.mitecmm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mitecmm.database.DatabaseHelper;
import com.example.mitecmm.model.Aviso;

import java.util.ArrayList;
import java.util.List;

public class AvisoDAO {

    private DatabaseHelper dbHelper;
    public AvisoDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    public List<Aviso> getAll(){
        List<Aviso> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM avisos ORDER BY idAviso DESC", null);

        if(cursor.moveToFirst()){
            do{
                lista.add(new Aviso(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Aviso> showAll() {
        List<Aviso> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM avisos ORDER BY idAviso DESC", null);
        if (cursor.moveToFirst()) {
            do {
                lista.add(new Aviso(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public boolean insert(String titulo, String desc, String fecha, String categoria){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("descripción", desc);
        values.put("fecha",fecha);
        values.put("categoria",categoria);

        long result = db.insert("aviso", null, values);
        db.close();
        return result != -1;
    }

    public boolean update(int id, String titulo, String desc, String fecha, String categoria){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("descripcion",desc);
        values.put("fecha",fecha);
        values.put("categria",categoria);

        int rows = db.update("avisos", values, "idAviso=?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public boolean delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete("avisos", "idAviso=?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }
}
