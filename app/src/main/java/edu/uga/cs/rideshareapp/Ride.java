package edu.uga.cs.rideshareapp;

public class Ride {
    String pickup_location, destination, rider, date, time, id, acceptedBy;
    int points;
    boolean accepted, driverConfirm, riderConfirm;
    public Ride() {

    }

    public Ride(String pickup_location, String destination, String rider, String date, String time, String id, int points) {
        this.pickup_location = pickup_location;
        this.destination = destination;
        this.rider = rider;
        this.date = date;
        this.time = time;
        this.points = points;
        this.accepted = false;
        this.driverConfirm = false;
        this.riderConfirm = false;
        this.acceptedBy = "";
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

    public String getRider() { return rider; }

    public void setRider(String rider) { this.rider = rider; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public boolean isAccepted() { return accepted; }

    public void setAccepted(boolean accepted) { this.accepted = accepted; }

    public String getAcceptedBy() { return acceptedBy; }

    public void setAcceptedBy(String acceptedBy) { this.acceptedBy = acceptedBy; }

    public boolean isDriverConfirm() { return driverConfirm; }

    public void setDriverConfirm(boolean driverConfirm) { this.driverConfirm = driverConfirm; }

    public boolean isRiderConfirm() { return riderConfirm; }

    public void setRiderConfirm(boolean riderConfirm) { this.riderConfirm = riderConfirm; }
}
