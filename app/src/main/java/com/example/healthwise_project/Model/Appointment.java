package com.example.healthwise_project.Model;

public class Appointment {
    int id;
    int idDoctor ;
    String name, phone, symptoms,idUser;
    String datetime;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Appointment(int id, String name, String phone, String symptoms, String datetime,
                       int idDoctor, String idUser) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.symptoms = symptoms;
        this.datetime = datetime;
        this.idDoctor = idDoctor;
        this.idUser = idUser;
    }

    public Appointment()
    {

    }


}
