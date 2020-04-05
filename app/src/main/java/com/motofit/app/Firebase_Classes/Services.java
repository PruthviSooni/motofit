package com.motofit.app.Firebase_Classes;

public class Services {


    public String date;
    public String time;
    public String odometer;
    public String type_service;
    public String location;
    public String notes;
    public String name;
    public String spare_parts;

    public Services() {
    }


    public Services(String name, String date, String time, String service, String odo_meter, String note, String locate, String spare_parts) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.odometer = odo_meter;
        this.type_service = service;
        this.location = locate;
        this.notes = note;
        this.spare_parts = spare_parts;
    }
}
