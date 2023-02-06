package model;

import org.junit.jupiter.api.Test;

import static model.PriorityLevel.LOW;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadHabitTest {

    Habit habitTest;

    @Test
    public void constructorTest() {
        habitTest = new BadHabit(1, "BadHabit1", LOW, "no notes");
        assertEquals(1, habitTest.getId());
        assertEquals("BadHabit1", habitTest.getName());
        assertEquals(LOW, habitTest.getPriorityLevel());
        assertEquals("no notes", habitTest.getNote());

    }
}