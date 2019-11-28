package entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    Location l1;
    Location l2;
    Location l3;

    @BeforeEach
    public void setup() {
        l1 = new Location("UBC Path A", 5, "Vancouver");
        l2 = new Location("Stanley Park", 10, "Vancouver");
        l2.visit();
        l3 = new Location("UBC Path B", 7, "Vancouver");
    }

    @Test
    public void testGetName() {
        assertEquals("UBC Path A", l1.getName());
        assertNotEquals("UBC Path B", l2.getName());
    }

    @Test
    public void testGetDistance() {
        assertEquals(5,l1.getDistance());
        assertNotEquals(10,l1.getDistance());
    }

    @Test
    public void testGetTimesVisited() {
        assertEquals(1, l1.getTimesVisited());
        assertEquals(2,l2.getTimesVisited());
    }

    @Test
    public void testVisit() {
        assertEquals(1,l1.getTimesVisited());
        l1.visit();
        assertEquals(2,l1.getTimesVisited());
    }

    @Test
    public void testEquals() {
        Location l4 = new Location("UBC Path A", 5, "Vancouver");
        assertTrue(l1.equals(l4));
        assertFalse(l1.equals(l3));
    }

    @Test
    public void testNotEquals() {

    }

    @Test
    public void testInfo() {
        assertEquals(l1.getName() + " of distance " + l1.getDistance() + " has been visited " + l1.getTimesVisited() + " times.",l1.info());
    }

}



