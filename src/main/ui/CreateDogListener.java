package ui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateDogListener implements ActionListener {

    private JTextField[] userData;
    private JTextField[] extraUserData;
    private VisualSystem vs;
    private boolean otherDog;

    CreateDogListener(JTextField[] userData, JTextField[] extraUserData, VisualSystem vs) {
        this.userData = userData;
        this.extraUserData = extraUserData;
        this.vs = vs;
        otherDog = false;
    }


    //MODIFIES: VisualSystem, dogAdmin
    //EFFECTS: handles recieved action events. If output = back return to mainmenu.
    // if output = Submit then check for valid data and either create new dog or pop up with error window.
    // If output = toggle, then show additional options.
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        String output = e.getActionCommand();
        if (output.equals("Back")) {
            vs.returnToMain();
        } else if (output.equals("Submit")) {
            try {
                vs.newDog(userData, extraUserData, otherDog);
                vs.returnToMain();
            } catch (NumberFormatException b) {
                vs.errorPopup("Invalid entry");
            }
        } else if (output.equals("toggle")) {
            otherDog = !otherDog;
            vs.extraDog.setVisible(otherDog);
        }

    }

}
