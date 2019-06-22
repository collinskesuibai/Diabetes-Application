package com.collinskesuiabi.diakenya.Model;

public class DiaData {


    private String Name;
    private String Phone;
    private String Value;





    public DiaData() {

    }

    public DiaData(String name, String phone, String value) {
        Name = name;
        Phone = phone;
        Value = value;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
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


}