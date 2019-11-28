package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherListener implements ActionListener {

    private JTextArea temp;
    private JTextArea info;
    private VisualSystem vs;
    private JComboBox loc;

    WeatherListener(JTextArea temp, JTextArea info,VisualSystem vs,JComboBox loc) {
        this.temp = temp;
        this.info = info;
        this.vs = vs;
        this.loc = loc;
        temp.setEditable(false);
        info.setEditable(false);
        temp.setVisible(false);
        info.setVisible(false);
    }



    //REQUIRES: temp != null, info!= null
    //MODIFIES:  this and the caller's temp, and info object references
    //EFFECTS: checks weather if action command is Check and goes to main menu if actioncommand is Back.
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand().toString();
        if (command.equals("Check")) {
            vs.fetchWeather(temp, info, (String)loc.getSelectedItem());
            temp.setVisible(true);
            info.setVisible(true);
        } else if (command.equals("Back")) {
            vs.returnToMain();
        }
    }
}
