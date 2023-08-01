package com.example.healthwise_project;

public class User {
    public String userName, healthRecord, realName;

    public User(String userName, String healthRecord, String realName) {
        this.userName = userName;
        this.healthRecord = healthRecord;
        this.realName = realName;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
