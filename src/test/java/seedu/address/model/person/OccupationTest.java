package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OccupationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Occupation(null));
    }

    @Test
    public void constructor_invalidOccupation_throwsIllegalArgumentException() {
        String invalidOccupation = "123"; // contains numbers
        assertThrows(IllegalArgumentException.class, () -> new Occupation(invalidOccupation));
    }

    @Test
    public void constructor_emptyString_success() {
        // Empty string is allowed - occupation is optional
        Occupation occupation = new Occupation("");
        assertEquals("", occupation.value);
    }

    @Test
    public void constructor_whitespaceOnly_success() {
        // Whitespace-only string should be trimmed to empty and allowed
        Occupation occupation = new Occupation("   ");
        assertEquals("", occupation.value);
    }

    @Test
    public void isValidOccupation() {
        // null occupation - returns false instead of throwing exception
        assertFalse(Occupation.isValidOccupation(null));

        // invalid occupations - contains numbers
        assertFalse(Occupation.isValidOccupation("Engineer123")); // contains numbers
        assertFalse(Occupation.isValidOccupation("123")); // only numbers
        assertFalse(Occupation.isValidOccupation("1st Engineer")); // starts with number
        assertFalse(Occupation.isValidOccupation("Software Engineer 2")); // ends with number

        // invalid occupations - contains special characters
        assertFalse(Occupation.isValidOccupation("Software-Engineer")); // contains hyphen
        assertFalse(Occupation.isValidOccupation("Software/Engineer")); // contains forward slash
        assertFalse(Occupation.isValidOccupation("Software_Engineer")); // contains underscore
        assertFalse(Occupation.isValidOccupation("Software.Engineer")); // contains period
        assertFalse(Occupation.isValidOccupation("Software@Engineer")); // contains at symbol
        assertFalse(Occupation.isValidOccupation("Software!Engineer")); // contains exclamation
        assertFalse(Occupation.isValidOccupation("Software#Engineer")); // contains hash
        assertFalse(Occupation.isValidOccupation("Software$Engineer")); // contains dollar sign
        assertFalse(Occupation.isValidOccupation("Software&Engineer")); // contains ampersand

        // invalid occupations - spacing issues
        assertFalse(Occupation.isValidOccupation(" Engineer")); // leading space
        assertFalse(Occupation.isValidOccupation("Engineer ")); // trailing space
        assertFalse(Occupation.isValidOccupation("Software  Engineer")); // consecutive spaces
        assertFalse(Occupation.isValidOccupation("   Engineer   ")); // multiple leading/trailing spaces
        assertFalse(Occupation.isValidOccupation(" ")); // only space
        // Note: empty string returns false from isValidOccupation, but constructor handles it specially
        assertFalse(Occupation.isValidOccupation("")); // empty string

        // valid occupations - single word
        assertTrue(Occupation.isValidOccupation("Engineer")); // single word
        assertTrue(Occupation.isValidOccupation("Doctor")); // single word
        assertTrue(Occupation.isValidOccupation("Teacher")); // single word
        assertTrue(Occupation.isValidOccupation("Chef")); // single word
        assertTrue(Occupation.isValidOccupation("Designer")); // single word
        assertTrue(Occupation.isValidOccupation("Student")); // single word

        // valid occupations - multiple words
        assertTrue(Occupation.isValidOccupation("Software Engineer")); // two words
        assertTrue(Occupation.isValidOccupation("Sales Manager")); // two words
        assertTrue(Occupation.isValidOccupation("Product Manager")); // two words
        assertTrue(Occupation.isValidOccupation("Data Scientist")); // two words
        assertTrue(Occupation.isValidOccupation("Business Analyst")); // two words
        assertTrue(Occupation.isValidOccupation("Project Manager")); // two words

        // valid occupations - more than two words
        assertTrue(Occupation.isValidOccupation("Senior Software Engineer")); // three words
        assertTrue(Occupation.isValidOccupation("Chief Executive Officer")); // three words
        assertTrue(Occupation.isValidOccupation("Head of Human Resources")); // four words
        assertTrue(Occupation.isValidOccupation("Senior Vice President of Sales")); // five words

        // valid occupations - case variations
        assertTrue(Occupation.isValidOccupation("software engineer")); // lowercase
        assertTrue(Occupation.isValidOccupation("SOFTWARE ENGINEER")); // uppercase
        assertTrue(Occupation.isValidOccupation("SoFtWaRe EnGiNeEr")); // mixed case
    }

    @Test
    public void equals() {
        Occupation occupation = new Occupation("Software Engineer");

        // same values -> returns true
        assertTrue(occupation.equals(new Occupation("Software Engineer")));

        // same object -> returns true
        assertTrue(occupation.equals(occupation));

        // null -> returns false
        assertFalse(occupation.equals(null));

        // different types -> returns false
        assertFalse(occupation.equals(5.0f));

        // different values -> returns false
        assertFalse(occupation.equals(new Occupation("Doctor")));
    }

    @Test
    public void hashCode_sameValue_equal() {
        Occupation occupation1 = new Occupation("Software Engineer");
        Occupation occupation2 = new Occupation("Software Engineer");
        assertEquals(occupation1.hashCode(), occupation2.hashCode());
    }

    @Test
    public void hashCode_differentValue_notEqual() {
        Occupation occupation1 = new Occupation("Software Engineer");
        Occupation occupation2 = new Occupation("Doctor");
        assertFalse(occupation1.hashCode() == occupation2.hashCode());
    }

    @Test
    public void toString_validOccupation_returnsCorrectString() {
        Occupation occupation = new Occupation("Software Engineer");
        assertEquals("Software Engineer", occupation.toString());
    }

    @Test
    public void toString_emptyOccupation_returnsEmptyString() {
        Occupation occupation = new Occupation("");
        assertEquals("", occupation.toString());
    }
}
