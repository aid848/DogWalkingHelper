package ui;

import util.*;

public class Main {

    public static void main(String[] args) {
        Tools toolchain = new Tools();
        //text based interaction in commented out code
        //Instance userInteraction = new Instance(toolchain);
        //userInteraction.startup(true);

        VisualSystem window = new VisualSystem(toolchain);
        window.openMainMenu();

    }







}
