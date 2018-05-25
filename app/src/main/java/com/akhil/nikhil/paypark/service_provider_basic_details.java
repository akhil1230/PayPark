package com.akhil.nikhil.paypark;

import java.io.Serializable;

/**
 * Created by Nikhil on 4/26/2018.*/
public class service_provider_basic_details implements Serializable {

    public String name,address,mobile ;

    service_provider_basic_details()
    {

    }

    public service_provider_basic_details(String name, String address, String mobile )

    {
        this.name = name;
        this.address = address;
        this.mobile= mobile;


    }



}
