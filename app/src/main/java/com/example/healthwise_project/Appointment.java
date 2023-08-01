package com.example.healthwise_project;

import java.util.Date;

public class Appointment {
    int id;

    int idDoctor;
    String name, phone, symptoms;
    Date datetime;
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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Appointment(int id, String name, String phone, String symptoms, Date datetime,
                       int idDoctor) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.symptoms = symptoms;
        this.datetime = datetime;
        this.idDoctor = idDoctor;
    }

    public Appointment()
    {

    }


}
