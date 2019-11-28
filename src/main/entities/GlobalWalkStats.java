package entities;


import util.Tools;

import java.util.*;

public class GlobalWalkStats implements Observer {
    private Tools tools;
    private Map<Location,Integer> places;

    public GlobalWalkStats(Tools tool) {
        places = new HashMap<Location,Integer>();
        this.tools = tool;
    }

    //MODIFIES: this,
    //EFFECTS: if an argument of type Location is passed then the collection places is checked to
    // see if the location is contained in the collection.
    // If yes then the integer with the key value is incremented by 1,
    // if not a key of the location is entered into the collection with a integer value of 1.
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("OBSERVER HAS BEEN NOTIFIED");
        if (!(arg instanceof Location)) {
            return;
        }
        if (places.get(arg) == null) {
            places.put((Location) arg,1);
        } else {
            Location l = (Location)arg;
            places.put(l,places.get(l) + 1);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads a Map object from a the load method in tools and assigns the object reference to places
    public void load() throws Exception {
        places = Tools.load("GlobalStats");
    }


    //EFFECTS: saves the places object to a file
    public void save() throws Exception {
        Tools.save("GlobalStats",places);
    }


    //EFFECTS:returns the value of the places with the key Location l
    public int getStat(Location l) {
        return places.get(l);
    }


    //MODIFIES: this
    //EFFECTS: assigns a new object reference of type HashMap<Location,Integer> to the places variable
    public void deleteData() {
        places = new HashMap<Location,Integer>();

    }


}
