package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DogTest {
    Dog zelda;
    Dog daisy;

    @BeforeEach
    public void setup() {
        zelda = new Dog("Zelda", 5);
        daisy = new Dog("Daisy", 3);
    }

    @Test
    public void testGetNameFromDogObject() {
        assertEquals("Zelda", zelda.getName());
    }

    @Test
    public void testGetAgefromDog() {
        assertEquals(5,zelda.getAge());
    }

    @Test
    public void testGetPLacesWalkedNone(){
        assertEquals(0,zelda.getPlacesWalked().size());
    }

    @Test
    public void testGetPLacesWalkedOne(){
        try {
            zelda.walk(new Location("UBC", 5, "Vancouver"));
            ArrayList<Location> one = new ArrayList<Location>(Arrays.asList(new Location[]{new Location("UBC", 5,"Vancouver" )}));
            assertEquals(1, zelda.getPlacesWalked().size());
            assertEquals(one, zelda.getPlacesWalked());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testGetPLacesWalkedMany(){
        try {
            zelda.walk(new Location("UBC", 5, "Vancouver"));
            zelda.walk(new Location("Crescent Beach", 8, "South Surrey"));
            ArrayList<Location> many = new ArrayList<Location>(Arrays.asList(new Location[]{new Location("UBC", 5,"Vancouver" ), new Location("Crescent Beach", 8, "South Surrey")}));
            assertEquals(2, zelda.getPlacesWalked().size());
            assertEquals(many, zelda.getPlacesWalked());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testBeenBeforeAlreadyVisted() {
        Location ubc = new Location("UBC", 5, "Vancouver");
        try {
            zelda.walk(ubc);
        } catch (Exception e) {
            fail();
        }
        assertTrue(zelda.beenBefore(ubc));
        assertTrue(zelda.beenBefore(new Location("UBC",5, "Vancouver")));
    }

    @Test
    public void testBeenBeforeNotVisted() {
        assertFalse(zelda.beenBefore(new Location("UBC", 5, "Vancouver")));
    }

    @Test
    public void testDogInfo() {
        String daisyGreeting = "My name is " + "Daisy" + " and I am " + "3" + " year(s) old!";
        assertEquals(daisyGreeting,daisy.info());
    }

    @Test
    public void testTimesWalkedAlreadyVisted() {
        Location ubc = new Location("UBC", 5, "Vancouver");
        try {
            daisy.walk(ubc);
            daisy.walk(ubc);
        } catch (Exception e) {
            fail();
        }
        assertEquals(2,daisy.timesWalked(ubc));
    }

    @Test
    public void testTimesWalkedNotVisted() {
        Location ubc = new Location("UBC", 5, "Vancouver" );
        assertEquals(0,daisy.timesWalked(ubc));

    }

    @Test
    public void testEqualsSame() {
        assertEquals(zelda,zelda);
    }

    @Test
    public void testEqualsNotSame() {
        assertNotEquals(zelda,daisy);
    }

    @Test
    public void testInfo() {
        assertEquals("My name is " + zelda.getName() + " and I am " + zelda.getAge() + " year(s) old!",zelda.info());
    }


}
