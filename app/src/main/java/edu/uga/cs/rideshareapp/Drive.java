package edu.uga.cs.rideshareapp;

public class Drive {
    String pickup_location, destination, driver, date, time, id;
    int points;

    boolean accepted, driverConfirm, riderConfirm;

    public Drive() {

    }

    public Drive(String pickup_location, String destination, String driver, String date, String time, String id, int points) {
        this.pickup_location = pickup_location;
        this.destination = destination;
        this.driver = driver;
        this.date = date;
        this.time = time;
        this.points = points;
        this.accepted = false;
        this.driverConfirm = false;
        this.riderConfirm = false;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) { this.pickup_location = pickup_location; }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
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

    public void setPoints(int points) { this.points = points; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public boolean isAccepted() { return accepted; }

    public void setAccepted(boolean accepted) { this.accepted = accepted; }
}
