package persistence;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads sessions from JSON data stored in file
// CITATION: used the JsonSerializationDemo as a template
public class JsonReader {

    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads sessions from file and returns it;
    // throws IOException if an error occurs reading data from file
    public HabitTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHabitTracker(jsonObject.getJSONObject("HabitTracker"));
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Habit Tracker from JSON object and returns it
    private HabitTracker parseHabitTracker(JSONObject jsonObject) {

        HabitTracker habitTracker = new HabitTracker();
        parseGoodHabits(habitTracker, jsonObject.getJSONArray("goodHabits"));
        parseBadHabits(habitTracker, jsonObject.getJSONArray("badHabits"));
        return habitTracker;
    }

    // EFFECTS: parses list of good habits from the JSON array
    private void parseGoodHabits(HabitTracker habitTracker, JSONArray array) {
        for (Object json : array) {
            JSONObject nextGoodHabit = (JSONObject) json;
            parseGoodHabit(habitTracker, nextGoodHabit);
        }

    }

    // EFFECTS: parses good habits from the JSON object
    private void parseGoodHabit(HabitTracker habitTracker, JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        PriorityLevel priorityLevel = PriorityLevel.valueOf(jsonObject.getString("priorityLevel"));
        String note = jsonObject.getString("note");
        Habit goodHabit = new GoodHabit(id, name, priorityLevel, note);
        habitTracker.addHabit(goodHabit, true);
    }


    // EFFECTS: parses list of bad habits from the JSON array
    private void parseBadHabits(HabitTracker habitTracker, JSONArray array) {
        for (Object json : array) {
            JSONObject nextBadHabit = (JSONObject) json;
            parseBadHabit(habitTracker, nextBadHabit);
        }
    }

    // EFFECTS: parses bad habits from the JSON object
    private void parseBadHabit(HabitTracker habitTracker, JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        PriorityLevel priorityLevel = PriorityLevel.valueOf(jsonObject.getString("priorityLevel"));
        String note = jsonObject.getString("note");
        Habit goodHabit = new GoodHabit(id, name, priorityLevel, note);
        habitTracker.addHabit(goodHabit, false);
    }


}
