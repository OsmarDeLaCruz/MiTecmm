package com.example.mitecmm.model;

public class Aviso {
    private String titulo;
    private String descripcion;
    private String fecha;
    private String categoria;


    public Aviso(String titulo, String descripcion, String fecha, String categoria){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.categoria = categoria;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getFecha(){
        return fecha;
    }

    public String getCategoria() {return categoria;}
}
