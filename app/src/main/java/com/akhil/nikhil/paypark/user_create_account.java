package com.akhil.nikhil.paypark;

/**
 * Created by Nikhil on 4/27/2018.
 */

public class user_create_account {

    public String name,address , mobile , lat , lng;

    user_create_account()
    {

    }

    public user_create_account(String name, String address,String mobile , String lat , String lng)

    {
        this.name = name;
        this.address = address;
        this.mobile= mobile;

        this.lat = lat;
        this.lng = lng;
    }
}
