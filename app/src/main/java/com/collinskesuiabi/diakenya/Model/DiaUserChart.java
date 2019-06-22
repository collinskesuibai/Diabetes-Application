package com.collinskesuiabi.diakenya.Model;

public class DiaUserChart {


    private String Name;
    private String Phone;
    private String Person;
    private String Id;
    private String Image;


    public DiaUserChart(String name, String phone, String person, String id, String image) {
        Name = name;
        Phone = phone;
        Person = person;
        Id = id;
        Image = image;
    }

    public DiaUserChart() {


    }


    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setImage(String image) {
        Image = image;
    }
}

