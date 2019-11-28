package util;
import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Instance;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ToolsTest {
    private Dog dOne;
    private Dog dTwo;
    private Location lOne;
    private Location lTwo;
    private Instance userInteraction;
    private Tools toolchain;
   // private final InputStream NORMALIN = System.in;
    public ByteArrayOutputStream output = new ByteArrayOutputStream();
    public ByteArrayInputStream input;

    @BeforeEach
    void setup() {
        dOne = new Dog("Zelda", 5);
        dTwo = new Dog("Daisy", 3);
        lOne = new Location("UBC", 2, "Vancouver");
        lTwo = new Location("White Rock Beach", 4, "White Rock");


        toolchain = new Tools();
        userInteraction = new Instance(toolchain);
        toolchain.clearSaved();
//        System.setOut(new PrintStream(output));
    }

//    @Test
//    public void testSaveAndLoadOneEntity() { // use direct methods
//        Dog test = new Dog("wrong",0);
//        userInteraction.toolchain.clearSaved();
//        userInteraction.toolchain.dogAdmin.insertDog(dOne);
//        userInteraction.toolchain.saveAll();
//        userInteraction.toolchain.loadAll();
//        try {
//            ObjectInputStream testDog1 = new ObjectInputStream(new FileInputStream(new File("data\\test\\testDog1")));
//            ArrayList<Dog> arrList = (ArrayList<Dog>) testDog1.readObject();
//            test = arrList.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//        assertTrue(userInteraction.toolchain.dogAdmin.dogs.size()==1);
//        assertEquals(test,dOne);
//
//    }

    @Test
    void testSaveAndLoadMultipleEntities() {
        Dog test = new Dog("wrong",0);
        Dog test2 = test;
        userInteraction.toolchain.clearSaved();
        userInteraction.toolchain.dogAdmin.insertDog(dOne);
        userInteraction.toolchain.dogAdmin.insertDog(dTwo);
        userInteraction.toolchain.saveAll();
        userInteraction.toolchain.loadAll();
        try {
            ObjectInputStream testDog2 = new ObjectInputStream(new FileInputStream(new File("data\\test\\testDog2")));
            ArrayList<Dog> arrList = (ArrayList<Dog>) testDog2.readObject();
            test = arrList.get(0);
            test2 = arrList.get(1);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(2, userInteraction.toolchain.dogAdmin.dogs.size());
        assertEquals(test,dOne);
        assertEquals(test2,dTwo);

    }

    @Test
    void testContainsDog() {
        toolchain.dogAdmin.insertDog(dOne);
        assertTrue(toolchain.dogAdmin.containsDog(dOne));
    }

    @Test
    void testContainsLoc() {
        toolchain.locationAdmin.insertLocation(lOne);
        assertTrue(toolchain.locationAdmin.containsLoc(lOne));
    }

    @Test
    void testMainMenu() {
        assertEquals(toolchain.menuText, toolchain.mainMenu());
    }

    @Test
    void testFindDog() {
        toolchain.dogAdmin.insertDog(dOne);
        try {
            assertEquals(dOne, toolchain.dogAdmin.findDog(new Dog("Zelda", 0)));
        } catch (UnknownDogException e) {
            fail();
        }
    }

    @Test
    void testDogInSystem() {
        toolchain.dogAdmin.insertDog(dOne);
        try {
            toolchain.dogAdmin.dogInSystem(dOne);
            fail();
        }catch (AlreadyInSystemException e) {
            assertEquals(AlreadyInSystemException.class, e.getClass());
        }
        try {
            assertFalse(toolchain.dogAdmin.dogInSystem(dTwo));
        } catch (AlreadyInSystemException e) {
            fail();
        }
    }


    @Test
    void testFindLocation() {
        toolchain.locationAdmin.insertLocation(lOne);
        try {
            assertEquals(lOne, toolchain.locationAdmin.findLocation(lOne.getName()));
        } catch (UnknownLocationException e) {
            fail();
        }

    }

    @Test
    void testNewDog() {
        try {
            assertTrue(toolchain.dogAdmin.newDog(dOne.getName(), dOne.getAge()));
        } catch (AlreadyInSystemException e) {
            fail();
        }
        try {
            toolchain.dogAdmin.newDog(dOne.getName(),dOne.getAge());
            fail();
        }catch (AlreadyInSystemException e) {
            assertEquals(AlreadyInSystemException.class,e.getClass());
        }

    }

    @Test
    void testNewLocation() {
        assertTrue(toolchain.locationAdmin.newLocation("UBC",5,"Vancouver"));
        assertFalse(toolchain.locationAdmin.newLocation("UBC",5,"Vancouver"));
    }

    @Test
    void testNewAndViewWalks() {
        String expectedWalk = "-UBC of distance 2.0 has been visited 2 times. and has been visited a total of 2 times\n" +
                "-White Rock Beach of distance 4.0 has been visited 1 times. and has been visited a total of 1 times\n";
        //setup
        toolchain.locationAdmin.newLocation(lOne.getName(),(int)lOne.getDistance(),lOne.getCity());
        try {
            toolchain.dogAdmin.newDog(dOne.getName(), dOne.getAge());
        } catch (AlreadyInSystemException e) {
            fail();
        }
        toolchain.locationAdmin.newLocation(lTwo.getName(),(int)lTwo.getDistance(),lTwo.getCity());
        //new walk
        String wOne = "Walked at " + lOne.getName() + "\nNew walk entry complete";
        String wTwo = "Walked at " + lTwo.getName() + "\nNew walk entry complete";
        String failedWalk = "Error, no such dog exists" + "\nNew walk entry complete";
        assertEquals(wOne,toolchain.newWalk(dOne.getName(),lOne.getName()));
        assertEquals(wOne,toolchain.newWalk(dOne.getName(),lOne.getName()));
        assertEquals(wTwo,toolchain.newWalk(dOne.getName(),lTwo.getName()));
        assertEquals(failedWalk,toolchain.newWalk(dTwo.getName(),lOne.getName()));
        //expected viewWalk
        assertEquals(expectedWalk, toolchain.dogAdmin.viewWalk(dOne.getName()));
    }

    @Test
    void newOtherDog() {
        try {
            assertTrue(toolchain.dogAdmin.newOtherDog("Willow", 3, "Larry", "Jan 1"));
        } catch (AlreadyInSystemException e) {
            fail();
        }
        try {
            toolchain.dogAdmin.newOtherDog("Willow", 3, "Larry", "Jan 1");
        } catch (AlreadyInSystemException e) {
            assertEquals(AlreadyInSystemException.class,e.getClass());
        }
    }


}
