package util;

import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Instance;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DogsAdminTest {
    private Dog dOne;
    private Dog dTwo;
    private DogsAdmin admin;

    @BeforeEach
    void setup() {
        dOne = new Dog("Zelda", 5);
        dTwo = new Dog("Daisy", 3);
        admin = new DogsAdmin(new Tools());
    }

    @Test
    public void enumerateDogsTest() {
        String expected = Arrays.toString(new String[]{"Zelda" , "Daisy"});
        try {
            admin.newDog(dOne.getName(),dOne.getAge());
            admin.newDog(dTwo.getName(),dTwo.getAge());
        } catch (AlreadyInSystemException e) {
            fail();
        }
        String output = Arrays.toString(admin.enumerateDogs());
        assertEquals(expected,output);

    }

}
