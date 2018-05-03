package com.akhil.nikhil.paypark;

/**
 * Created by Nikhil on 4/30/2018.
 */

public class parking_detail_model {

    public String avial , space , car_charges , bike_charges ;

    public parking_detail_model()
    {

    }

    public parking_detail_model(String avail , String space , String car_charges , String bike_charges)
    {
        this.avial = avail;

        this.space = space ;

        this.car_charges = car_charges ;

        this.bike_charges = bike_charges ;

    }
}
