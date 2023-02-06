package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
// CITATION: used our JsonSerializationDemo as a template
class JsonReaderTest extends JsonTest {

    private HabitTracker habitTracker;
    private List<Habit> goodHabits = new ArrayList<>();
    private List<Habit> badHabits = new ArrayList<>();

    @BeforeEach
    public void setup() {

        habitTracker = new HabitTracker();


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
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            habitTracker = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmptyHabitTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHabitTracker.json");
        try {
            habitTracker = reader.read();
            assertEquals(0, habitTracker.getGoodHabits().size());
            assertEquals(0, habitTracker.getBadHabits().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralHabitTracker() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHabitTracker.json");
        JsonWriter writer = new JsonWriter("./data/testReaderGeneralHabitTracker.json");
        try {
            writer.open();
            writer.write(habitTracker);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Caught FileNotFoundException");
        }
        try {
            HabitTracker habitTrackerRead = reader.read();
            assertEquals(3, habitTrackerRead.getGoodHabits().size());
            assertEquals(3, habitTrackerRead.getBadHabits().size());
            List<Habit> goodHabitsExpected = habitTracker.getGoodHabits();
            List<Habit> goodHabitsActual = habitTrackerRead.getGoodHabits();
            List<Habit> badHabitsExpected = habitTracker.getBadHabits();
            List<Habit> badHabitsActual = habitTrackerRead.getBadHabits();
            for (int i=0; i< habitTrackerRead.getBadHabits().size(); i++) {
                Habit goodHabitExpected = goodHabitsExpected.get(i);
                Habit goodHabitActual = goodHabitsActual.get(i);
                checkHabit(goodHabitExpected, goodHabitActual);
                Habit badHabitExpected = badHabitsExpected.get(i);
                Habit badHabitActual = badHabitsActual.get(i);
                checkHabit(badHabitExpected, badHabitActual);
            }
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }



}