package com.example.mitecmm.model;

public class Profesor {
String Nombre;
String Carrera;

public Profesor(String Nombre, String Carrera){
    this.Nombre= Nombre;
    this.Carrera = Carrera;

}

    public String getCarrera() {
        return Carrera;
    }

    public String getNombre() {
        return Nombre;
    }
}
