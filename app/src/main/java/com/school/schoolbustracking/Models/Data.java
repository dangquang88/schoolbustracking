package com.school.schoolbustracking.Models;

public class Data {
    private  String room;
    private  String token;
    private  String type;
    private  String photo;
    private  String name;
    private  String uid;

    public Data(String room, String token,String type,String photo,String name,String uid) {
        this.room = room;
        this.token = token;
        this.type=type;
        this.photo=photo;
        this.name=name;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getType() {
        return type;
    }

    public String getRoom() {
        return room;
    }

    public String getToken() {
        return token;
    }
}
