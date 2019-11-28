package entities;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;


public class Dog extends Entity implements Serializable {
    private String name;
    private int age;
    private ArrayList<Location> placesWalked;

    //REQUIRES: a non negative number for age, unique name when stored in a collection
    //MODIFIES: this
    //EFFECTS: sets name and age for new dog instance, creates empty Arraylist for location called placesWalked
    public Dog(String name,int age) {
        this.name = name;
        this.age = age;
        placesWalked = new ArrayList<Location>();
    }

    //EFFECTS: returns name as a String
    public String getName() {
        return name;
    }

    //EFFECTS: returns age as a int
    public int getAge() {
        return age;
    }

    //EFFECTS: returns placesWalked as a ArrayList<Location>
    public ArrayList<Location> getPlacesWalked() {
        return placesWalked;
    }

    //MODIFIES: this, Location
    //EFFECTS: Search list for location and if found increment timesVisited, if not found add to list
    public void walk(Location loc) throws UnknownLocationException {
        try {
            if (beenBefore(loc)) {
                placesWalked.stream().filter(location -> location.equals(loc)).findFirst().get().visit();
            } else {
                loc.visitor = this;
                Location l = new Location(loc.getName(),loc.getDistance(),loc.getCity());
                placesWalked.add(l);
            }
        } catch (NullPointerException e) {
            throw new UnknownLocationException();
        }
    }

    //EFFECTS: Returns a string with the dog's info in a sentence (My name is (name) and I am (age) year(s) old!)
    public String info() {
        return ("My name is " + name + " and I am " + age + " year(s) old!");
    }

    //EFFECTS: Search list for location element and return visted # if element is found, 0 if not found
    protected int timesWalked(Location loc) {
        Stream<Location> place = placesWalked.stream();
        try {
            return place.filter(location -> location.equals(loc)).findFirst().get().getTimesVisited();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    //EFFECTS: Returns true if Locations are the same (same name) and false otherwise
    @Override
    public boolean equals(Object l2) {
        if (l2.getClass() == Dog.class || l2.getClass() == OtherDog.class) {
            Dog b = (Dog) l2;
            return (this.name.equals(b.getName()));
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode() * 31;
    }

    //EFFECTS: returns true or false based on if loc is in placeswalked list
    protected boolean beenBefore(Location loc) {
        return placesWalked.contains(loc);
    }



}
