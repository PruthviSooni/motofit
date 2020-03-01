package com.test.motofit_temp.test_1.Firebase_Classes;

public class Services {
    public String date;
    public String time;
    public String odometer;
    public String type_service;
  public String location;
    public String notes;

    public Services(){

    }
    public Services(String date,String time,String type_service,String odometer,String notes,String location){
        this.date=date;
        this.time=time;
        this.odometer=odometer;
        this.type_service=type_service;
      this.location=location;
        this.notes=notes;
    }
}
