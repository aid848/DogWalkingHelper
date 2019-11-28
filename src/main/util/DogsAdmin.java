package util;

import entities.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DogsAdmin {
    ArrayList<Dog> dogs;
    public Tools tools;

    DogsAdmin(Tools tools) {
        this.tools = tools;
        dogs = new ArrayList<Dog>();
    }


    //MODIFIES: this
    //EFFECTS: creates a new dog and adds to collection if dog is not already in collection,
    // if already in collection then alreadyinsystemexception is thrown.
    public boolean newDog(String name, int age) throws AlreadyInSystemException {
        dogInSystem(new Dog(name,age));
        System.out.println("Creating new dog with name " + name + " and age " + age);
        insertDog(new Dog(name,age));
        return true;
    }


    //MODIFIES: this
    //EFFECTS: creates a new other dog and adds to collection if other dog not already in system.
    // If already in system alreadyinsystemexception is thrown.
    public boolean newOtherDog(String name, int age, String owner, String startDate) throws AlreadyInSystemException {
        dogInSystem(new Dog(name,age));
        insertDog(new OtherDog(name,age,owner,startDate));
        System.out.println("Creating new dog with name " + name + " and age " + age
                + " owner " + owner + " startDate " + startDate);
        return true;
    }


    boolean dogInSystem(Dog d) throws AlreadyInSystemException {
        if (dogs.contains(d)) {
            System.out.println("Dog is already in system");
            throw new AlreadyInSystemException();
        }
        return false;
    }

    //EFFECTS: outputs formatted string of places a particular dog matching name has walked and how many times.
    // If not walked anywhere output is changed to reflect that.
    public String viewWalk(String name) {
        String output = "";
        try {
            Dog doggo = findDog(new Dog(name,0));
            for (Location l: doggo.getPlacesWalked()) {
                output += "-" + l.info() + tools.globalStats(l) + "\n";
            }
            if (doggo.getPlacesWalked().size() == 0) {
                output = "Not walked anywhere yet.";
            }
        } catch (UnknownDogException e) {
            output = "Dog not found";
        }

        return output;
    }

    Dog findDog(Dog dog) throws UnknownDogException {
        try {
            return dogs.stream().filter(d -> d.equals(dog)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UnknownDogException();
        }
    }

    void deleteData() {
        dogs.clear();
    }

    boolean containsDog(Dog dog) {
        return dogs.contains(dog);
    }

    //REQUIRES: Dog must not already be in list
    void insertDog(Dog d) {
        dogs.add(d);
    }

    void save() throws Exception {
        Tools.save("dogs",dogs);
    }

    void load() throws Exception {
        dogs = Tools.<ArrayList<Dog>>load("dogs");
    }


    //EFFECTS: returns array of dog names that have been recorded
    public String[] enumerateDogs() {
        dogs.trimToSize();
        String[] names = new String[dogs.size()];
        int i = 0;
        for (Dog d: dogs) {
            names[i] = d.getName();
            i++;
        }
        return names;
    }
}
