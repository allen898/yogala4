package com.example.aninterface;

import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String name;
    private String description;
    private String theme;
    private boolean is_add_to_menu;
    private boolean has_done;

    public Course() {}
    /*public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }*/
    /*public Course(int id, String name, String description, boolean is_add_to_menu) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.is_add_to_menu = false;
    }*/
    public Course(int id, String name, String description, String theme, boolean is_add_to_menu, boolean has_done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.is_add_to_menu = is_add_to_menu;
        this.has_done = has_done;
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setIs_add_to_menu(boolean is_add_to_menu) {
        this.is_add_to_menu = is_add_to_menu;
    }
    public void setHas_done(boolean has_done) {
        this.has_done = has_done;
    }

    public String getName() {
        return this.name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public String getTheme() {
        return this.theme;
    }
    public boolean getIs_add_to_menu() {
        return this.is_add_to_menu;
    }
    public boolean getHas_done() {
        return this.has_done;
    }
}
