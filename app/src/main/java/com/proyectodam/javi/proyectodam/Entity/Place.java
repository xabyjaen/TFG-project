package com.proyectodam.javi.proyectodam.Entity;

public class Place {

    private Integer id;
    private String name;
    private String coordinates;
    private Integer folderId;

    public Place() { }

    public Place(Integer id, String name, String coordinates, Integer folderId) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.folderId = folderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }
}



