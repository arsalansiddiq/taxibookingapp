package com.taxibooking.user.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusTripModel {

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
    @SerializedName("vehicle_id")
    private int vehicle_id;
    @Expose
    @SerializedName("available_seat")
    private String available_seat;
    @Expose
    @SerializedName("rate")
    private String rate;
    @Expose
    @SerializedName("total_seat")
    private String total_seat;
    @Expose
    @SerializedName("bus_time")
    private String bus_time;
    @Expose
    @SerializedName("bus_date")
    private String bus_date;
    @Expose
    @SerializedName("to_city_id")
    private int to_city_id;
    @Expose
    @SerializedName("from_city_id")
    private int from_city_id;
    @Expose
    @SerializedName("id")
    private int id;

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

    public int getVehicle_id() {
        return vehicle_id;
    }

    public String getAvailable_seat() {
        return available_seat;
    }

    public String getRate() {
        return rate;
    }

    public String getTotal_seat() {
        return total_seat;
    }

    public String getBus_time() {
        return bus_time;
    }

    public String getBus_date() {
        return bus_date;
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
