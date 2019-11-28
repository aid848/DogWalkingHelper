package util;
import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Instance;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LocationAdminTest {

    private Location lOne;
    private Location lTwo;
    private LocationsAdmin admin;

    @BeforeEach
    void setup() {
        lOne = new Location("UBC", 2, "Vancouver");
        lTwo = new Location("White Rock Beach", 4, "White Rock");
        admin = new LocationsAdmin();
    }

    @Test
    public void enumerateLocationsTest() {
        String expected = Arrays.toString(new String[]{"UBC", "White Rock Beach"});
        admin.newLocation(lOne.getName(),lOne.getDistance(),lOne.info());
        admin.newLocation(lTwo.getName(),lTwo.getDistance(),lTwo.info());
        String output = Arrays.toString(admin.enumerateLocations());
        assertEquals(expected,output);

    }

}
