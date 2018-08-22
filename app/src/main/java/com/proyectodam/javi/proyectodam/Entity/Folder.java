package com.proyectodam.javi.proyectodam.Entity;

public class Folder {

    private Integer id;
    private String name;
    private String files;
    private Integer numberFiles = 0;
    private String date;

    public Folder() {

    }

    public Folder(Integer id, String name, String files, Integer numberFiles, String date) {
        this.id = id;
        this.name = name;
        this.files = files;
        this.numberFiles = numberFiles;
        this.date = date;
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

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Integer getNumberFiles() {
        return numberFiles;
    }

    public void setNumberFiles(Integer numberFiles) {
        this.numberFiles = numberFiles;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}



