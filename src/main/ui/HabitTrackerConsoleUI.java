package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Application of Habit Tracker user interface
class HabitTrackerConsoleUI {
    private static final String JSON_STORE = "./data/habitTracker.json";
    private static final boolean GOOD = true;
    private static final boolean BAD = false;
    private static HabitTracker habitTracker = new HabitTracker();
    private Scanner input;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: construct new scanner and run the habit tracker
    public HabitTrackerConsoleUI() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        System.out.println("Welcome to the Habit Tracker!");
        runHabitTracker();
    }

    // MODIFIES: this
    // EFFECTS: display menu, receive user input, and exit program if input is "q"
    public void runHabitTracker() {
        displayMenu();
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            command = getInput();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processHabitTracker(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // REQUIRES: one of a, v, r, q, l, s keys
    // EFFECTS: processes user command inputs
    public void processHabitTracker(String command) {
        switch (command) {
            case "a":
                addHabit();
                break;
            case "v":
                viewHabits();
                break;
            case "r":
                removeHabit();
                break;
            case "s":
                saveHabitTracker();
                break;
            case "l":
                loadHabitTracker();
                break;
            case "q":
                System.out.println("Goodbye!");
                break;
            default:
                tryAgainMessage();
                break;
        }
    }

    // CITATION: used Serialization Demo as template
    // EFFECTS: saves the HabitTracker to file
    private void saveHabitTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(habitTracker);
            jsonWriter.close();
            System.out.println("Saved your List of Habits to " + JSON_STORE + "\n> ");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }


    // CITATION: used Serialization Demo as template
    // EFFECTS: load habitTracker from file
    private void loadHabitTracker() {
        try {
            habitTracker = jsonReader.read();
            System.out.println("Loaded your List of Habits from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // EFFECTS: prints main menu options to select
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add a habit");
        System.out.println("\tv -> View your habits");
        System.out.println("\tr -> Remove a habit");
        System.out.println("\ts -> save habits to file");
        System.out.println("\tl -> load habits from file");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: complete creating a good or bad habit
    private void addHabit() {
        System.out.println("What type of habit would you like to add?");
        System.out.println("\t1 -> Good");
        System.out.println("\t2 -> Bad");

        boolean keepGoing = true;

        while (keepGoing) {
            String command = getInput();
            if (command.equals("1")) {
                processAddHabit(GOOD);
                keepGoing = false;
            } else if (command.equals("2")) {
                processAddHabit(BAD);
                keepGoing = false;
            } else {
                tryAgainMessage();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: choose name, priority level, and note for your new habit
    private void processAddHabit(boolean isGood) {
        System.out.println("What is the name of your Habit?");
        String habitName = getInput();
        System.out.println("Select a priority level for your this habit: " + habitName);
        System.out.println("1 -> Low");
        System.out.println("2 -> Medium");
        System.out.println("3 -> High");
        PriorityLevel pl = getPriorityLevel();

        System.out.println("Please add any additional notes:");
        String notes = getUserInput();
        System.out.println("note is  " + notes);
        if (isGood) {
            int id = habitTracker.getGoodHabits().size();
            Habit habit = new GoodHabit(id + 1, habitName, pl, notes);
            habitTracker.addHabit(habit, isGood);
        } else {
            int id = habitTracker.getBadHabits().size();
            Habit habit = new BadHabit(id, habitName, pl, notes);
            habitTracker.addHabit(habit, isGood);
        }
        System.out.println("You have successfully added a new habit: " + habitName);
        runHabitTracker();
    }

    // EFFECTS: give the right priority level to the new habit
    private PriorityLevel getPriorityLevel() {
        while (true) {
            String priorityLevel = getInput();
            if (priorityLevel.equals("1")) {
                return PriorityLevel.LOW;
            } else if (priorityLevel.equals("2")) {
                return PriorityLevel.MEDIUM;
            } else if (priorityLevel.equals("3")) {
                return PriorityLevel.HIGH;
            } else {
                tryAgainMessage();
            }

        }
    }


    // EFFECTS: view a separate list of good and bad habits
    private void viewHabits() {
        List<Habit> goodHabits = habitTracker.getGoodHabits();
        List<Habit> badHabits = habitTracker.getBadHabits();
        System.out.println("GOOD HABITS:");
        displayHabits(goodHabits);
        System.out.println("\nBAD HABITS:");
        displayHabits(badHabits);
        runHabitTracker();
    }

    // EFFECTS: give incrementing ID's to habits, then print separate
    //          good and bad habits including all their information
    private void displayHabits(List<Habit> habits) {
        for (int i = 0; i < habits.size(); i++) {
            Habit habit = habits.get(i);
            System.out.println("\t ID: " + Integer.toString(i + 1) + "\t Name: " + habit.getName()
                    + "\t Priority Level: " + habit.getPriorityLevel());
            String note = habit.getNote();
            if (!note.equals("")) {
                System.out.println("\t Note: " + habit.getNote());
            }
            System.out.println("- - - - - - - - - - - - - - - - - - - - - -");
        }
    }

    // MODIFIES: this
    // EFFECTS: remove a good or bad habit
    private void removeHabit() {
        System.out.println("Select the type of Habit to remove");
        System.out.println("\t1 -> Good");
        System.out.println("\t2 -> Bad");

        boolean keepGoing = true;

        while (keepGoing) {
            String command = getInput();
            if (command.equals("1")) {
                processRemoveGoodHabit();
                keepGoing = false;
            } else if (command.equals("2")) {
                processRemoveBadHabit();
                keepGoing = false;
            }  else if (command.equals("q")) {
                keepGoing = false;
            } else {
                tryAgainMessage();
            }
        }
        runHabitTracker();
    }

    // MODIFIES: this
    // EFFECTS: remove a bad habit based on the ID input
    private void processRemoveBadHabit() {
        List<Habit> badHabits = habitTracker.getBadHabits();
        System.out.println("Select the ID of the bad habit to remove:");
        displayHabits(badHabits);
        while (true) {
            String command = getInput();
            int id = Integer.parseInt(command);
            if (id >= 0 && id <= badHabits.size()) {
                badHabits.remove(id - 1);
                System.out.println("You have successfully removed the bad habit");
                break;
            } else {
                tryAgainMessage();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: remove a good habit based on the ID input
    private void processRemoveGoodHabit() {
        List<Habit> goodHabits = habitTracker.getGoodHabits();
        System.out.println("Select the ID of the good habit to remove:");
        displayHabits(goodHabits);
        while (true) {
            String command = getInput();
            int id = Integer.parseInt(command);
            if (id >= 0 && id <= goodHabits.size()) {
                goodHabits.remove(id - 1);
                System.out.println("You have successfully removed the good habit");
                break;
            } else {
                tryAgainMessage();
            }
        }
    }

    // EFFECTS: tells user to try a different input
    private void tryAgainMessage() {
        System.out.print("Please try again!" + "\n> ");
    }


    // MODIFIES: this
    // EFFECTS: returns the next user input in all lowercase, trimmed and spaces removed
    private String getInput() {
        input = new Scanner(System.in);
        return input.next().toLowerCase().trim().replace(" ", "");
    }

    //Effects: returns the exact user input
    private String getUserInput() {
        input = new Scanner(System.in);
        return input.nextLine();
    }
}
