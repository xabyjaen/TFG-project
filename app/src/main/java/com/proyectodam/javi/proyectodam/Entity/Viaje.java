package com.proyectodam.javi.proyectodam.Entity;

import java.sql.Date;

public class Viaje {

    private Integer id;
    private String lugar;
    private String fecha;
    private String coordenadas;

    public Viaje() {

    }

    public Viaje(Integer id, String lugar, String fecha, String coordenadas) {
        this.id = id;
        this.lugar = lugar;
        this.fecha = fecha;
        this.coordenadas = coordenadas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}



