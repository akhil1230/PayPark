package com.akhil.nikhil.paypark;

/**
 * Created by charanghumman on 09/05/18.
 */

public class BookDataModel {

    public String sp_email , address , car_charges , bike_charges ,  payment_mode , lat , lng , time ;


    public BookDataModel()
    {

    }

    public BookDataModel(String sp_email , String address , String car_charges , String bike_charges , String payment_mode , String lat , String lng )
    {
        this.sp_email = sp_email;
        this.address = address;
        this.car_charges = car_charges;
        this.bike_charges = bike_charges ;

        this.payment_mode = payment_mode ;

        this.lat = lat;
        this.lng = lng;

    }



}
