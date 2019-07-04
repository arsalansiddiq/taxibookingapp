package com.taxibooking.user.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class BusTimeSlotModel {


    @Expose
    @SerializedName("bus_time")
    private String bus_time;

    public String getBus_time() {
        return bus_time;
    }
}
