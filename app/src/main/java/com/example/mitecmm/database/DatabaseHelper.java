package com.example.mitecmm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mitecmm_datos.db";
    private static final int DATABASE_VERSION = 3;

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
                    "FOREIGN KEY (idCarrera) REFERENCES CARRERAS (idCarrera))";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tablaCarreras);
        db.execSQL(tablaProfesores);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS profesores");
        db.execSQL("DROP TABLE IF EXISTS carreras");

        onCreate(db);


        db.execSQL("INSERT INTO carreras (nombre, siglas) VALUES ('Ingenieria en Sistemas Computacionales' , 'ISC')");
        db.execSQL("INSERT INTO carreras (nombre, siglas) VALUES ('Ingenieria Industrial', 'II')");

    }

    public java.util.List<com.example.mitecmm.model.Carrera> obtenerTodasLasCarreras() {
        java.util.List<com.example.mitecmm.model.Carrera> lista = new java.util.ArrayList<>();

        // 1. Abrimos la base de datos en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. Hacemos la consulta SQL (Traer todo de la tabla carreras)
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM carreras", null);

        // 3. Recorremos los resultados fila por fila
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);          // Columna 0: idCarrera
                String nombre = cursor.getString(1); // Columna 1: nombre
                String siglas = cursor.getString(2); // Columna 2: siglas

                // Agregamos cada carrera a nuestra lista de Java
                lista.add(new com.example.mitecmm.model.Carrera(id, nombre, siglas));
            } while (cursor.moveToNext());
        }

        // 4. Cerramos las conexiones para no gastar batería
        cursor.close();
        db.close();

        return lista;
    }
}
