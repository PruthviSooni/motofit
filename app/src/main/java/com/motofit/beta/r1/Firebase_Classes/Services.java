package com.motofit.beta.r1.Firebase_Classes;

public class Services {
    public String date;
    public String time;
    public String odometer;
    public String type_service;
    public String location;
    public String notes;
    public String name;

    public Services(){

    }
    public Services(String name,String date,String time,String type_service,String odometer,String notes,String location){
        this.name=name;
        this.date=date;
        this.time=time;
        this.odometer=odometer;
        this.type_service=type_service;
        this.location=location;
        this.notes=notes;
    }
}
