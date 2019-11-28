package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewWalkListener implements ActionListener {

    private VisualSystem vis;

    NewWalkListener(VisualSystem vis) {
        this.vis = vis;
    }

    //MODIFIES: Visualsystem
    //EFFECTS: Performs various actions in the visualsystem based on action command passed in method signature
    @Override
    public void actionPerformed(ActionEvent e) {
        String output = e.getActionCommand();
        if (output.equals("Back")) {
            vis.returnToMain();
        } else if (output.equals("Walk")) {
            try {
                vis.enterWalk();
                vis.returnToMain();
                vis.errorPopup("Walk Success");
            } catch (Exception g) {
                vis.errorPopup("Invalid entry");
            }
        }
    }
}
