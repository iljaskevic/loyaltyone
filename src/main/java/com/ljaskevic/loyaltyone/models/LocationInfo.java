package com.ljaskevic.loyaltyone.models;

public class LocationInfo {

    public final String city;
    public final double lon;
    public final double lat;
    public final double temp;

    public LocationInfo() {
        this.city = "Unknown";
        this.lon = 0;
        this.lat = 0;
        this.temp = 0;
    }

    public LocationInfo(String city, double lon, double lat, double temp) {
        this.city = city;
        this.lon = lon;
        this.lat = lat;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public double getTemp() {
        return temp;
    }
}