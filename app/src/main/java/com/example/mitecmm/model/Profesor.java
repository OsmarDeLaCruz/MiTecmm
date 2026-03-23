package com.example.mitecmm.model;

public class Profesor {

int idProfesor;
String Nombre;
int idCarrera;

public Profesor(int idProfesor, String Nombre, int idCarrera){
    this.idProfesor = idProfesor;
    this.Nombre= Nombre;
    this.idCarrera = idCarrera;

}

    public int getCarrera() {
        return idCarrera;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getIdProfesor(){
        return idProfesor;
    }
}
