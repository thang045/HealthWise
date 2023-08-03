package com.example.healthwise_project.Model;

public class HistoryAppointment {
    String date;
    int idDoctor;

    public HistoryAppointment(String date, int idDoctor) {
        this.date = date;
        this.idDoctor = idDoctor;
    }

    public HistoryAppointment(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }




}
