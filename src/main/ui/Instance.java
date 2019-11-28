package ui;

import util.Tools;

@Deprecated
public class Instance {
    public Tools toolchain;

    public Instance(Tools toolchain) {
        this.toolchain = toolchain;
        toolchain.loadAll();
    }

    //MODIFIES: this,tools
    //EFFECTS: User generated loop code for old terminal style interactivity
    public void startup(boolean running) { //Used for when the user is using the program
        while (running) {
            toolchain.mainMenu();
            String input = UiPrompts.pollUserLine("");
            running = UiPrompts.userAction(toolchain, input.charAt(0));
        }
        toolchain.saveAll();
    }

}
