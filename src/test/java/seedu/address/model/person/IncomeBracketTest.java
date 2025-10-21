package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IncomeBracketTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IncomeBracket((String) null));
        assertThrows(NullPointerException.class, () -> new IncomeBracket((IncomeBracket.Level) null));
    }

    @Test
    public void constructor_invalidIncomeBracket_throwsIllegalArgumentException() {
        String invalidIncomeBracket = "";
        assertThrows(IllegalArgumentException.class, () -> new IncomeBracket(invalidIncomeBracket));
    }

    @Test
    public void isValidIncomeBracket() {
        // null income bracket
        assertThrows(NullPointerException.class, () -> IncomeBracket.isValidIncomeBracket(null));

        // invalid income brackets
        assertFalse(IncomeBracket.isValidIncomeBracket("")); // empty string
        assertFalse(IncomeBracket.isValidIncomeBracket(" ")); // spaces only
        assertFalse(IncomeBracket.isValidIncomeBracket("rich")); // invalid value
        assertFalse(IncomeBracket.isValidIncomeBracket("poor")); // invalid value
        assertFalse(IncomeBracket.isValidIncomeBracket("very high")); // invalid value

        // valid income brackets
        assertTrue(IncomeBracket.isValidIncomeBracket("low"));
        assertTrue(IncomeBracket.isValidIncomeBracket("middle"));
        assertTrue(IncomeBracket.isValidIncomeBracket("high"));
        assertTrue(IncomeBracket.isValidIncomeBracket("LOW"));
        assertTrue(IncomeBracket.isValidIncomeBracket("MIDDLE"));
        assertTrue(IncomeBracket.isValidIncomeBracket("HIGH"));
        assertTrue(IncomeBracket.isValidIncomeBracket("Low"));
        assertTrue(IncomeBracket.isValidIncomeBracket("Middle"));
        assertTrue(IncomeBracket.isValidIncomeBracket("High"));
        assertTrue(IncomeBracket.isValidIncomeBracket(" low ")); // with spaces
        assertTrue(IncomeBracket.isValidIncomeBracket(" MIDDLE ")); // with spaces
        assertTrue(IncomeBracket.isValidIncomeBracket(" High ")); // with spaces
    }

    @Test
    public void constructor_validIncomeBracket_success() {
        // valid income brackets
        IncomeBracket low = new IncomeBracket("low");
        assertEquals(IncomeBracket.Level.LOW, low.value);
        assertEquals("Low Income", low.getValue());

        IncomeBracket middle = new IncomeBracket("middle");
        assertEquals(IncomeBracket.Level.MIDDLE, middle.value);
        assertEquals("Middle Income", middle.getValue());

        IncomeBracket high = new IncomeBracket("high");
        assertEquals(IncomeBracket.Level.HIGH, high.value);
        assertEquals("High Income", high.getValue());

        // case insensitive
        IncomeBracket upperLow = new IncomeBracket("LOW");
        assertEquals(IncomeBracket.Level.LOW, upperLow.value);

        // with spaces
        IncomeBracket spacedMiddle = new IncomeBracket(" middle ");
        assertEquals(IncomeBracket.Level.MIDDLE, spacedMiddle.value);
    }

    @Test
    public void constructor_validLevel_success() {
        IncomeBracket low = new IncomeBracket(IncomeBracket.Level.LOW);
        assertEquals(IncomeBracket.Level.LOW, low.value);

        IncomeBracket middle = new IncomeBracket(IncomeBracket.Level.MIDDLE);
        assertEquals(IncomeBracket.Level.MIDDLE, middle.value);

        IncomeBracket high = new IncomeBracket(IncomeBracket.Level.HIGH);
        assertEquals(IncomeBracket.Level.HIGH, high.value);
    }

    @Test
    public void getValue() {
        assertEquals("Low Income", new IncomeBracket("low").getValue());
        assertEquals("Middle Income", new IncomeBracket("middle").getValue());
        assertEquals("High Income", new IncomeBracket("high").getValue());
    }

    @Test
    public void toString_test() {
        assertEquals("Low Income", new IncomeBracket(IncomeBracket.Level.LOW).toString());
        assertEquals("Middle Income", new IncomeBracket(IncomeBracket.Level.MIDDLE).toString());
        assertEquals("High Income", new IncomeBracket(IncomeBracket.Level.HIGH).toString());
    }

    @Test
    public void equals() {
        IncomeBracket low = new IncomeBracket("low");

        // same values -> returns true
        assertTrue(low.equals(new IncomeBracket("low")));
        assertTrue(low.equals(new IncomeBracket("LOW"))); // case insensitive
        assertTrue(low.equals(new IncomeBracket(IncomeBracket.Level.LOW)));

        // same object -> returns true
        assertTrue(low.equals(low));

        // null -> returns false
        assertFalse(low.equals(null));

        // different types -> returns false
        assertFalse(low.equals(5.0f));

        // different values -> returns false
        assertFalse(low.equals(new IncomeBracket("high")));
        assertFalse(low.equals(new IncomeBracket("middle")));
    }

    @Test
    public void hashCode_test() {
        IncomeBracket low1 = new IncomeBracket("low");
        IncomeBracket low2 = new IncomeBracket("LOW");
        IncomeBracket high = new IncomeBracket("high");

        // same values should have same hash code
        assertEquals(low1.hashCode(), low2.hashCode());

        // different values should have different hash codes (though not guaranteed)
        // This is just a good practice test
        assertTrue(low1.hashCode() != high.hashCode());
    }

    @Test
    public void levelEnum_displayValues() {
        assertEquals("Low Income", IncomeBracket.Level.LOW.getDisplayValue());
        assertEquals("Middle Income", IncomeBracket.Level.MIDDLE.getDisplayValue());
        assertEquals("High Income", IncomeBracket.Level.HIGH.getDisplayValue());

        assertEquals("Low Income", IncomeBracket.Level.LOW.toString());
        assertEquals("Middle Income", IncomeBracket.Level.MIDDLE.toString());
        assertEquals("High Income", IncomeBracket.Level.HIGH.toString());
    }
}
