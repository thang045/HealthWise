package com.example.healthwise_project;

public class Doctor {
    String name, department;

    int id;

    double experience;

    public Doctor(String name, String department, int id, double experience) {
        this.name = name;
        this.department = department;
        this.id = id;
        this.experience = experience;
    }

    public Doctor(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }
}
