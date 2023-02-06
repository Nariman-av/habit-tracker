package model;

import org.json.JSONObject;
import persistence.Writable;
// Represents a habit having an id, name, priority level, along with a customized note

public abstract class Habit implements Writable {


    protected String name;
    protected int id;
    protected PriorityLevel priorityLevel;
    protected String note;


    /* REQUIRES: id > 0, Habit name has non-zero length
     * EFFECTS: Not instantiated; constructs a new habit with a
     * unique id starting from 1 and incrementing; given priority level
     * and a customized note
     */
    public Habit(int id, String name, PriorityLevel priorityLevel, String note) {
        this.id = id;
        this.name = name;
        this.priorityLevel = priorityLevel;
        this.note = note;

    }

    //getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public String getNote() {
        return note;
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("id", id);
        json.put("priorityLevel", priorityLevel);
        json.put("note", note);
        return json;
    }

}
