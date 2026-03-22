package com.example.mitecmm.model;

public class Profesor {

int idProfesor;
String Nombre;
String Carrera;

public Profesor(String Nombre, String Carrera){
    this.Nombre= Nombre;
    this.Carrera = Carrera;
    this.idProfesor = idProfesor;

}

    public String getCarrera() {
        return Carrera;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getIdProfesor(){
        return idProfesor;
    }
}
