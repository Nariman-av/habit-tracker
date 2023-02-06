package model;

import org.json.JSONObject;
import persistence.Writable;

// Representing a good type of habit with potential differences than bad habit
public class GoodHabit extends Habit implements Writable {

    /* REQUIRES: id > 0 AND Habit name has non-zero length
     * EFFECTS: uses habit abstract class to construct a good habit with a
     * unique id starting from 1 and incrementing; given priority level
     * and a customized note
     */
    public GoodHabit(int id, String name, PriorityLevel priorityLevel, String note) {
        super(id, name, priorityLevel, note);
    }



}
