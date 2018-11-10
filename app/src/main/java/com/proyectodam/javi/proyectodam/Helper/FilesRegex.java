package com.proyectodam.javi.proyectodam.Helper;

import java.util.ArrayList;

public class FilesRegex {

    private String editedFile;
    private String query;
    private ArrayList<String> selectedItems;

    public FilesRegex(ArrayList<String> selectedItems)
    {
        this.selectedItems = selectedItems;
        this.query = "";
    }

    public String getQuery()
    {
        for (int i = 0; i < this.selectedItems.size(); i++) {
            editedFile = regexFile(selectedItems.get(i));
            this.query = this.query + editedFile + " ";
        }
        return this.query;
    }


    public String regexFile(String file)
    {
        char fileStep = '\\';
        char coma = ',';
        char interrogationMark = '?';
        int length = file.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = file.charAt(i);
            if (ch == ' ' || ch == coma || ch == interrogationMark) {
                sb.append(fileStep);
                sb.append(ch);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
