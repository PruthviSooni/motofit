package com.motofit.app.Firebase_Classes;

public class Breakdown {
    public String User_Name;
    public String Phone_Number;
    public String Model;
    public String Brand;
    public String Dropdown_service;
    public String Location;
    public String Date_and_Time;

    public Breakdown() {
    }

    public Breakdown(String user_Name, String phone_Number, String model, String brand, String dropdown_service, String location, String date_and_Time) {
        User_Name = user_Name;
        Phone_Number = phone_Number;
        Model = model;
        Brand = brand;
        Dropdown_service = dropdown_service;
        Location = location;
        Date_and_Time = date_and_Time;
    }

}
