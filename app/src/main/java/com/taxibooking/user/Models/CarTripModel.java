package com.taxibooking.user.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class CarTripModel {


    @Expose
    @SerializedName("vehicle")
    private Vehicle vehicle;
    @Expose
    @SerializedName("to_city")
    private To_city to_city;
    @Expose
    @SerializedName("from_city")
    private From_city from_city;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("back_seat")
    private int back_seat;
    @Expose
    @SerializedName("front_seat")
    private int front_seat;
    @Expose
    @SerializedName("vehicle_id")
    private int vehicle_id;
    @Expose
    @SerializedName("back_seat_rate")
    private String back_seat_rate;
    @Expose
    @SerializedName("front_seat_rate")
    private String front_seat_rate;
    @Expose
    @SerializedName("vendor")
    private String vendor;
    @Expose
    @SerializedName("to_city_id")
    private int to_city_id;
    @Expose
    @SerializedName("from_city_id")
    private int from_city_id;
    @Expose
    @SerializedName("id")
    private int id;

    public String getVendor() {
        return vendor;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public To_city getTo_city() {
        return to_city;
    }

    public From_city getFrom_city() {
        return from_city;
    }

    public int getStatus() {
        return status;
    }

    public int getBack_seat() {
        return back_seat;
    }

    public int getFront_seat() {
        return front_seat;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public String getBack_seat_rate() {
        return back_seat_rate;
    }

    public String getFront_seat_rate() {
        return front_seat_rate;
    }

    public int getTo_city_id() {
        return to_city_id;
    }

    public int getFrom_city_id() {
        return from_city_id;
    }

    public int getId() {
        return id;
    }

    public static class Vehicle {
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("owner_type")
        private String owner_type;
        @Expose
        @SerializedName("vehicle_type")
        private String vehicle_type;
        @Expose
        @SerializedName("number")
        private String number;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("vendor_id")
        private int vendor_id;
        @Expose
        @SerializedName("id")
        private int id;

        public int getStatus() {
            return status;
        }

        public String getOwner_type() {
            return owner_type;
        }

        public String getVehicle_type() {
            return vehicle_type;
        }

        public String getNumber() {
            return number;
        }

        public String getName() {
            return name;
        }

        public int getVendor_id() {
            return vendor_id;
        }

        public int getId() {
            return id;
        }
    }

    public static class To_city {
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

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

    public static class From_city {
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

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
}
