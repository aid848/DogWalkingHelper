package entities;

import java.io.Serializable;

public class Location extends Entity implements Serializable {
    private String name;
    private double distance;
    private String city;
    private int timesVisited;
    Dog visitor;

    public String getCity() {
        return city;
    }

    //REQUIRES: distance to be a positive number, name must be unique from others previously created
    //MODIFIES: this
    //EFFECTS: sets name and distance from parameters and initializes timesVisited to 0
    public Location(String name, double distance, String city) {
        this.name = name;
        this.distance = distance;
        this.city = city;
        timesVisited = 1;
    }

    //EFFECTS: returns a formatted string containing the name, distance and timesVisited class attributes
    @Override
    public String info() {
        return getName() + " of distance " + getDistance() + " has been visited " + getTimesVisited() + " times.";
    }

    //EFFECTS: returns name as a String
    public String getName() {
        return name;
    }

    //EFFECTS: returns distance as a double
    public double getDistance() {
        return distance;
    }

    //EFFECTS: returns timesVisited as a double
    int getTimesVisited() {
        return timesVisited;
    }

    //MODIFIES: this
    //EFFECTS: returns name as a String
    void visit() {
        timesVisited++;
    }

    //EFFECTS: Returns true if Locations are the same (same name) and false otherwise
    @Override
    public boolean equals(Object l2) {
        if (l2.getClass() == Location.class) {
            Location b = (Location) l2;
            return this.name.equals(b.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31;
    }
}
