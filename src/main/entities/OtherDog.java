package entities;

import java.util.Date;

public class OtherDog extends Dog {
    private String owner;
    private String dogSittingStart;
    private String dogSittingEnd;

    public OtherDog(String name, int age, String owner, String dogSittingStart) {
        super(name,age);
        this.owner = owner;
        this.dogSittingStart = dogSittingStart;
    }

    //EFFECTS: returns a formatted string containing superclass' attributes and the owner and dogSittingStart
    @Override
    public String info() {
        return super.info() + "\nMy Owner is called: " + getOwner() + " I've been dogsat from " + getDogSittingStart();
    }

    String getOwner() {
        return owner;
    }


    String getDogSittingStart() {
        return dogSittingStart;
    }


    String getDogSittingEnd() {
        return dogSittingEnd;
    }

    void setDogSittingEnd(String dogSittingEnd) {
        this.dogSittingEnd = dogSittingEnd;
    }
}
