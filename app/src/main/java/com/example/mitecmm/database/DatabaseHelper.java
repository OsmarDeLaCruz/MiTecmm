package com.example.mitecmm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mitecmm_datos.db";
    private static final int DATABASE_VERSION = 8;

    private static final  String tablaHorarios =
            "CREATE TABLE horarios_docentes("+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "idRemoto INTEGER,"+
                    "docente TEXT,"+
                    "materia TEXT,"+
                    "grupo TEXT,"+
                    "dia TEXT,"+
                    "horaInicio TEXT,"+
                    "horaFin TEXT,"+
                    "aula TEXT)";

    private static final String tablaCarreras =
            "CREATE TABLE carreras ( " +
                    "idCarrera INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "siglas TEXT NOT NULL)";

    private static final String tablaProfesores =
            "CREATE TABLE profesores ( "+
                    "idProfesores INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "nombre TEXT NOT NULL, " +
                    "idCarrera INTEGER NOT NULL ," +
                    "urlHorario TEXT ," +
                    "FOREIGN KEY (idCarrera) REFERENCES CARRERAS (idCarrera))";

    private static final String tablaAdmin =
            "CREATE TABLE  admin (" +
                    "idAdmin INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "usuario TEXT NOT NULL, "+
                    "password TEXT NOT NULL)";


    private static final String tablaAviso = "CREATE TABLE avisos ("+
            "idAviso INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "idRemoto INTEGER,"+
            "titulo TEXT NOT NULL,"+
            "descripcion TEXT NOT NULL,"+
            "fecha TEXT NOT NULL,"+
            "categoria TEXT NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tablaCarreras);
        db.execSQL(tablaProfesores);
        db.execSQL(tablaAdmin);
        db.execSQL(tablaAviso);
        db.execSQL(tablaHorarios);

        //estas son provicionales las vamos a quitar
        db.execSQL("INSERT INTO carreras (nombre, siglas) VALUES ('Ingenieria en Sistemas Computacionales' , 'ISC')");


        db.execSQL("INSERT INTO admin (usuario, password) VALUES ('admin', 'admineo12')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS profesores");
        db.execSQL("DROP TABLE IF EXISTS carreras");
        db.execSQL("DROP TABLE IF EXISTS admin");
        db.execSQL("DROP TABLE IF EXISTS avisos");
        db.execSQL("DROP TABLE IF EXISTS horarios_docentes");

        onCreate(db);

    }


    //esta funcion es provicional la vamos a quitar
    public java.util.List<com.example.mitecmm.model.Carrera> obtenerTodasLasCarreras() {
        java.util.List<com.example.mitecmm.model.Carrera> lista = new java.util.ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM carreras", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String siglas = cursor.getString(2);
                lista.add(new com.example.mitecmm.model.Carrera(id, nombre, siglas));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lista;
    }
}
