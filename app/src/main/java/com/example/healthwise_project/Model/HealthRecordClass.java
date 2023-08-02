package com.example.healthwise_project.Model;

public class HealthRecordClass {
    String date;
    String symtomps;

    public HealthRecordClass(String date, String symtomps) {
        this.date = date;
        this.symtomps = symtomps;
    }

    public HealthRecordClass()
    {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSymtomps() {
        return symtomps;
    }

    public void setSymtomps(String symtomps) {
        this.symtomps = symtomps;
    }
}
