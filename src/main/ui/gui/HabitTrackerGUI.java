package ui.gui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

// Represents the Main Graphical User Interface
public class HabitTrackerGUI extends JFrame implements ActionListener, ListSelectionListener {

    private static final String JSON_STORE = "./data/HabitTracker.json";
    private final int fontSize = 18;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel habitTrackerPanel;

    private HabitTracker habitTracker;
    // GoodHabits panel
    private JPanel goodHabitsPanel;
    private JSplitPane goodHabitsSplitPane;
    private JPanel goodHabitsButtons;
    private JList goodHabitsList;
    private DefaultListModel goodHabitsModel;
    private JTextArea goodHabitsText;
    private JButton addGoodHabitButton;
    private JButton removeGoodHabitButton;
    // BadHabits panel
    private JPanel badHabitsPanel;
    private JSplitPane badHabitsSplitPane;
    private JPanel badHabitsButtons;
    private JList badHabitsList;
    private DefaultListModel badHabitsModel;
    private JTextArea badHabitsText;
    private JButton addBadHabitButton;
    private JButton removeBadHabitButton;
    // Menu Header
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveHabits;
    private JMenuItem loadHabits;


    // EFFECTS: Initialize and create the main application
    public HabitTrackerGUI() {
        super("Habit Tracker");
        initializeFields();
        initializeFrame();
        addMenuHeader();
        addGoodHabitPanel();
        addBadHabitPanel();
        addHabitTrackerPanel();
        setVisible(true);
    }


    // Citations: https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
    //           https://stackoverflow.com/questions/8597140/system-exit0-vs-jframe-exit-on-close/8597161#8597161
    // EFFECTS: print all logged events in order when program is exited
    @Override
    public void processWindowEvent(WindowEvent windowEvent) {
        if (windowEvent.getID() == WindowEvent.WINDOW_CLOSING) {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString() + "\n");
            }
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize appropriate fields for GUI
    private void initializeFields() {
        this.habitTracker = new HabitTracker();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

    }

    // MODIFIES: this
    // EFFECTS: choose frame size and other minor operations
    private void initializeFrame() {
        setSize(new Dimension(840, 660));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

    }


    // MODIFIES: this
    // EFFECTS: adds a file button with save and load options to the menu bar
    private void addMenuHeader() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        saveHabits = new JMenuItem("Save Habits");
        loadHabits = new JMenuItem("Load Habits");
        saveHabits.addActionListener(this);
        loadHabits.addActionListener(this);
        fileMenu.add(saveHabits);
        fileMenu.add(loadHabits);

        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: adds the main panel which consists 2 other panels
    private void addHabitTrackerPanel() {
        habitTrackerPanel = new JPanel();
        habitTrackerPanel.setLayout(new GridLayout(2,1));
        habitTrackerPanel.add(goodHabitsPanel);
        habitTrackerPanel.add(badHabitsPanel);
        add(habitTrackerPanel);
    }

    // MODIFIES: this
    // EFFECTS: required buttons are added to goodHabit panel
    private void addGoodHabitPanel() {
        goodHabitsPanel = new JPanel();
        goodHabitsPanel.setLayout(new BorderLayout());
        goodHabitsModel = new DefaultListModel();
        goodHabitsButtons = new JPanel();
        goodHabitsButtons.setPreferredSize(new Dimension(200, 100));
        goodHabitsButtons.setLayout(new GridLayout(3, 1));

        goodHabitsList = new JList(goodHabitsModel);
        goodHabitsList.setFont(new Font("Arial", Font.PLAIN, fontSize));
        goodHabitsList.setMinimumSize(new Dimension(200, 80));
        goodHabitsList.addListSelectionListener(this);
        goodHabitsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        goodHabitsText = new JTextArea();
        goodHabitsText.setFont(new Font("Arial", Font.PLAIN, fontSize));

        goodHabitsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, goodHabitsList, goodHabitsText);
        goodHabitsSplitPane.setEnabled(false);

        addGoodHabitButtons();
        addGoodHabitSplitPane();
    }

    // MODIFIES: this
    // EFFECTS: required buttons are added to badHabit panel
    private void addBadHabitPanel() {
        badHabitsPanel = new JPanel();
        badHabitsPanel.setLayout(new BorderLayout());
        badHabitsModel = new DefaultListModel();
        badHabitsButtons = new JPanel();
        badHabitsButtons.setPreferredSize(new Dimension(200, 80));
        badHabitsButtons.setLayout(new GridLayout(3, 1));

        badHabitsList = new JList(badHabitsModel);
        badHabitsList.setFont(new Font("Arial", Font.PLAIN, fontSize));
        badHabitsList.setMinimumSize(new Dimension(200, 80));
        badHabitsList.addListSelectionListener(this);
        badHabitsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        badHabitsText = new JTextArea();
        badHabitsText.setFont(new Font("Arial", Font.PLAIN, fontSize));

        badHabitsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, badHabitsList, badHabitsText);
        badHabitsSplitPane.setEnabled(false);

        addBadHabitButtons();
        addBadHabitSplitPane();
    }


    // MODIFIES: this
    // EFFECTS: places add and remove buttons in GoodHabit Panel along with a logo
    private void addGoodHabitButtons() {
        addGoodHabitButton = new JButton("Add Good Habit");
        addGoodHabitButton.addActionListener(this);
        goodHabitsButtons.add(addGoodHabitButton);
        removeGoodHabitButton = new JButton("Remove Good Habit");
        removeGoodHabitButton.addActionListener(this);
        goodHabitsButtons.add(removeGoodHabitButton);

        ImageIcon img = new ImageIcon("./data/goodHabitLogo1.jpg");
        JLabel goodLogo = new JLabel();
        goodLogo.setIcon(new ImageIcon(img.getImage()
                .getScaledInstance(200, 100, Image.SCALE_SMOOTH)));
        goodLogo.setHorizontalAlignment(JLabel.CENTER);
        goodHabitsButtons.add(goodLogo);
    }

    // MODIFIES: this
    // EFFECTS: places add and remove buttons in BadHabit Panel along with a logo
    private void addBadHabitButtons() {
        addBadHabitButton = new JButton("Add Bad Habit");
        addBadHabitButton.addActionListener(this);
        badHabitsButtons.add(addBadHabitButton);
        removeBadHabitButton = new JButton("Remove Bad Habit");
        removeBadHabitButton.addActionListener(this);
        badHabitsButtons.add(removeBadHabitButton);

        ImageIcon img = new ImageIcon("./data/badHabitsLogo.jpg");
        JLabel goodLogo = new JLabel();
        goodLogo.setIcon(new ImageIcon(img.getImage()
                .getScaledInstance(200, 100, Image.SCALE_SMOOTH)));
        goodLogo.setHorizontalAlignment(JLabel.CENTER);
        badHabitsButtons.add(goodLogo);
    }

    // MODIFIES: this
    // EFFECTS: adds a split pane for Good Habits
    private void addGoodHabitSplitPane() {
        JLabel panelTitle = new JLabel("Good Habits");
        panelTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitle.setHorizontalAlignment(JLabel.CENTER);
        panelTitle.setForeground(Color.WHITE);
        panelTitle.setOpaque(true);
        panelTitle.setBackground(new Color(0x006600));
        goodHabitsPanel.add(panelTitle, BorderLayout.NORTH);
        goodHabitsPanel.add(goodHabitsSplitPane, BorderLayout.CENTER);
        goodHabitsPanel.add(goodHabitsButtons, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: adds a split pane for Bad Habits
    private void addBadHabitSplitPane() {
        JLabel panelTitle = new JLabel("Bad Habits");
        panelTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitle.setHorizontalAlignment(JLabel.CENTER);
        panelTitle.setForeground(Color.WHITE);
        panelTitle.setBackground(new Color(0x660000));
        panelTitle.setOpaque(true);
        badHabitsPanel.add(panelTitle, BorderLayout.NORTH);
        badHabitsPanel.add(badHabitsSplitPane, BorderLayout.CENTER);
        badHabitsPanel.add(badHabitsButtons, BorderLayout.EAST);
    }


    // MODIFIES: this
    // EFFECTS: adds a new good habit based on user's given name, priority level and note
    private void addGoodHabit() {
        int id = habitTracker.getGoodHabits().size();
        String name = JOptionPane.showInputDialog("What is the name of your Good Habit?");
        String priorityLevel = (JOptionPane.showInputDialog(
                "Enter a priority level (LOW, MEDIUM, or HIGH):"));
        String note = JOptionPane.showInputDialog("Please add any additional notes:");
        Habit goodHabit = new GoodHabit(id, name, getEnumIgnoreCase(priorityLevel), note);
        habitTracker.addHabit(goodHabit, true);
        goodHabitsModel.addElement(name);
        goodHabitsList.setSelectedIndex(goodHabitsList.getLastVisibleIndex());
    }

    // MODIFIES: this
    // EFFECTS: adds a new bad habit based on user's given name, priority level and note
    private void addBadHabit() {
        int id = habitTracker.getBadHabits().size();
        String name = JOptionPane.showInputDialog("What is the name of your Bad Habit?");
        String priorityLevel = (JOptionPane.showInputDialog(
                "Enter a priority level (LOW, MEDIUM, or HIGH):"));
        String note = JOptionPane.showInputDialog("Please add any additional notes:");
        Habit badHabit = new GoodHabit(id, name, getEnumIgnoreCase(priorityLevel), note);
        habitTracker.addHabit(badHabit, false);
        badHabitsModel.addElement(name);
        badHabitsList.setSelectedIndex(badHabitsList.getLastVisibleIndex());
    }

    // reference: https://stackoverflow.com/questions/28332924/case-insensitive-matching-of-a-string-to-a-java-enum
    // EFFECTS: return the Priority Level enum matching case-insensitive given string
    //          return null if string does not match
    private PriorityLevel getEnumIgnoreCase(String priorityLevel) {
        for (PriorityLevel d : PriorityLevel.values()) {
            if (d.name().equalsIgnoreCase(priorityLevel)) {
                return d;
            }
        }
        return null;
    }


    // MODIFIES: this
    // EFFECTS: removes the selected good habit
    private void removeGoodHabit() {
        if (goodHabitsList.getSelectedIndex() != -1) {
            String name = goodHabitsModel.remove(goodHabitsList.getSelectedIndex()).toString();
            habitTracker.removeHabit(name, true);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the selected bad habit
    private void removeBadHabit() {
        if (badHabitsList.getSelectedIndex() != -1) {
            String name = badHabitsModel.remove(badHabitsList.getSelectedIndex()).toString();
            habitTracker.removeHabit(name, false);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves all the current habits to JSON file
    private void saveHabits() {
        try {
            jsonWriter.open();
            jsonWriter.write(habitTracker);
            jsonWriter.close();
            System.out.println("Saved your List of Habits to " + JSON_STORE + "\n> ");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file to " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: reads source file and displays it
    private void loadHabits() {
        try {
            habitTracker = jsonReader.read();
            System.out.println("Loaded your List of Habits from " + JSON_STORE);
            goodHabitsModel.removeAllElements();
            badHabitsModel.removeAllElements();
            for (Habit h : habitTracker.getGoodHabits()) {
                goodHabitsModel.addElement(h.getName());
            }
            for (Habit h : habitTracker.getBadHabits()) {
                badHabitsModel.addElement(h.getName());
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }


    }

    // EFFECTS: every button pressed represents a unique action
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addGoodHabitButton) {
            addGoodHabit();
        } else if (ae.getSource() == addBadHabitButton) {
            addBadHabit();
        } else if (ae.getSource() == removeGoodHabitButton) {
            removeGoodHabit();
        } else if (ae.getSource() == removeBadHabitButton) {
            removeBadHabit();
        } else if (ae.getSource() == saveHabits) {
            saveHabits();
        } else if (ae.getSource() == loadHabits) {
            loadHabits();
        }
    }


    // EFFECTS: change habit details displayed based on the current habit selected
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        if (listSelectionEvent.getSource() == goodHabitsList) {
            goodHabitsText.setText("");
            if (goodHabitsList.getSelectedIndex() != -1) { // if goodHabits not empty
                goodHabitsText.setText(habitTracker.getHabitDetails(goodHabitsList.getSelectedValue().toString(),
                        true));
            }
        } else if (listSelectionEvent.getSource() == badHabitsList) {
            badHabitsText.setText("");
            if (badHabitsList.getSelectedIndex() != -1) { // if badHabits not empty
                badHabitsText.setText(habitTracker.getHabitDetails(badHabitsList.getSelectedValue().toString(),
                        false));
            }
        }
    }


}
















