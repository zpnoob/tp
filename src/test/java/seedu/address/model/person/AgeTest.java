package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "abc";
        assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));

        // More than 3 digits after removing leading zeros
        assertThrows(IllegalArgumentException.class, () -> new Age("1234"));

        // Below minimum age (10) - even with leading zeros
        assertThrows(IllegalArgumentException.class, () -> new Age("5"));
        assertThrows(IllegalArgumentException.class, () -> new Age("9"));
        assertThrows(IllegalArgumentException.class, () -> new Age("0"));
        assertThrows(IllegalArgumentException.class, () -> new Age("00005")); // becomes 5 after normalization

        // Above maximum age (120)
        assertThrows(IllegalArgumentException.class, () -> new Age("121"));
        assertThrows(IllegalArgumentException.class, () -> new Age("150"));
        assertThrows(IllegalArgumentException.class, () -> new Age("999"));
    }

    @Test
    public void constructor_leadingZeros_removesLeadingZeros() {
        // Single leading zero - two digit result
        Age age1 = new Age("020");
        assertEquals("20", age1.value);

        // Multiple leading zeros - 4 digits becomes 2 digits
        Age age2 = new Age("0020");
        assertEquals("20", age2.value);

        // Single leading zero - minimum valid age
        Age age3 = new Age("010");
        assertEquals("10", age3.value);

        // Multiple leading zeros - 4 digits becomes minimum valid age
        Age age4 = new Age("0010");
        assertEquals("10", age4.value);

        // All zeros except last digit - within valid range
        Age age5 = new Age("015");
        assertEquals("15", age5.value);

        // No leading zeros - should remain unchanged
        Age age6 = new Age("100");
        assertEquals("100", age6.value);

        Age age7 = new Age("25");
        assertEquals("25", age7.value);

        // Maximum valid age
        Age age8 = new Age("120");
        assertEquals("120", age8.value);

        // Minimum valid age
        Age age9 = new Age("10");
        assertEquals("10", age9.value);
    }

    @Test
    public void isValidAge() {
        // null age
        assertThrows(NullPointerException.class, () -> Age.isValidAge(null));

        // invalid ages - non-numeric or wrong format
        assertFalse(Age.isValidAge("abc")); // non-numeric
        assertFalse(Age.isValidAge("12.5")); // decimal
        assertFalse(Age.isValidAge("-5")); // negative
        assertFalse(Age.isValidAge("20a")); // contains letter
        assertFalse(Age.isValidAge("1234")); // more than 3 digits
        assertFalse(Age.isValidAge("a20")); // starts with letter

        // invalid ages - outside valid range (10-120)
        assertFalse(Age.isValidAge("0")); // below minimum
        assertFalse(Age.isValidAge("5")); // below minimum
        assertFalse(Age.isValidAge("9")); // below minimum
        assertFalse(Age.isValidAge("121")); // above maximum
        assertFalse(Age.isValidAge("150")); // above maximum
        assertFalse(Age.isValidAge("999")); // above maximum

        // valid ages
        assertTrue(Age.isValidAge("")); // empty string
        assertTrue(Age.isValidAge("10")); // minimum valid age
        assertTrue(Age.isValidAge("25")); // typical age
        assertTrue(Age.isValidAge("65")); // typical age
        assertTrue(Age.isValidAge("100")); // three digits
        assertTrue(Age.isValidAge("120")); // maximum valid age
        assertTrue(Age.isValidAge("020")); // with leading zeros (valid after parsing)
        assertTrue(Age.isValidAge("010")); // with leading zeros (valid after parsing)
    }

    @Test
    public void equals() {
        Age age = new Age("25");

        // same values -> returns true
        assertTrue(age.equals(new Age("25")));

        // same object -> returns true
        assertTrue(age.equals(age));

        // null -> returns false
        assertFalse(age.equals(null));

        // different types -> returns false
        assertFalse(age.equals(5.0f));

        // different values -> returns false
        assertFalse(age.equals(new Age("30")));

        // leading zeros removed - should be equal
        Age ageWithLeadingZero = new Age("025");
        assertTrue(age.equals(ageWithLeadingZero));
        assertTrue(ageWithLeadingZero.equals(age));

        // another test with leading zeros
        Age age1 = new Age("010");
        Age age2 = new Age("10");
        assertTrue(age1.equals(age2));
        assertTrue(age2.equals(age1));
    }

    @Test
    public void hashCode_equalAges_sameHashCode() {
        Age age1 = new Age("25");
        Age age2 = new Age("025");

        // Ages with leading zeros should be equal and have same hash code
        assertEquals(age1, age2);
        assertEquals(age1.hashCode(), age2.hashCode());

        Age age3 = new Age("010");
        Age age4 = new Age("10");

        assertEquals(age3, age4);
        assertEquals(age3.hashCode(), age4.hashCode());
    }

    @Test
    public void toString_validAge_returnsCorrectString() {
        assertEquals("25", new Age("25").toString());
        assertEquals("100", new Age("100").toString());
        assertEquals("", new Age("").toString());
        assertEquals("10", new Age("10").toString());
        assertEquals("120", new Age("120").toString());

        // Leading zeros removed in toString
        assertEquals("20", new Age("020").toString());
        assertEquals("10", new Age("010").toString());
        assertEquals("15", new Age("015").toString());
    }
}

