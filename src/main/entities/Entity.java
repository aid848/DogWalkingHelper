package entities;

import interfaces.Loadable;
import interfaces.Saveable;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    //EFFECTS: returns a string that identifies the entity to the caller
    public abstract String info();

    public abstract String getName();

}
