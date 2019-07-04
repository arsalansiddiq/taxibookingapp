package com.taxibooking.user.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitiesModel {

    @Expose
    @SerializedName("deleted_at")
    private String deleted_at;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("id")
    private int id;

    public String getDeleted_at() {
        return deleted_at;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
