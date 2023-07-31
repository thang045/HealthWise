package com.example.healthwise_project;

import android.widget.ImageView;

public class User {
    public String userName, healthRecord, yourAppointment;

    public User(String userName, String healthRecord, String yourAppointment) {
        this.userName = userName;
        this.healthRecord = healthRecord;
        this.yourAppointment = yourAppointment;

    }
    public User()
    {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHealthRecord() {
        return healthRecord;
    }

    public void setHealthRecord(String healthRecord) {
        this.healthRecord = healthRecord;
    }

    public String getYourAppointment() {
        return yourAppointment;
    }

    public void setYourAppointment(String yourAppointment) {
        this.yourAppointment = yourAppointment;
    }
}
