package model;

import org.junit.jupiter.api.Test;

import static model.PriorityLevel.MEDIUM;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoodHabitTest {
    Habit habitTest;

    @Test
    public void constructorTest() {
        habitTest = new GoodHabit(1, "GoodHabit", MEDIUM, "no notes");
        assertEquals(1, habitTest.getId());
        assertEquals("GoodHabit", habitTest.getName());
        assertEquals(MEDIUM, habitTest.getPriorityLevel());
        assertEquals("no notes", habitTest.getNote());

      

    }


}
