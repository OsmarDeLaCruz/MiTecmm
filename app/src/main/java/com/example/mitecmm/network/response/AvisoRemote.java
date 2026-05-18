package com.example.mitecmm.network.response;

public class AvisoRemote {
    private Integer id;
    private String titulo;
    private String contenido;
    private String fecha_publicacion;
    private boolean es_urgente;
    private String subtitulo;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public boolean isEs_urgente() {
        return es_urgente;
    }

    public void setEs_urgente(boolean es_urgente) {
        this.es_urgente = es_urgente;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
}
