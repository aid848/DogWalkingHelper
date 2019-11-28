package util;

import entities.*;
import interfaces.Loadable;
import interfaces.Saveable;
import javafx.beans.InvalidationListener;
import network.WeatherParse;
import ui.UiPrompts;

import java.io.*;
import java.util.HashMap;
import java.util.Observable;

public class Tools extends Observable implements Saveable, Loadable {
    public DogsAdmin dogAdmin;
    public LocationsAdmin locationAdmin;
    final String menuText;
    private GlobalWalkStats global = new GlobalWalkStats(this);

    /*
    Cohesion assessment results:
    Class is acting like 3 different classes:
    1. a dog manager class
    2. a location manager class
    3. data management and save/loading

    solutions:
    - split dog management method into new class
    - split location management method into new class

    Also save and load had a lot of repeated code so solution:
    - reduce duplication in save/load methods by using parameters to
    save different types of data collections
    (lowers coupling by having one save/load method instead of 2)

     */


    public Tools() {
        dogAdmin = new DogsAdmin(this);
        locationAdmin = new LocationsAdmin();
        super.addObserver(global);
        menuText = "\nWhat would you like to do?\n"
                                + "A. Enter walk\n"
                                + "B. View walk data\n"
                                + "C. New dog\n"
                                + "D. New location\n"
                                + "W. Check location weather data\n"
                                + "R. Clear user data\n"
                                + "Anything else = quit";
    }


    //EFFECTS: prompts user for a location name and returns formatted weather data if location exists.
    // If location doesn't exist a string saying there was an error is returned.
    public String weatherMode() {
        String location = UiPrompts.pollUserLine("Enter a location to check the current weather of that place");
        String output = "";
        try {
            Location l = locationAdmin.findLocation(location);
            output = WeatherParse.getCurrentWeatherOfCity(l.getCity());
        } catch (Exception e) {
            output = "Error finding weather data";
        } finally {
            return output;
        }
    }

    //EFFECTS: prints the menuText to the console and returns the text.
    public String mainMenu() {
        System.out.println(menuText);
        return menuText;
    }


    //MODIFIES: GlobalWalkStats, Location, Dog
    //EFFECTS: Finds dog from name and finds location from location parameter,
    // if found then increment times walked on the location in the dog's class
    // and increment the location's value in the globalwalkstats.
    public String newWalk(String name, String location) {
        String result = "";
        try {
            Dog dog = dogAdmin.findDog(new Dog(name, 0));
            Location place = locationAdmin.findLocation(location);
            dog.walk(place);
            super.setChanged();
            super.notifyObservers(place);
            //global.update(this,place);
            result =  "Walked at " + location;
        } catch (UnknownDogException e) {
            result =  "Error, no such dog exists";
        } catch (UnknownLocationException e) {
            result = "Error, no such place exists";
        } catch (Exception e) {
            result = "Unknown error occurred";
        } finally {
            result += "\nNew walk entry complete";
        }
        return result;
    }


    //MODIFIES: GlobalWalkStats, dogAdmin, locationAdmin
    //EFFECTS: loads all data for program to operate properly by
    // calling particular load methods for the different data managers.
    @Override
    public void loadAll() {
        try {
            global.load();
            dogAdmin.load();
            locationAdmin.load();
            System.out.println("Loading complete");
        } catch (Exception e) {
            System.out.println("No save data");
        }
    }


    //EFFECTS: saves all data for program to operate properly by
    // calling particular save methods for the different data managers.
    @Override
    public void saveAll() {
        try {
            global.save();
            dogAdmin.save();
            locationAdmin.save();
            System.out.println("Save Complete");
        } catch (Exception e) {
            System.out.println("Unable to save");
        }
    }

    //EFFECTS: generic method allows saving object dataStructure with file name filename
    public static void save(String filename, Object dataStructure) throws Exception {
        ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream((new File(filename))));
        saver.writeObject(dataStructure);
        saver.close();
    }

    //EFFECTS: returns object of type T from file with name filename located in project main folder
    public static <T> T load(String filename) throws Exception {
        ObjectInputStream loader = new ObjectInputStream(new FileInputStream(new File(filename)));
        T data = (T)loader.readObject();
        loader.close();
        return data;
    }

    //MODIFIES: GlobalWalkStats, dogAdmin, locationAdmin
    //EFFECTS: clears all data for the different data managers.
    @Override
    public void clearSaved() {
        dogAdmin.deleteData();
        locationAdmin.deleteData();
        global.deleteData();
        saveAll();
        System.out.print(" & Cleared");
    }



    String globalStats(Location l) {
        return " and has been visited a total of " + global.getStat(l) + " times";
    }
}
