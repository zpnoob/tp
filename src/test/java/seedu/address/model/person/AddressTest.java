package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(" "));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress("  ")); // multiple spaces only
        assertFalse(Address.isValidAddress(" 123 Main St")); // starts with space
        assertFalse(Address.isValidAddress("!Invalid Address")); // starts with exclamation
        assertFalse(Address.isValidAddress("#12-34")); // starts with hash (special char)
        assertFalse(Address.isValidAddress("-Main St")); // starts with hyphen
        assertFalse(Address.isValidAddress(",123 Street")); // starts with comma
        assertFalse(Address.isValidAddress("123 Main St*")); // contains asterisk
        assertFalse(Address.isValidAddress("123 Main St!")); // contains exclamation
        assertFalse(Address.isValidAddress("123@Main St")); // contains at symbol
        assertFalse(Address.isValidAddress("123 Main St;")); // contains semicolon
        assertFalse(Address.isValidAddress("123 (Main) St")); // contains parentheses
        assertFalse(Address.isValidAddress("123 [Main] St")); // contains brackets

        // valid addresses
        assertTrue(Address.isValidAddress("")); // empty string is valid
        assertTrue(Address.isValidAddress("123 Main Street")); // basic address
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355")); // with comma and hash
        assertTrue(Address.isValidAddress("21 Heng Mui Keng Terrace")); // multiple words
        assertTrue(Address.isValidAddress("A123 Smith St.")); // starts with letter, contains period
        assertTrue(Address.isValidAddress("45A Orchard Road")); // letter after number
        assertTrue(Address.isValidAddress("Unit 5, 789 Orchard Road")); // with comma
        assertTrue(Address.isValidAddress("Building A-12, Level 3")); // with hyphen
        assertTrue(Address.isValidAddress("123/456 Main St")); // with forward slash
        assertTrue(Address.isValidAddress("Blk 123, Serangoon Gardens Street 26, #12-345")); // Singapore format
        assertTrue(Address.isValidAddress("1 Example St.")); // with period
        assertTrue(Address.isValidAddress("1")); // single digit
        assertTrue(Address.isValidAddress("A")); // single letter
        assertTrue(Address.isValidAddress("123 21st Avenue")); // with ordinal number
        assertTrue(Address.isValidAddress(
            "Leng Inc, 1234 Market St, San Francisco CA 2349879, USA")); // long address with commas
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address")));
    }
}
