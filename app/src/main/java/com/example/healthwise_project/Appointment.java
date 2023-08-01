package com.example.healthwise_project;

import com.google.type.DateTime;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Appointment {
    int id;
    String name, phone, sympton;
    Date datetime;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSympton() {
        return sympton;
    }

    public void setSympton(String sympton) {
        this.sympton = sympton;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Appointment(int id, String name, String phone, String sympton, Date datetime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sympton = sympton;
        this.datetime = datetime;
    }

//    public static ArrayList<Appointment>initAppData(int[] lsid, String[] lsname, String[] lsphone, String[] lssympton, Date lsdate){
//        ArrayList<Appointment> appList =new ArrayList<Appointment>();
//        for(int i =0; i<lsid.length;i++){
//            Appointment app = new Appointment(lsid[i], lsname[i], lsphone[i], lssympton[i], lsdate);
//            appList.add(app);
//        }
//        return  appList;
//     };
}