package com.example.mitecmm.model;

public class Profesor {

int idProfesor;
String nombre;
int idCarrera;
String nombreCarrera;


public Profesor(int idProfesor, String nombre, int idCarrera, String nombreCarrera){
    this.idProfesor = idProfesor;
    this.nombre = nombre;
    this.idCarrera = idCarrera;
    this.nombreCarrera = nombreCarrera;
}
public Profesor(int idProfesor, String nombre, int idCarrera){
    this.idProfesor = idProfesor;
    this.nombre= nombre;
    this.idCarrera = idCarrera;

}

    public int getCarrera() {
        return idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdProfesor(){
        return idProfesor;
    }
    public String getNombreCarrera() {return nombreCarrera;}
}
