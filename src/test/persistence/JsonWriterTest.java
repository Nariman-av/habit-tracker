
package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
// CITATION: used our JsonSerializationDemo as a template
public class JsonWriterTest {

    private HabitTracker habitTracker = new HabitTracker();
    private List<Habit> goodHabits = new ArrayList<>();
    private List<Habit> badHabits= new ArrayList<>();

    @BeforeEach
    public void setup() {

        Habit goodA = new GoodHabit(1, "Walk", PriorityLevel.LOW,
                "15 minute walk every afternoon");
        Habit goodB = new GoodHabit(2, "Read Books", PriorityLevel.MEDIUM,
                "read a thoughtful book before sleep");
        Habit goodC = new GoodHabit(3, "Water Plants", PriorityLevel.HIGH,
                "water balcony's plants");
        Habit badA = new BadHabit(1, "Fast Food", PriorityLevel.HIGH,
                "less fast food, more home cooked meals");
        Habit badB = new BadHabit(2, "Social Media", PriorityLevel.MEDIUM,
                "limit to 1 hour a day");
        Habit badC = new BadHabit(3, "Candy", PriorityLevel.MEDIUM,
                "limit candy to 1 piece a day");

        habitTracker.addHabit(goodA, true);
        habitTracker.addHabit(goodB, true);
        habitTracker.addHabit(goodC, true);
        habitTracker.addHabit(badA, false);
        habitTracker.addHabit(badB, false);
        habitTracker.addHabit(badC, false);

    }

    @Test
    public void testWriterInvalidFile() {
        try {
            HabitTracker ht = new HabitTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException wasn't thrown");
        } catch (IOException e) {
        }
    }


    @Test
    void testWriterEmptyHabitTracker() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHabitTracker.json");
            writer.open();
            writer.write(new HabitTracker());
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHabitTracker.json");
            habitTracker = reader.read();
            assertEquals(0, habitTracker.getGoodHabits().size());
            assertEquals(0, habitTracker.getBadHabits().size());

        } catch (IOException e) {
            fail("Caught IOException");
        }
    }


    @Test
    void testWriterGeneralHabitTracker() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHabitTracker.json");
            writer.open();
            writer.write(habitTracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHabitTracker.json");
            HabitTracker ht = reader.read();
            assertEquals(3, ht.getGoodHabits().size());
            assertEquals(3, ht.getBadHabits().size());

        } catch (IOException e) {
            fail("Caught IOException");
        }
    }
}
