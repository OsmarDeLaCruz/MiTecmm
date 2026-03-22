package com.example.mitecmm.model;

public class Carrera {
    String Nombre;
    String siglas;
    int idCarrera;

    public Carrera(int idCarrera, String Nombre, String siglas){
        this.Nombre = Nombre;
        this.idCarrera = idCarrera;
        this.siglas = siglas;
    }

    public String getNombre(){
        return Nombre;
    }

    public int getIdCarrera (){
        return idCarrera;
    }
    
    public String getSiglas(){ return siglas;}
}


