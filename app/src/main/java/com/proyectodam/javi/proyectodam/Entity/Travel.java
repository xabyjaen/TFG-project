package com.proyectodam.javi.proyectodam.Entity;

public class Travel {

    private Integer id;

    private String comments;

    private String people;

    private Integer idPlace;

    private Integer idFolder;

    public Travel() {
    }

    public Travel(int id, String comments, String people, int idPlace, int idFolder) {
        this.id = id;
        this.comments = comments;
        this.people = people;
        this.idPlace = idPlace;
        this.idFolder = idFolder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public Integer getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(Integer idPlace) {
        this.idPlace = idPlace;
    }

    public Integer getIdFolder() {
        return idFolder;
    }

    public void setIdFolder(Integer idFolder) {
        this.idFolder = idFolder;
    }
}
