package util;

import entities.Location;
import entities.UnknownLocationException;
import ui.UiPrompts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class LocationsAdmin {
    private ArrayList<Location> locations;

    LocationsAdmin() {
        locations = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: creates a new location if not already in arraylist locations and notifies the console and returns true.
    // if already in system returns false.
    public boolean newLocation(String name, double distance,String city) {
        if (containsLoc(new Location(name,distance, city))) {
            System.out.println("Location is already in system");
            return false;
        }
        locations.add(new Location(name,distance, city));
        System.out.println("Creating new location called " + name + " of distance " + distance + ", city: " + city);
        return true;
    }

    Location findLocation(String locName) throws UnknownLocationException {
        try {
            return getLocation(new Location(locName,0,null));
        } catch (NoSuchElementException e) {
            throw new UnknownLocationException();
        } catch (NullPointerException e) {
            throw new UnknownLocationException();
        }
    }


    //EFFECTS: looks for location l in collection and returns that location.
    // If not found throws UnknownLocationException.
    public Location getLocation(Location l) throws UnknownLocationException {
        try {
            return locations.stream().filter(b -> b.equals(l)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UnknownLocationException();
        }
    }

    boolean containsLoc(Location loc) {
        try {
            if (getLocation(loc) != null) {
                return true;
            } else {
                return false;
            }
        } catch (UnknownLocationException e) {
            return false;
        }

    }

    void insertLocation(Location l) {
        locations.add(l);
    }

    void load() throws Exception {
        locations = Tools.<ArrayList<Location>>load("locations");
    }

    void save() throws Exception {
        Tools.save("locations",locations);
    }

    void deleteData() {
        locations.clear();
    }


    //EFFECTS: returns an array of location names that have been entered into the system
    public String[] enumerateLocations() {
        Location[] l = locations.toArray(new Location[]{});
        String[] names = new String[l.length];
        int i = 0;
        for (Location e: l) {
            names[i] = e.getName();
            i++;
        }
        return names;
    }
}
