package com.example.mitecmm.network.response;

import com.google.gson.annotations.SerializedName;

public class HorariosRemote {
    @SerializedName("id")
    private Integer id;
    @SerializedName("docente")
    private String docente;
    @SerializedName("materia")
    private String materia;
    @SerializedName("grupo")
    private String grupo;
    @SerializedName("dia")
    private String dia;
    @SerializedName("hora_inicio")
    private String hora_inicio;
    @SerializedName("hora_fin")
    private String hora_fin;
    @SerializedName("aula")
    private String aula;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }
}
