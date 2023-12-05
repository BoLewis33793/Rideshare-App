package edu.uga.cs.rideshareapp;

public class Ride {
    String pickup_location, destination, rider, date, time, id;
    int points;

    public Ride() {

    }

    public Ride(String pickup_location, String destination, String rider, String date, String time, String id, int points) {
        this.pickup_location = pickup_location;
        this.destination = destination;
        this.rider = rider;
        this.date = date;
        this.time = time;
        this.points = points;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDriver() {
        return rider;
    }

    public void setDriver(String driver) {
        this.rider = driver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
