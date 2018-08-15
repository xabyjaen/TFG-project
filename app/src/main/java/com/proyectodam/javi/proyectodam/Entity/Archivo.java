package com.proyectodam.javi.proyectodam.Entity;

public class Archivo {

    private Integer id;
    private String archivos;
    private Integer numeroArchivos = 0;
    private Integer idViaje;

    public Archivo() {

    }

    public Archivo(Integer id, String archivos, Integer numeroArchivos) {
        this.id = id;
        this.archivos = archivos;
        this.numeroArchivos = numeroArchivos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Integer idViaje) {
        this.idViaje = idViaje;
    }

    public Integer getNumeroArchivos() {
        return numeroArchivos;
    }

    public void setNumeroArchivos(Integer numeroArchivos) {
        this.numeroArchivos = numeroArchivos;
    }

    public String getArchivos() {
        return archivos;
    }

    public void setArchivos(String archivos) {
        this.archivos = archivos;
    }


}



