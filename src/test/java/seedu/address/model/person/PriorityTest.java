package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority((String) null));
        assertThrows(NullPointerException.class, () -> new Priority((Priority.Level) null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "INVALID";
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null priority
        assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("INVALID")); // invalid priority level
        assertFalse(Priority.isValidPriority("low priority")); // multiple words
        assertFalse(Priority.isValidPriority("123")); // numbers

        // valid priority
        assertTrue(Priority.isValidPriority("NONE"));
        assertTrue(Priority.isValidPriority("LOW"));
        assertTrue(Priority.isValidPriority("MEDIUM"));
        assertTrue(Priority.isValidPriority("HIGH"));
        // case insensitive
        assertTrue(Priority.isValidPriority("none"));
        assertTrue(Priority.isValidPriority("low"));
        assertTrue(Priority.isValidPriority("medium"));
        assertTrue(Priority.isValidPriority("high"));
        assertTrue(Priority.isValidPriority("None"));
        assertTrue(Priority.isValidPriority("Low"));
        assertTrue(Priority.isValidPriority("Medium"));
        assertTrue(Priority.isValidPriority("High"));
    }

    @Test
    public void constructor_validPriority_success() {
        // Test string constructor
        Priority priority = new Priority("HIGH");
        assertEquals(Priority.Level.HIGH, priority.value);
        // Test enum constructor
        Priority priority2 = new Priority(Priority.Level.MEDIUM);
        assertEquals(Priority.Level.MEDIUM, priority2.value);
    }

    @Test
    public void getValue() {
        assertEquals("NONE", new Priority("NONE").getValue());
        assertEquals("LOW", new Priority("LOW").getValue());
        assertEquals("MEDIUM", new Priority("MEDIUM").getValue());
        assertEquals("HIGH", new Priority("HIGH").getValue());
    }

    @Test
    public void toString_test() {
        assertEquals("NONE", new Priority("NONE").toString());
        assertEquals("LOW", new Priority("LOW").toString());
        assertEquals("MEDIUM", new Priority("MEDIUM").toString());
        assertEquals("HIGH", new Priority("HIGH").toString());
    }

    @Test
    public void equals() {
        Priority priority = new Priority("HIGH");

        // same values -> returns true
        assertTrue(priority.equals(new Priority("HIGH")));
        assertTrue(priority.equals(new Priority(Priority.Level.HIGH)));

        // same object -> returns true
        assertTrue(priority.equals(priority));

        // null -> returns false
        assertFalse(priority.equals(null));

        // different type -> returns false
        assertFalse(priority.equals(5.0f));

        // different values -> returns false
        assertFalse(priority.equals(new Priority("LOW")));
    }

    @Test
    public void hashCode_test() {
        Priority priority1 = new Priority("HIGH");
        Priority priority2 = new Priority("HIGH");
        Priority priority3 = new Priority("LOW");

        // same priority -> same hash code
        assertEquals(priority1.hashCode(), priority2.hashCode());
        // different priority -> different hash code (likely)
        assertFalse(priority1.hashCode() == priority3.hashCode());
    }
}
