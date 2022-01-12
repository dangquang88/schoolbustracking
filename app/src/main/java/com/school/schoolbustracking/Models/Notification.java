package com.school.schoolbustracking.Models;

public class Notification {
    private  Data data;
    private String to;

    public Notification(Data data, String to) {
        this.data = data;
        this.to = to;
    }

    public Notification() {
    }
}
