package com.akhil.nikhil.paypark;

import java.io.Serializable;

/**
 * Created by Nikhil on 4/26/2018.*/
public class createaccount implements Serializable {

    public String name,address,mobile,parking_capacity , lat , lng , car_charges , bike_charges , available , space_left , sp_email ;

    createaccount()
    {

    }

    public createaccount(String name, String address,String mobile,String parking_capacity , String lat , String lng , String car_charges , String bike_charges , String available , String space_left)

    {
        this.name = name;
        this.address = address;
        this.mobile= mobile;
        this.parking_capacity= parking_capacity;
        this.lat = lat;
        this.lng = lng;
        this.car_charges = car_charges;
        this.bike_charges = bike_charges;
        this.available = available;

        this.space_left = space_left;

    }


    public createaccount(String name, String address,String mobile,String parking_capacity , String lat , String lng , String car_charges , String bike_charges , String available , String space_left , String sp_email)

    {
        this.name = name;
        this.address = address;
        this.mobile= mobile;
        this.parking_capacity= parking_capacity;
        this.lat = lat;
        this.lng = lng;
        this.car_charges = car_charges;
        this.bike_charges = bike_charges;
        this.available = available;

        this.space_left = space_left;

        this.sp_email = sp_email;

    }
}
