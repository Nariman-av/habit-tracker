package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a habit tracker with lists of good and bad habits
public class HabitTracker implements Writable {

    private List<Habit> goodHabits;
    private List<Habit> badHabits;

    // EFFECTS: create a habit tracker with two lists representing good and bad habits
    public HabitTracker() {
        this.goodHabits = new ArrayList<>();
        this.badHabits = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("A new Habit Tracker created"));
    }


    /* MODIFIES: this
     * EFFECTS: if isGood is true, add the given habit to
     * the list of good habits; if false, add it to the
     * list of bad habits
     */
    public void addHabit(Habit habit, Boolean isGood) {
        if (isGood) {
            this.goodHabits.add(habit);
            Event e = new Event(habit.name + " added to list of Good Habits");
            EventLog.getInstance().logEvent(e);
        } else {
            this.badHabits.add(habit);
            Event e = new Event(habit.name + " added to list of Bad Habits");
            EventLog.getInstance().logEvent(e);
        }


    }

    /* MODIFIES: this
     * EFFECTS: if isGood is true, remove given habit from
     * the list of good habits; if false, remove it from the
     * list of bad habits
     */
    public void removeHabit(Habit habit, Boolean isGood) {
        if (isGood) {
            this.goodHabits.remove(habit);
            Event e = new Event(habit.name + " removed from list of Good Habits");
            EventLog.getInstance().logEvent(e);
        } else {
            this.badHabits.remove(habit);
            Event e = new Event(habit.name + " removed from list of Bad Habits");
            EventLog.getInstance().logEvent(e);
        }
    }

    /* REQUIRES: habit with given name is in the list of good or bad habits
     * MODIFIES: this
     * EFFECTS: if isGood is true, remove given name from
     * the list of good habits; if false, remove it from the
     * list of bad habits
     */
    public void removeHabit(String name, Boolean isGood) {
        int index = getIndexOf(name);
        if (isGood) {
            goodHabits.remove(index);
            Event e = new Event(name + " removed from list of Good Habits");
            EventLog.getInstance().logEvent(e);
        } else {
            badHabits.remove(index);
            Event e = new Event(name + " removed from list of Bad Habits");
            EventLog.getInstance().logEvent(e);
        }
    }

    // getters
    public List<Habit> getGoodHabits() {
        return goodHabits;
    }

    public List<Habit> getBadHabits() {
        return badHabits;
    }


    // REQUIRES: habit with given name is in the list of good or bad habits
    // EFFECTS: returns string with all the details of a habit
    public String getHabitDetails(String name, Boolean isGood) {
        int id = getIndexOf(name);
        if (isGood) {
            Habit habit = goodHabits.get(id); // index = id
            return ("Name: " + habit.getName() + "\nPriority Level: " + habit.getPriorityLevel()
                    + "\nNote: " + habit.getNote());
        } else {
            Habit habit = badHabits.get(id); // index = id
            return ("Name: " + habit.getName() + "\nPriority Level: " + habit.getPriorityLevel()
                    + "\nNote: " + habit.getNote());
        }
    }

    // EFFECTS: returns the index of good or bad habit with given name
    //          if not found, return -1
    public int getIndexOf(String name) {
        int index = 0;
        for (Habit h : goodHabits) {
            index++;
            if (h.getName() == name) {
                return index - 1;
            }
        }
        index = 0;
        for (Habit h : badHabits) {
            index++;
            if (h.getName() == name) {
                return index - 1;
            }
        }
        return -1;
    }


    // EFFECTS: converts this to a Json object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject mainJson = new JSONObject();
        JSONObject json = new JSONObject();
        json.put("goodHabits", goodHabitsToJson());
        json.put("badHabits", badHabitsToJson());
        mainJson.put("HabitTracker", json);
        return mainJson;
    }



    // EFFECTS: return the list of good habits as a JSON array
    private JSONArray goodHabitsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Habit habit : goodHabits) {
            jsonArray.put(habit.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: return the list of bad habits as a JSON array
    private JSONArray badHabitsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Habit habit : badHabits) {
            jsonArray.put(habit.toJson());
        }
        return jsonArray;
    }



}
