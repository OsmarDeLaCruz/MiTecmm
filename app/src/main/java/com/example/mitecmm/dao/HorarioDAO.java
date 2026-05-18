package com.example.mitecmm.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mitecmm.database.DatabaseHelper;
import com.example.mitecmm.model.Horario;

import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {
    private DatabaseHelper dbHelper;
    public HorarioDAO(Context context){ dbHelper = new DatabaseHelper(context);}

    public List<Horario> showAll(){
        List<Horario> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM horarios_docentes", null);

        if (cursor.moveToFirst()){
            do{
                lista.add(new Horario(
                        cursor.getInt(cursor.getColumnIndexOrThrow("idRemoto")),
                        cursor.getString(cursor.getColumnIndexOrThrow("docente")),
                        cursor.getString(cursor.getColumnIndexOrThrow("materia")),
                        cursor.getString(cursor.getColumnIndexOrThrow("grupo")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dia")),
                        cursor.getString(cursor.getColumnIndexOrThrow("horaInicio")),
                        cursor.getString(cursor.getColumnIndexOrThrow("horaFin")),
                        cursor.getString(cursor.getColumnIndexOrThrow("aula"))
                ));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Horario> getByDocente(String nombreDocente){
        List<Horario> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM horarios_docentes WHERE docente=?",
                new String[]{nombreDocente}
        );

        if(cursor.moveToFirst()){
            do{
                lista.add(
                        new Horario(
                                cursor.getInt(cursor.getColumnIndexOrThrow("idRemoto")),
                                cursor.getString(cursor.getColumnIndexOrThrow("docente")),
                                cursor.getString(cursor.getColumnIndexOrThrow("materia")),
                                cursor.getString(cursor.getColumnIndexOrThrow("grupo")),
                                cursor.getString(cursor.getColumnIndexOrThrow("dia")),
                                cursor.getString(cursor.getColumnIndexOrThrow("horaInicio")),
                                cursor.getString(cursor.getColumnIndexOrThrow("horaFin")),
                                cursor.getString(cursor.getColumnIndexOrThrow("aula"))
                        )
                );

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    /*
    public List<Horario> obtenerPorCarrera(String carrera){

        List<Horario> lista=new ArrayList<>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();

        Cursor c=db.rawQuery(
                "SELECT * FROM horarios_docentes WHERE grupo=?",
                new String[]{carrera}
        );

        if(c.moveToFirst()){

            do{

                lista.add(
                        new Horario(
                                c.getInt(1),
                                c.getString(2),
                                c.getString(3),
                                c.getString(4),
                                c.getString(5),
                                c.getString(6),
                                c.getString(7),
                                c.getString(8)
                        )
                );

            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return lista;
    }*/
    public List<Horario> obtenerPorDocente(String docente) {
        return getByDocente(docente);
    }

    public  List<Horario> obtenerTodos(){
        return showAll();
    }
}
