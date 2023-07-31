package com.example.healthwise_project;

import java.util.Date;

public class Appointment {
    public Appointment(String name, String symptons, String doctor, String phone, Date time) {
        this.name = name;
        this.symptons = symptons;
        this.doctor = doctor;
        this.phone = phone;
        this.time = time;
    }
    public Appointment(){

    }

    String name, symptons, doctor, phone ;
    Date time;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymptons() {
        return symptons;
    }

    public void setSymptons(String symptons) {
        this.symptons = symptons;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


}
