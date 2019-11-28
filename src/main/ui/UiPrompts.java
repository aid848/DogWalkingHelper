package ui;

import entities.AlreadyInSystemException;
import util.LocationsAdmin;
import util.Tools;

import java.util.Scanner;

public class UiPrompts {


    //EFFECTS: Prints message to console and then takes the next line of user input and returns it.
    public static String pollUserLine(String message) {
        System.out.println(message);
        Scanner in = new Scanner(System.in);
        String response = in.nextLine();
        return response;
    }

    private static int pollUserNum(String message) {
        System.out.println(message);
        Scanner in = new Scanner(System.in);
        int response = in.nextInt();
        return response;
    }

    static boolean userAction(Tools tools, char choice) {
        try {
            if (choice == 'A') {
                newWalk(tools);
            } else if (choice == 'B') {
                System.out.println(viewWalk(tools));
            } else if (choice == 'C' | choice == 'D') {
                newData(tools,choice);
            } else if (choice == 'W') {
                System.out.println(tools.weatherMode());
            } else if (choice == 'R') {
                tools.clearSaved();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void newData(Tools tools, char choice) throws Exception {
        if (choice == 'C') {
            newDog(tools);
        } else {
            newLocation(tools.locationAdmin);
        }
    }


    private static boolean newDog(Tools tools)  {
        String name = pollUserLine("What is the dog's name?");
        int age = pollUserNum("What is the dog's age");
        String owned = pollUserLine("Is this your dog? (y/n)");
        try {
            if (owned.equals("n")) {
                String owner = pollUserLine("Who is the owner?");
                String startDate = pollUserLine("What is the start date of taking care of the dog?");
                return tools.dogAdmin.newOtherDog(name, age, owner, startDate);
            } else {
                return tools.dogAdmin.newDog(name, age);
            }
        } catch (AlreadyInSystemException e) {
            return false;
        }
    }

    private static String viewWalk(Tools tools) {
        String name = pollUserLine("Enter dog name");
        return tools.dogAdmin.viewWalk(name);
    }

    private static void newWalk(Tools tools) {

        String name = pollUserLine("Enter dog name");
        String location = pollUserLine("Enter Location name");
        String result = tools.newWalk(name,location);
        System.out.println(result);

    }

    private static boolean newLocation(LocationsAdmin locationsAdmin) {
        String name = pollUserLine("Enter Location Name");
        int distance = pollUserNum("Enter Distance");
        String city = pollUserLine("Enter City");
        return locationsAdmin.newLocation(name,distance,city);

    }
}
