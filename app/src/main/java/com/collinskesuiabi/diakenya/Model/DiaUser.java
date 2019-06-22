package com.collinskesuiabi.diakenya.Model;

public class DiaUser {


    private String Name;
    private String Phone;
    private String Person;
    private String Id;
    private String Image;





    public DiaUser() {

    }

    public DiaUser(String name, String phone, String person, String id, String image) {
        Name = name;
        Phone = phone;
        Person = person;
        Id = id;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}