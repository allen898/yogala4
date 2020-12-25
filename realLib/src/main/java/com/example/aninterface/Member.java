package com.example.aninterface;

import java.io.Serializable;
import java.util.ArrayList;

public class Member implements Serializable {
    private String firstname;
    private String lastname;
    private String email;
    private ArrayList<Course> menu;

    // Constructor （帳號部分還沒加！！！）
    public Member(String firstname, String lastname, String email){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
    public Member(){

    }

    //methods
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setMenu(ArrayList<Course> menu) {
        menu = new ArrayList<Course>();
        this.menu = menu;
    }
    public void add_course_to_menu(Course course) {
        menu = new ArrayList<Course>();
        this.menu.add(course);
    }
    public void remove_course_from_menu(Course course) {
        menu = new ArrayList<Course>();
        this.menu.remove(course);
    }
    public String getFirstname(){
        return this.firstname;
    }
    public String getLastname(){
        return this.lastname;
    }
    public String getEmail(){
        return this.email;
    }
    public int getMenuSize() {
        menu = new ArrayList<Course>();
        return this.menu.size();
    }
    public boolean menu_isEmpty() {
        menu = new ArrayList<Course>();
        return this.menu.isEmpty();
    }
    public Course get_course_from_menu(int index) {
        menu = new ArrayList<Course>();
        return this.menu.get(index);
    }
    public int get_course_index(Course course) {
        menu = new ArrayList<Course>();
        return this.menu.indexOf(course);
    }
    public ArrayList<Course> getMenu() {
        menu = new ArrayList<Course>();
        return this.menu;
    }
}