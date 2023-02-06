package persistence;

import model.Habit;
import model.PriorityLevel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
// extra test that could be used by JsonReaderTest, JsonWriterTest, or both
// currently only Json reader needs it
public class JsonTest {



    protected void checkHabit(Habit habitExpected, Habit habitActual) {

        assertEquals(habitExpected.getId(), habitActual.getId());
        assertEquals(habitExpected.getName(), habitActual.getName());
        assertEquals(habitExpected.getPriorityLevel(), habitActual.getPriorityLevel());
        assertEquals(habitExpected.getNote(), habitActual.getNote());
    }
}
