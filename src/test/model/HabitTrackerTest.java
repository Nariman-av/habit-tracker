package model;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HabitTrackerTest {

    private HabitTracker testHabitTracker;
    private Habit goodA;
    private Habit goodB;
    private Habit goodC;
    private Habit badA;
    private Habit badB;
    private Habit badC;
    private EventLog el;
    private List<Event> log;


    @BeforeEach
    void runBefore() {
        testHabitTracker = new HabitTracker();
        this.goodA = new GoodHabit(1, "Walk", PriorityLevel.LOW,
                "15 minute walk every afternoon");
        this.goodB = new GoodHabit(2, "Read Books", PriorityLevel.MEDIUM,
                "read a thoughtful book before sleep");
        this.goodC = new GoodHabit(3, "Water Plants", PriorityLevel.HIGH,
                "water balcony's plants");
        this.badA = new BadHabit(1, "Fast Food", PriorityLevel.HIGH,
                "less fast food, more home cooked meals");
        this.badB = new BadHabit(2, "Social Media", PriorityLevel.MEDIUM,
                "limit to 1 hour a day");
        this.badC = new BadHabit(3, "Candy", PriorityLevel.MEDIUM,
                "limit candy to 1 piece a day");
        log = new LinkedList<>();
        el = EventLog.getInstance();
        el.clear();
    }

    @Test
    public void constructorTest() {
        assertEquals(0, testHabitTracker.getGoodHabits().size());
        assertEquals(0, testHabitTracker.getBadHabits().size());
    }

    @Test
    public void addOneGoodHabitTest() {
        testHabitTracker.addHabit(goodA, true);
        assertEquals(1, testHabitTracker.getGoodHabits().size());
        assertEquals(0, testHabitTracker.getBadHabits().size());
        assertTrue(testHabitTracker.getGoodHabits().contains(goodA));

        for (Event event : el) {
            log.add(event);
        }
        assertEquals(log.get(0).getDescription(), "Event log cleared.");
        assertEquals(log.get(1).getDescription(), "Walk added to list of Good Habits");
    }

    @Test
    public void addOneBadHabitTest() {
        testHabitTracker.addHabit(badA, false);
        assertEquals(0, testHabitTracker.getGoodHabits().size());
        assertEquals(1, testHabitTracker.getBadHabits().size());
        assertEquals(badA, testHabitTracker.getBadHabits().get(0));


        for (Event event : el) {
            log.add(event);
        }
        assertEquals(log.get(0).getDescription(), "Event log cleared.");
        assertEquals(log.get(1).getDescription(), "Fast Food added to list of Bad Habits");
    }

    @Test
    public void addOneGoodOneBadHabitTest() {
        testHabitTracker.addHabit(goodA, true);
        testHabitTracker.addHabit(badA, false);
        assertTrue(testHabitTracker.getGoodHabits().contains(goodA));
    }


    @Test
    public void addMultipleGoodHabitsTest() {
        List<Habit> habitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            GoodHabit goodHabit = new GoodHabit(i , "Good Habit " + i, PriorityLevel.LOW, "");
            habitsToAdd.add(goodHabit);
            testHabitTracker.addHabit(goodHabit, true);
        }
        assertEquals(3, testHabitTracker.getGoodHabits().size());
        assertEquals(0, testHabitTracker.getBadHabits().size());

        List<Habit> goodHabits = testHabitTracker.getGoodHabits();
        for (Habit habit : habitsToAdd) {
            assertTrue(goodHabits.contains(habit));
        }
        for (Event event : el) {
            log.add(event);
        }
        assertEquals(log.get(0).getDescription(), "Event log cleared.");
        assertEquals(log.get(1).getDescription(), "Good Habit 1 added to list of Good Habits");
        assertEquals(log.get(2).getDescription(), "Good Habit 2 added to list of Good Habits");
        assertEquals(log.get(3).getDescription(), "Good Habit 3 added to list of Good Habits");
    }

    @Test
    public void addMultipleBadHabitsTest() {
        List<Habit> habitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            BadHabit badHabit = new BadHabit(i ,"Bad Habit " + i, PriorityLevel.MEDIUM, "");
            habitsToAdd.add(badHabit);
            testHabitTracker.addHabit(badHabit, false);
        }
        assertEquals(0, testHabitTracker.getGoodHabits().size());
        assertEquals(3, testHabitTracker.getBadHabits().size());

        List<Habit> badHabits = testHabitTracker.getBadHabits();
        for (Habit habit : habitsToAdd) {
            assertTrue(badHabits.contains(habit));
        }
        // For event log
        for (Event event : el) {
            log.add(event);
        }
        assertEquals(log.get(0).getDescription(), "Event log cleared.");
        assertEquals(log.get(1).getDescription(), "Bad Habit 1 added to list of Bad Habits");
        assertEquals(log.get(2).getDescription(), "Bad Habit 2 added to list of Bad Habits");
        assertEquals(log.get(3).getDescription(), "Bad Habit 3 added to list of Bad Habits");
    }

    @Test
    public void addMultipleGoodHabitsHighVolumeTest() {
        List<Habit> habitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            GoodHabit goodHabit = new GoodHabit( i,"Good Habit " + i, PriorityLevel.LOW, "");
            habitsToAdd.add(goodHabit);
            testHabitTracker.addHabit(goodHabit, true);
        }
        assertEquals(1000, testHabitTracker.getGoodHabits().size());
        assertEquals(0, testHabitTracker.getBadHabits().size());

        List<Habit> goodHabits = testHabitTracker.getGoodHabits();
        for (Habit habit : habitsToAdd) {
            assertTrue(goodHabits.contains(habit));
        }
    }


    @Test
    public void addMultipleBadHabitsHighVolumeTest() {
        List<Habit> habitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            BadHabit badHabit = new BadHabit(i,"Bad Habit " + i, PriorityLevel.MEDIUM, "");
            habitsToAdd.add(badHabit);
            testHabitTracker.addHabit(badHabit, false);
        }
        assertEquals(0, testHabitTracker.getGoodHabits().size());
        assertEquals(1000, testHabitTracker.getBadHabits().size());

        List<Habit> badHabits = testHabitTracker.getBadHabits();
        for (Habit habit : habitsToAdd) {
            assertTrue(badHabits.contains(habit));
        }
    }

    @Test
    public void addMultipleHabitsAlternateGoodAndBadTest() {
        List<Habit> goodHabitsToAdd = new ArrayList<>();
        List<Habit> badHabitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            GoodHabit goodHabit = new GoodHabit(i,"Good Habit " + i, PriorityLevel.MEDIUM, "");
            BadHabit badHabit = new BadHabit(i, "Bad Habit " + i, PriorityLevel.MEDIUM, "");
            goodHabitsToAdd.add(goodHabit);
            badHabitsToAdd.add(badHabit);
            testHabitTracker.addHabit(goodHabit, true);
            testHabitTracker.addHabit(badHabit, false);
        }
        assertEquals(3, testHabitTracker.getGoodHabits().size());
        assertEquals(3, testHabitTracker.getBadHabits().size());

        List<Habit> goodHabits = testHabitTracker.getGoodHabits();
        for (Habit habit : goodHabitsToAdd) {
            assertTrue(goodHabits.contains(habit));
        }

        List<Habit> badHabits = testHabitTracker.getBadHabits();
        for (Habit habit : badHabitsToAdd) {
            assertTrue(badHabits.contains(habit));
        }
    }
    @Test
    public void addMultipleGoodMultipleBadHabitsNotAlternatingTest() {
        List<Habit> goodHabitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            GoodHabit goodHabit = new GoodHabit(i,"Good Habit " + i, PriorityLevel.LOW, "");
            goodHabitsToAdd.add(goodHabit);
            testHabitTracker.addHabit(goodHabit, true);
        }
        assertEquals(3, testHabitTracker.getGoodHabits().size());
        assertEquals(0, testHabitTracker.getBadHabits().size());

        List<Habit> badHabitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            BadHabit badHabit = new BadHabit(i, "Bad Habit " + i, PriorityLevel.MEDIUM, "");
            badHabitsToAdd.add(badHabit);
            testHabitTracker.addHabit(badHabit, false);
        }
        assertEquals(3, testHabitTracker.getGoodHabits().size());
        assertEquals(3, testHabitTracker.getBadHabits().size());

        List<Habit> goodHabits = testHabitTracker.getGoodHabits();
        for (Habit habit : goodHabitsToAdd) {
            assertTrue(goodHabits.contains(habit));
        }

        List<Habit> badHabits = testHabitTracker.getBadHabits();
        for (Habit habit : badHabitsToAdd) {
            assertTrue(badHabits.contains(habit));
        }
    }

    @Test
    public void removeOneGoodHabitGivenNameTest() {
        testHabitTracker.addHabit(goodA, true);
        testHabitTracker.removeHabit("Walk", true);
        assertEquals(0, testHabitTracker.getGoodHabits().size());
        assertFalse(testHabitTracker.getGoodHabits().contains(goodA));

        // For event log
        for (Event event : el) {
            log.add(event);
        }
        assertEquals(log.get(0).getDescription(), "Event log cleared.");
        assertEquals(log.get(1).getDescription(), "Walk added to list of Good Habits");
        assertEquals(log.get(2).getDescription(), "Walk removed from list of Good Habits");

    }

    @Test
    public void removeOneBadHabitGivenNameTest() {
        testHabitTracker.addHabit(badA, false);

        testHabitTracker.removeHabit("Fast Food", false);
        assertEquals(0, testHabitTracker.getBadHabits().size());
        assertFalse(testHabitTracker.getBadHabits().contains(badA));

        // For event log
        for (Event event : el) {
            log.add(event);
        }
        assertEquals(log.get(0).getDescription(), "Event log cleared.");
        assertEquals(log.get(1).getDescription(), "Fast Food added to list of Bad Habits");
        assertEquals(log.get(2).getDescription(), "Fast Food removed from list of Bad Habits");
    }


    @Test
    public void removeOneGoodHabitTest() {
        testHabitTracker.addHabit(goodA, true);

        testHabitTracker.removeHabit(goodA, true);
        assertEquals(0, testHabitTracker.getGoodHabits().size()); // todo delete?
        assertFalse(testHabitTracker.getGoodHabits().contains(goodA));
    }

    @Test
    public void removeOneBadHabitTest() {
        testHabitTracker.addHabit(badA, false);

        testHabitTracker.removeHabit(badA, false);
        assertEquals(0, testHabitTracker.getBadHabits().size()); // todo delete?
        assertFalse(testHabitTracker.getBadHabits().contains(badA));
    }

    // removeAnUnAddedGoodHabit
    // check the size is the same, check it doesn't contain the unadded habit
    // removeAnUnAddedBadHabit
    @Test
    public void removeAnUnAddedGoodHabit() {
        testHabitTracker.addHabit(goodA, true);
        assertEquals(1, testHabitTracker.getGoodHabits().size());

        testHabitTracker.removeHabit(goodB, true);
        assertEquals(1, testHabitTracker.getGoodHabits().size());
        assertFalse(testHabitTracker.getBadHabits().contains(goodB));

    }

    @Test
    public void removeAnUnAddedBadHabit() {
        testHabitTracker.addHabit(badA, false);
        assertEquals(1, testHabitTracker.getBadHabits().size());

        testHabitTracker.removeHabit(badB, false);
        assertEquals(1, testHabitTracker.getBadHabits().size());
        assertFalse(testHabitTracker.getBadHabits().contains(badB));
    }



    @Test
    public void removeGoodHabitFromBadTest() {
        testHabitTracker.addHabit(goodA, true);
        testHabitTracker.addHabit(badA, false);

        testHabitTracker.removeHabit(goodA, false);
        assertTrue(testHabitTracker.getGoodHabits().contains(goodA));
    }

    @Test
    public void removeBadHabitFromGoodTest() {
        testHabitTracker.addHabit(goodA, true);
        testHabitTracker.addHabit(badA, false);

        testHabitTracker.removeHabit(badA, true);
        assertTrue(testHabitTracker.getBadHabits().contains(badA));
    }

    @Test
    public void removeMultipleGoodHabitTest() {
        List<Habit> habitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            GoodHabit goodHabit = new GoodHabit( i,"Good Habit " + i, PriorityLevel.LOW, "");
            habitsToAdd.add(goodHabit);
            testHabitTracker.addHabit(goodHabit, true);
        }

        for (int i = 0; i < 3; i++) {
            Habit habitToRemove = habitsToAdd.get(i);
            testHabitTracker.removeHabit(habitToRemove, true);
            assertEquals(2 - i, testHabitTracker.getGoodHabits().size());
            assertFalse(testHabitTracker.getGoodHabits().contains(habitToRemove));
        }

        // For event log
        for (Event event : el) {
            log.add(event);
        }
        assertEquals("Event log cleared.", log.get(0).getDescription());
        assertEquals("Good Habit 1 added to list of Good Habits", log.get(1).getDescription());
        assertEquals( "Good Habit 2 added to list of Good Habits", log.get(2).getDescription());
        assertEquals( "Good Habit 3 added to list of Good Habits", log.get(3).getDescription());
        assertEquals( "Good Habit 1 removed from list of Good Habits", log.get(4).getDescription());
        assertEquals( "Good Habit 2 removed from list of Good Habits", log.get(5).getDescription());
        assertEquals( "Good Habit 3 removed from list of Good Habits", log.get(6).getDescription());
    }

//    @Test
//    public void removeMultipleGoodHabitGivenNameTest() {
//        List<Habit> habitsToAdd = new ArrayList<>();
//        for (int i = 1; i <= 3; i++) {
//            GoodHabit goodHabit = new GoodHabit( i,"Good Habit " + i, PriorityLevel.LOW, "");
//            habitsToAdd.add(goodHabit);
//            testHabitTracker.addHabit(goodHabit, true);
//        }
//
//        for (int i = 1; i <= 3; i++) {
//            String habitToRemove = "Good Habit " + i;
//            testHabitTracker.removeHabit(habitToRemove, true);
//            assertEquals(2 - i, testHabitTracker.getGoodHabits().size());
//            assertFalse(testHabitTracker.getGoodHabits().contains(habitToRemove));
//        }
//    }

    @Test
    public void removeMultipleBadHabitTest() {
        List<Habit> habitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            BadHabit badHabit = new BadHabit(i,"Bad Habit " + i, PriorityLevel.MEDIUM, "");
            habitsToAdd.add(badHabit);
            testHabitTracker.addHabit(badHabit, false);
        }
        for (int i = 0; i < 3; i++) {
            Habit habitToRemove = habitsToAdd.get(i);
            testHabitTracker.removeHabit(habitToRemove, false);
            assertEquals(2 - i, testHabitTracker.getBadHabits().size());
            assertFalse(testHabitTracker.getBadHabits().contains(habitToRemove));
        }
        // For event log
        for (Event event : el) {
            log.add(event);
        }
        assertEquals(log.get(0).getDescription(), "Event log cleared.");
        assertEquals(log.get(1).getDescription(), "Bad Habit 1 added to list of Bad Habits");
        assertEquals(log.get(2).getDescription(), "Bad Habit 2 added to list of Bad Habits");
        assertEquals(log.get(3).getDescription(), "Bad Habit 3 added to list of Bad Habits");
        assertEquals(log.get(4).getDescription(), "Bad Habit 1 removed from list of Bad Habits");
        assertEquals(log.get(5).getDescription(), "Bad Habit 2 removed from list of Bad Habits");
        assertEquals(log.get(6).getDescription(), "Bad Habit 3 removed from list of Bad Habits");
    }

    @Test
    public void removeMultipleHabitsAlternateTest() {
        List<Habit> goodHabitsToAdd = new ArrayList<>();
        List<Habit> badHabitsToAdd = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            GoodHabit goodHabit = new GoodHabit(i, "Good Habit " + i, PriorityLevel.MEDIUM, "");
            BadHabit badHabit = new BadHabit( i, "Bad Habit " + i, PriorityLevel.MEDIUM, "");
            goodHabitsToAdd.add(goodHabit);
            badHabitsToAdd.add(badHabit);
            testHabitTracker.addHabit(goodHabit, true);
            testHabitTracker.addHabit(badHabit, false);
        }
//        List<Habit> badHabits = testHabitTracker.getBadHabits();
        for (int i = 0; i < 3; i++) {
            Habit goodHabitToRemove = goodHabitsToAdd.get(i);
            Habit badHabitToRemove = badHabitsToAdd.get(i);

            testHabitTracker.removeHabit(goodHabitToRemove, true);
            assertEquals(2 - i, testHabitTracker.getGoodHabits().size());
            assertFalse(testHabitTracker.getGoodHabits().contains(goodHabitToRemove));

            testHabitTracker.removeHabit(badHabitToRemove, false);
            assertEquals(2 - i, testHabitTracker.getGoodHabits().size());
            assertFalse(testHabitTracker.getBadHabits().contains(badHabitToRemove));
        }

        // For event log
        for (Event event : el) {
            log.add(event);
        }
        assertEquals(log.get(0).getDescription(), "Event log cleared.");
        assertEquals("Good Habit 1 added to list of Good Habits", log.get(1).getDescription());
        assertEquals("Bad Habit 1 added to list of Bad Habits", log.get(2).getDescription());
        assertEquals("Good Habit 2 added to list of Good Habits", log.get(3).getDescription());
        assertEquals("Bad Habit 2 added to list of Bad Habits", log.get(4).getDescription());
        assertEquals("Good Habit 3 added to list of Good Habits", log.get(5).getDescription());
        assertEquals("Bad Habit 3 added to list of Bad Habits", log.get(6).getDescription());
    }


    @Test
    public void alternateAddAndRemove() {
        testHabitTracker.addHabit(goodA, true);
        testHabitTracker.addHabit(badA, false);

        testHabitTracker.addHabit(goodB, true);
        assertEquals (2, testHabitTracker.getGoodHabits().size());
        testHabitTracker.removeHabit(goodB, true);
        assertEquals (1, testHabitTracker.getGoodHabits().size());

        testHabitTracker.addHabit(badB, false);
        assertEquals (2, testHabitTracker.getBadHabits().size());
        testHabitTracker.removeHabit(badB, false);
        assertEquals (1, testHabitTracker.getBadHabits().size());

        testHabitTracker.addHabit(goodC, true);
        assertEquals (2, testHabitTracker.getGoodHabits().size());
        testHabitTracker.removeHabit(goodC, true);
        assertEquals (1, testHabitTracker.getGoodHabits().size());

        testHabitTracker.addHabit(badC, false);
        assertEquals (2, testHabitTracker.getBadHabits().size());
        testHabitTracker.removeHabit(badC, false);
        assertEquals (1, testHabitTracker.getBadHabits().size());
    }

    @Test
    public void getIndexOfTest() {
        testHabitTracker.addHabit(goodA, true);
        testHabitTracker.addHabit(goodB, true);
        testHabitTracker.addHabit(goodC, true);
        testHabitTracker.addHabit(badA, false);
        testHabitTracker.addHabit(badB, false);
        testHabitTracker.addHabit(badC, false);

        assertEquals(testHabitTracker.getIndexOf("Walk"), 0);
        assertEquals(testHabitTracker.getIndexOf("Read Books"), 1);
        assertEquals(testHabitTracker.getIndexOf("Water Plants"), 2);
        assertEquals(testHabitTracker.getIndexOf("Fast Food"), 0);
        assertEquals(testHabitTracker.getIndexOf("Social Media"), 1);
        assertEquals(testHabitTracker.getIndexOf("Candy"), 2);
        assertEquals(testHabitTracker.getIndexOf("Wrong"), -1);
    }

    @Test
    public void getHabitDetailsTest() {
        testHabitTracker.addHabit(goodA, true);
        testHabitTracker.addHabit(goodB, true);
        testHabitTracker.addHabit(badA, false);
        String expectedGoodA = testHabitTracker.getHabitDetails("Walk", true);
        String expectedGoodB = testHabitTracker.getHabitDetails("Read Books", true);
        String expectedBadB = testHabitTracker.getHabitDetails("Fast Food", false);
        assertEquals(expectedGoodA, "Name: Walk\n" + "Priority Level: LOW\n"
                + "Note: 15 minute walk every afternoon");
        assertEquals(expectedGoodB, "Name: Read Books\n" +
                "Priority Level: MEDIUM\n" +
                "Note: read a thoughtful book before sleep");
        assertEquals(expectedBadB, "Name: Fast Food\n" +
                "Priority Level: HIGH\n" +
                "Note: less fast food, more home cooked meals");
    }




    // alternateAddAndRemove: add good add bad,  alternate adding 3 good and bad and removing right away





}
