package com.example.mitecmm.model;

public class Profesor {

int idProfesor;
String nombre;
int idCarrera;
String nombreCarrera;

String urlHorario;


public Profesor(int idProfesor, String nombre, int idCarrera, String nombreCarrera){
    this.idProfesor = idProfesor;
    this.nombre = nombre;
    this.idCarrera = idCarrera;
    this.nombreCarrera = nombreCarrera;
}
public Profesor(int idProfesor, String nombre, int idCarrera){         //------------
    this.idProfesor = idProfesor;                                      //NO BORRAR ESTE BLOQUE
    this.nombre= nombre;                                               // PARECE QUE NO HACE
    this.idCarrera = idCarrera;                                        // NADA PERO SI xDD
                                                                       //------------
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

    public String getUrlHorario(){ return urlHorario;}
    public void setUrlHorario(String urlHorario){ this.urlHorario = urlHorario;}
}
