package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    @Test
    public void isNonZeroUnsignedInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


    //---------------- Tests for containsSubstringIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for substring: null, empty
     * The three test cases below test one invalid input at a time.
     */

    @Test
    public void containsSubstringIgnoreCase_nullSubstring_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                StringUtil.containsSubstringIgnoreCase("test string", null));
    }

    @Test
    public void containsSubstringIgnoreCase_emptySubstring_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Substring parameter cannot be empty", ()
            -> StringUtil.containsSubstringIgnoreCase("test string", "  "));
    }

    @Test
    public void containsSubstringIgnoreCase_nullString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                StringUtil.containsSubstringIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for substring:
     *   - partial match (beginning, middle, end of string)
     *   - exact match
     *   - substring with symbols/numbers
     *   - substring with spaces
     *
     * Possible scenarios returning true:
     *   - case-insensitive match
     *   - substring trimmed before matching
     *   - partial match in email/special formats
     *
     * Possible scenarios returning false:
     *   - empty string being searched
     *   - substring not found
     *
     * The test method below verifies the above scenarios.
     */

    @Test
    public void containsSubstringIgnoreCase_validInputs_correctResult() {

        // Empty string
        assertFalse(StringUtil.containsSubstringIgnoreCase("", "abc"));
        assertFalse(StringUtil.containsSubstringIgnoreCase("    ", "123"));

        // Substring not found
        assertFalse(StringUtil.containsSubstringIgnoreCase("hello world", "xyz"));

        // Partial matches - case insensitive
        assertTrue(StringUtil.containsSubstringIgnoreCase("Hello World", "hel")); // Beginning
        assertTrue(StringUtil.containsSubstringIgnoreCase("Hello World", "Wor")); // Middle
        assertTrue(StringUtil.containsSubstringIgnoreCase("Hello World", "RLD")); // End

        // Exact match - case insensitive
        assertTrue(StringUtil.containsSubstringIgnoreCase("Hello", "hello"));

        // Substring with leading/trailing spaces (trimmed)
        assertTrue(StringUtil.containsSubstringIgnoreCase("hello world", "  world  "));

        // Special characters and numbers
        assertTrue(StringUtil.containsSubstringIgnoreCase("test@123", "@123"));
        assertTrue(StringUtil.containsSubstringIgnoreCase("user@example.com", "example"));

        // Real use case: email partial match
        assertTrue(StringUtil.containsSubstringIgnoreCase("lidavid@example.com", "lidavid"));
        assertTrue(StringUtil.containsSubstringIgnoreCase("lidavid@example.com", "david"));

        // Substring spanning multiple words/characters
        assertTrue(StringUtil.containsSubstringIgnoreCase("abc def ghi", "c d"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

}
