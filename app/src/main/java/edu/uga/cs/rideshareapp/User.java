package edu.uga.cs.rideshareapp;

public class User {
    String id, type, first_name, last_name;
    int points;

    public User() {

    }

    public User(String id, String type, String first_name, String last_name) {
        this.id = id;
        this.points = 0;
        this.type = type;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
