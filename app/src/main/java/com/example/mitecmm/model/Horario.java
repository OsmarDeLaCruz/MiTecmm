package com.example.mitecmm.model;

import com.google.gson.annotations.SerializedName;

public class Horario {
    @SerializedName("id")
    private int id;
    @SerializedName("docente")
    private String docente;
    @SerializedName("materia")
    private String materia;
    @SerializedName("grupo")
    private String grupo;
    @SerializedName("dia")
    private String dia;
    @SerializedName("hora_inicio")
    private String horaInicio;
    @SerializedName("hora_fin")
    private String horaFin;
    @SerializedName("aula")
    private String aula;

    public Horario(int id,String docente,String materia, String grupo,String dia, String horaInicio,String horaFin,String aula){
        this.id =id;
        this.docente=docente;
        this.materia=materia;
        this.grupo=grupo;
        this.dia=dia;
        this.horaInicio=horaInicio;
        this.horaFin=horaFin;
        this.aula=aula;
    }

    public int getId() {
        return id;
    }

    public String getDocente(){
        return docente;
    }
    public String getMateria(){
        return materia;
    }
    public String getGrupo() {
        return grupo;
    }
    public String getDia() {
        return dia;
    }
    public String getHoraInicio() {
        return horaInicio;
    }
    public String getHoraFin() {
        return horaFin;
    }
    public String getAula() {
        return aula;
    }
}
