package com.example.healthwise_project.Model;

public class HistoryAppointment {
    String date;
    String nameDoctor;

    public HistoryAppointment(String date, String nameDoctor) {
        this.date = date;
        this.nameDoctor = nameDoctor;
    }

    public HistoryAppointment(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameDoctor() {
        return nameDoctor;
    }

    public void setIdDoctor(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }




}
