package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class OtherDogTest {

    OtherDog dog1;
    String dog1InfoPart1 =  "My name is " + "Willow" + " and I am " + "3" + " year(s) old!";
    String dog1InfoPart2 = "\nMy Owner is called: " + "Larry" + " I've been dogsat from " + "October 5th, 2019";
    String dog1Info = dog1InfoPart1+dog1InfoPart2;

    @BeforeEach
    public void setup() {
        dog1 = new OtherDog("Willow",3, "Larry", "October 5th, 2019");

    }

    @Test
    public void testInfo() {
        assertEquals(dog1Info,dog1.info());
    }

    @Test
    public void testgetOwner() {
        assertEquals("Larry",dog1.getOwner());
    }

    @Test
    public void testGetDogSittingStart() {
        assertEquals("October 5th, 2019",dog1.getDogSittingStart());
    }

    @Test
    public void testGetAndSetDogSittingEnd() {
        String endDate = "December 5";
        assertNotEquals(endDate,dog1.getDogSittingEnd());
        dog1.setDogSittingEnd(endDate);
        assertEquals(endDate,dog1.getDogSittingEnd());
    }


}
