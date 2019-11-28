package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateLocationListener implements ActionListener {

    private VisualSystem vis;
    private JTextField[] userData;

    CreateLocationListener(JTextField[] userData, VisualSystem vis) {
        this.vis = vis;
        this.userData = userData;
    }

    //MODIFIES: visualSystem, LocationAdmin
    //EFFECTS: perform action based on actioncommand. if back go to mainmenu, if submit attempt to create new location.
    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();
        if (input.equals("Back")) {
            vis.returnToMain();
        } else if (input.equals("Submit")) {
            if (inputValid()) {
                vis.createLocation(userData);
            } else {
                tellInputIsIncorrect();
            }
        }
    }

    private void tellInputIsIncorrect() {
        vis.errorPopup("Location input is invalid");
    }


    private boolean inputValid() {
        try {
            String distance = userData[3].getText();
            Double dist = Double.parseDouble(distance);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
