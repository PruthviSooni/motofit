package com.motofit.beta.r1.Firebase_Classes;

public class Breakdown {
    public String User_Name, Phone_Number, Model, Brand, Dropdown_service, Location;

    public Breakdown() {
    }

    public Breakdown(String user_Name, String phone_Number, String model, String brand, String dropdown_service, String location) {
        User_Name = user_Name;
        Phone_Number = phone_Number;
        Model = model;
        Brand = brand;
        Dropdown_service = dropdown_service;
        Location = location;
    }
}
