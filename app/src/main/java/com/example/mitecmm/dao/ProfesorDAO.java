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

    public long insertarP(String nombre, int idCarrera, String urlHorario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("idCarrera", idCarrera);
        valores.put("urlHorario", urlHorario);

        long resultado = db.insert("profesores", null, valores );
        db.close();

        return resultado;
    }

    public List<Profesor> showAll(){
        List<Profesor> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM profesores", null);

        if(cursor.moveToFirst()){
            do{
                Profesor p = new Profesor(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2));
                int urlIndex = cursor.getColumnIndex("urlHorario");
                if(urlIndex != -1) {
                    p.setUrlHorario(cursor.getString(urlIndex));                }
                lista.add(p);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lista;
    }

    public List<Profesor> obtenerPorCarrera(String siglas) {
        List<Profesor> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT p.idProfesores, p.nombre, p.idCarrera, p.urlHorario " +
                "FROM profesores p " +
                "INNER JOIN carreras c ON p.idCarrera = c.idCarrera " +
                "WHERE c.siglas = ?";

        Cursor cursor = db.rawQuery(query, new String[]{siglas});

        if(cursor.moveToFirst()){
            do{
                Profesor p = new Profesor(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2));

                int urlIndex = cursor.getColumnIndex("urlHorario");
                if(urlIndex != -1) {
                    p.setUrlHorario(cursor.getString(urlIndex));
                }
                lista.add(p);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lista;
    }

    public  boolean actualizar(int id, String nombre, int idCarrera, String urlHorario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("idCarrera", idCarrera);
        valores.put("urlHorario", urlHorario);
        int rows = db.update("profesores", valores, "idProfesores=?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public boolean eliminar(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete("profesores", "idProfesores=?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }
}
