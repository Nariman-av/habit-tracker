package model;

import org.json.JSONObject;
import persistence.Writable;

// Representing a bad type of habit
public class BadHabit extends Habit implements Writable {

    /* REQUIRES: id > 0 AND Habit name has non-zero length
     * EFFECTS: uses habit abstract class to construct a bad habit with a
     * unique id starting from 1 and incrementing; given priority level
     * and a customized note
     */
    public BadHabit(int id, String name, PriorityLevel priorityLevel, String note) {
        super(id, name, priorityLevel, note);
    }

}
