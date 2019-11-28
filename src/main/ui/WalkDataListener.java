package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WalkDataListener implements ActionListener {

    private JComboBox dog;
    private VisualSystem vis;

    WalkDataListener(Component[] control, JTextPane output, VisualSystem vis) {
        this.dog = (JComboBox) control[2];
        this.vis = vis;
    }

    //MODIFIES: VisualSystem
    //EFFECTS: reacts to actionevent e's actioncommand. if Back go to main menu. If show data then call show output.
    @Override
    public void actionPerformed(ActionEvent e) {
        String output = e.getActionCommand();
        if (output.equals("Back")) {
            vis.returnToMain();
        } else if (output.equals("Show Data")) {
            showOutput();
        }
    }

    private void showOutput() {
        try {
            vis.outputDog(dog.getSelectedItem().toString());
        } catch (NullPointerException e) {
            vis.errorPopup("No dog selected");
        }
    }
}
