package ui;

import util.Tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InteractionListener implements ActionListener {
    private int menuState;
    private VisualSystem vis;


    InteractionListener(VisualSystem vis) {
        this.vis = vis;
        menuState = 10; //main menu default state
    }



    //EFFECTS: refers actionevent e's actioncommand to the parseButton button
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        //TODO put method here to switch parseButton types based on menu
        menuState = parseButton(e.getActionCommand());
    }

    private int parseButton(String e) {
        switch (e) {
            case "New Dog":
                return vis.openNewDogMenu();
            case "View Walk Data":
                return vis.openWalkData();
            case "New Walk":
                return vis.openNewWalkMenu();
            case "Check Location Weather":
                return vis.weatherMenu();
            case "New Location":
                return vis.newLocation();
            case ("Clear User Data"):
                return vis.clearData();
            case "Quit":
                return vis.quit();
            default:
        }
        return 10; //return to main menu
    }


}
