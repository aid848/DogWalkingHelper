package ui;

import entities.*;
import network.WeatherParse;
import util.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class VisualSystem {
    private JFrame mainMenu;
    private JFrame currentlyOpen;
    private GridLayout outerMenuGrid = new GridLayout(3,0);
    private JTextArea textOutputArea;
    private JTextField input;
    private InteractionListener listen;

    private List<JPanel> views;

    private JComboBox<JTextArea> weatherLocationList;

    Component extraDog;

    //view walk components
    JTextPane walkOutput;

    //new walk components
    JComboBox nwDogs;
    JComboBox nwLocations;

    private Tools tools;


    VisualSystem(Tools tools) {
        this.tools = tools;
        tools.loadAll();
        listen = new InteractionListener(this);

    }

    void openMainMenu() { //to be called when main menu appears to user
        mainMenuSetup();
        mainMenu.setVisible(true);
        currentlyOpen = mainMenu;
    }

    private void mainMenuSetup() {
        mainMenu = new JFrame("Dog Walking App");
        mainMenu.setBackground(new Color(255,255,255));
        mainMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainMenu.setLayout(outerMenuGrid);
        mainMenu.setMinimumSize(new Dimension(400,300));
        List<JButton[]> buttonList = new ArrayList<JButton[]>(Arrays.asList(loadButtonsMainMenu()));
        JPanel[] outerMenu = setupOuterMenu(buttonList);
        for (JPanel p : outerMenu) {
            mainMenu.add(p);
        }
        mainMenu.getComponent(0).setMinimumSize(new Dimension(200,150));
        textOutputArea.setText("Select an action");
        textOutputArea.setEditable(false);
        textOutputArea.setMinimumSize(new Dimension(200,150));
    }

    private JButton[][] loadButtonsMainMenu() {
        //main menu buttons here
        JButton enterWalk = new JButton("New Walk");
        JButton viewWalkData = new JButton("View Walk Data");
        JButton newDog = new JButton("New Dog");
        JButton newLocation = new JButton("New Location");
        JButton checkWeather = new JButton("Check Location Weather");
        JButton clearData = new JButton("Clear User Data");
        JButton quit = new JButton("Save & Quit");
        quit.setActionCommand("Quit");
        JButton[] b1 = {enterWalk, viewWalkData, checkWeather, newDog, newLocation};
        JButton[] b2 = {clearData, quit};
        return new JButton[][]{b1,b2};
    }

    private JPanel[] setupOuterMenu(List<JButton[]> buttons) {
        ArrayList<JPanel> out = new ArrayList<JPanel>();
        textOutputArea = new JTextArea();
        JPanel textPlace = new JPanel();
        textPlace.add(textOutputArea);
        out.add(textPlace);
        for (JButton[] j: buttons) {
            JPanel splitButton = new JPanel();
            //splitButton.setPreferredSize(new Dimension(400,300));
            for (JButton g: j) {
                g.addActionListener(listen);
                splitButton.add(g);
            }
            out.add(splitButton);
        }
        return out.toArray(new JPanel[0]);
    }

    int openNewWalkMenu() {
        currentlyOpen.dispose();
        JFrame walkMenu = newWalkMenuSetup();
        NewWalkListener newWalkListener = new NewWalkListener(this);
        JButton newWalkBack = new JButton("Back");
        newWalkBack.addActionListener(newWalkListener);
        JButton newWalkWalk = new JButton("Walk");
        newWalkWalk.addActionListener(newWalkListener);
        nwDogs = fetchDogs();
        nwLocations = fetchLocations();
        walkMenu.add(newWalkBack);
        walkMenu.add(nwDogs);
        walkMenu.add(nwLocations);
        walkMenu.add(newWalkWalk);
        walkMenu.setVisible(true);
        return 0;
    }

    private JFrame newWalkMenuSetup() {
        JFrame walkMenu = new JFrame("Walk Menu");
        walkMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        walkMenu.setLayout(new GridLayout(1,4));
        walkMenu.setMinimumSize(new Dimension(400,100));
        currentlyOpen = walkMenu;
        return walkMenu;
    }

    int openNewDogMenu() {
        currentlyOpen.dispose();
        JFrame newDogWindow = setupNewDogWindow();
        newDogWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newDogWindow.setVisible(true);
        currentlyOpen = newDogWindow;
        return 1;
    }

    private JFrame setupNewDogWindow() {
        JFrame window = new JFrame("New Dog Data");
        window.setMinimumSize(new Dimension(600,150));
        window.setLayout(new GridLayout(2,2));
        JButton backDog = new JButton("Back");
        JButton submitDog = new JButton("Submit");
        JTextField[] enteredData = newDogDataSetup();
        JTextField[] additionalData = newOtherDogDataSetUp();
        CreateDogListener dogListen = new CreateDogListener(enteredData,additionalData,this);
        JPanel otherDogPanel = setupOtherDogPanel(dogListen);
        JPanel controls = dogControls(backDog,submitDog,dogListen);
        window.add(controls);
        window.add(otherDogPanel);
        window.add(dogDataFormSetup(enteredData,true));
        extraDog = dogDataFormSetup(additionalData,false);
        window.add(extraDog);
        return window;
    }

    private JPanel setupOtherDogPanel(ActionListener a) {
        JTextArea dogSat = new JTextArea("Dog Sat?");
        JCheckBox otherDog = new JCheckBox();
        otherDog.setActionCommand("toggle");
        otherDog.addActionListener(a);
        JPanel output = new JPanel();
        output.add(dogSat);
        output.add(otherDog);
        return output;


    }

    private Component dogDataFormSetup(JTextField[] enteredData,boolean show) {
        JPanel data = new JPanel();
        data.setLayout(new GridLayout(2,2));
        for (JTextField j: enteredData) {
            data.add(j);
        }
        data.setVisible(show);
        return data;
    }

    private JPanel dogControls(JButton backDog, JButton submitDog,ActionListener a) {
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2,2));
        backDog.addActionListener(a);
        submitDog.addActionListener(a);
        controls.add(backDog);
        controls.add(submitDog);
        return controls;
    }

    private JTextField[] newOtherDogDataSetUp() {
        JTextField ownerHint = new JTextField("Owner Name:");
        ownerHint.setEditable(false);
        JTextField owner = new JTextField();
        JTextField dogSittingStartHint = new JTextField("Looking after since:");
        dogSittingStartHint.setEditable(false);
        JTextField dogSittingStart = new JTextField();
        return new JTextField[]{ownerHint,owner,dogSittingStartHint,dogSittingStart};
    }

    private JTextField[] newDogDataSetup() {
        JTextField nameHint = new JTextField("Name:");
        nameHint.setEditable(false);
        JTextField name = new JTextField();
        JTextField ageHint = new JTextField("Age:");
        ageHint.setEditable(false);
        JTextField age = new JTextField();

        return new JTextField[]{nameHint,name,ageHint,age};
    }

    int newLocation() {
        currentlyOpen.dispose();
        JFrame newLocWindow = setupNewLocationWindow();
        newLocWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newLocWindow.setVisible(true);
        currentlyOpen = newLocWindow;
        return 2;
    }

    private JFrame setupNewLocationWindow() {
        JFrame window = new JFrame("New Location maker");
        window.setMinimumSize(new Dimension(400,150));
        window.setLayout(new GridLayout(0,2));
        JButton backLoc = new JButton("Back");
        JButton submitLoc = new JButton("Submit");
        JTextField[] enteredData = newLocationDataSetup();
        CreateLocationListener locListen = new CreateLocationListener(enteredData,this);
        backLoc.addActionListener(locListen);
        submitLoc.addActionListener(locListen);
        JPanel data = locationDataFormSetup(enteredData);
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2,1));
        controls.add(backLoc);
        controls.add(submitLoc);
        window.add(controls);
        window.add(data);
        return window;
    }

    private JPanel locationDataFormSetup(JTextField[] enteredData) {
        JPanel data = new JPanel();
        data.setLayout(new GridLayout(3,2));
        for (JTextField j: enteredData) {
            data.add(j);
        }
        return data;
    }

    private JTextField[] newLocationDataSetup() {
        JTextField name = new JTextField();
        JTextField nameHint = new JTextField("Name:");
        nameHint.setEditable(false);
        JTextField distance = new JTextField();
        JTextField distanceHint = new JTextField("Distance:");
        distanceHint.setEditable(false);
        JTextField city = new JTextField();
        JTextField cityHint = new JTextField("City:");
        cityHint.setEditable(false);
        return new JTextField[]{nameHint,name,distanceHint,distance,cityHint,city};

    }

    int clearData() {
        errorPopup("Data Cleared!");
        tools.clearSaved();

        return 3;
    }

    void errorPopup(String msg) {
        JFrame message = new JFrame();
        message.setMinimumSize(new Dimension(150,100));
        message.setLayout(new GridLayout(2,0));
        JTextArea clearedMsg = new JTextArea();
        clearedMsg.setText(msg);
        clearedMsg.setEditable(false);
        JButton goBack = new JButton("Close");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.dispose();
            }
        });
        message.add(clearedMsg);
        message.add(goBack);
        message.setAlwaysOnTop(true);
        message.setVisible(true);
    }

    int weatherMenu() {
        closeCurrentMenu();
        JFrame weather = setupWeatherMenu();
        weather.setVisible(true);
        currentlyOpen = weather;
        return 4;
    }

    private void closeCurrentMenu() {
        currentlyOpen.dispose();
    }

    private JFrame setupWeatherMenu() {
        JTextArea temperature = new JTextArea();
        JTextArea conditions = new JTextArea();
        JButton back = new JButton("Back");
        JFrame weather = weatherMenuSettings();
        //weather menu elements
        JButton checkW = new JButton("Check");
        JComboBox locationArea = fetchLocations();
        WeatherListener weatherListener = new WeatherListener(temperature, conditions, this, locationArea);
        back.addActionListener(weatherListener);
        locationArea.addActionListener(weatherListener);
        weather.add(locationArea);
        checkW.addActionListener(weatherListener);
        weather.add(back);
        weather.add(checkW);
        JPanel outW = new JPanel();
        outW.add(conditions);
        outW.add(temperature);
        weather.add(outW);
        return weather;
    }

    private JFrame weatherMenuSettings() {
        JFrame weather = new JFrame("Weather menu");
        weather.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        weather.setMinimumSize(new Dimension(300,100));
        weather.setLayout(new FlowLayout());
        return weather;
    }

    private JComboBox fetchLocations() {
        String[] locs = tools.locationAdmin.enumerateLocations();
        JComboBox locations = new JComboBox(locs);
        return locations;
    }

    private JComboBox fetchDogs() {
        String[] dogs = tools.dogAdmin.enumerateDogs();
        JComboBox dogsBox = new JComboBox(dogs);
        return  dogsBox;
    }

    int openWalkData() {
        currentlyOpen.dispose();
        JFrame walkDataMenu = new JFrame("View Walk Data");
        walkDataMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        walkDataMenu.setMinimumSize(new Dimension(300,300));
        walkDataMenu.setLayout(new GridLayout(2,1));
        Component[] control = walkDataControlsSetup();
        walkOutput = new JTextPane();
        walkOutput.setText("Select a dog");
        JScrollPane walkOutputScroll = new JScrollPane(walkOutput);
        WalkDataListener listener = new WalkDataListener(control,walkOutput,this);
        JPanel controls = walkDataControlSetup(control,listener);
        walkOutput.setEditable(false);
        walkDataMenu.add(controls);
        walkDataMenu.add(walkOutputScroll);
        currentlyOpen = walkDataMenu;
        walkDataMenu.setVisible(true);
        return 5;
    }

    private Component[] walkDataControlsSetup() {
        JButton backWalk = new JButton("Back");
        JButton walkWalk = new JButton("Show Data");
        JComboBox dogsWalk = fetchDogs();
        return new Component[]{backWalk,walkWalk,dogsWalk};
    }

    private JPanel walkDataControlSetup(Component[] c,ActionListener l) {

        JPanel control = new JPanel();
        JButton bkWalk = (JButton) c[0];
        bkWalk.addActionListener(l);
        JButton wkWalk = (JButton) c[1];
        wkWalk.addActionListener(l);
        for (Component p: c) {
            control.add(p);
        }
        return control;
    }

    int quit() {
        currentlyOpen.dispose();
        tools.saveAll();
        System.exit(0);
        return 6;
    }


    void fetchWeather(JTextArea temp, JTextArea info, String selectedItem) {
        try {
            Location l = tools.locationAdmin.getLocation(new Location(selectedItem, 0, null));
            String output = WeatherParse.getCurrentWeatherOfCity(l.getCity(),true);
            String[] split = output.split(";");
            split[1] = new java.text.DecimalFormat("0.00").format(Double.parseDouble(split[1])).toString();
            split[1] += " Degrees Celsius";
            info.setText(split[0]);
            temp.setText(split[1]);

        } catch (Exception e) {
            e.printStackTrace();
            temp.setText("error");
            info.setText("error");
        }

    }


    void returnToMain() {
        currentlyOpen.dispose();
        openMainMenu();
    }

    void createLocation(JTextField[] userData) {
        String name = userData[1].getText();
        double distance = Double.parseDouble(userData[3].getText());
        String city = userData[5].getText();
        boolean already = tools.locationAdmin.newLocation(name,distance,city);
        if (!already) {
            errorPopup("Location already exists");
        }
        returnToMain();

    }

    void newDog(JTextField[] userData, JTextField[] extraData, boolean otherDog) throws NumberFormatException {
        String name = userData[1].getText();
        int age = Integer.parseInt(userData[3].getText());

        if (!otherDog) {
            try {
                tools.dogAdmin.newDog(name,age);
            } catch (AlreadyInSystemException e) {
                errorPopup("Dog already in system");
            }
        } else {
            String ownerName = extraData[1].getText();
            String date = extraData[3].getText();
            try {
                tools.dogAdmin.newOtherDog(name,age,ownerName,date);
            } catch (AlreadyInSystemException e) {
                errorPopup("Other Dog already in system");
            }

        }
    }

    void outputDog(String dogName) {
        String output = tools.dogAdmin.viewWalk(dogName);
        walkOutput.setText(output);
    }

    void enterWalk() {
        tools.newWalk(nwDogs.getSelectedItem().toString(),nwLocations.getSelectedItem().toString());
    }
}
