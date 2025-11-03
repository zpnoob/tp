package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 4 numbers
        assertFalse(Phone.isValidPhone("123")); // exactly 3 numbers - now invalid (need 4-17)
        assertFalse(Phone.isValidPhone("123456789012345678")); // 18 digits - too long
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("9111")); // exactly 4 numbers - minimum
        assertTrue(Phone.isValidPhone("93121534")); // 8 digits
        assertTrue(Phone.isValidPhone("12429384203312")); // 14 digits
        assertTrue(Phone.isValidPhone("12345678901234567")); // 17 digits - maximum
        assertTrue(Phone.isValidPhone("9312 1534")); // spaces within digits - now valid
        assertTrue(Phone.isValidPhone("91 23 45 67")); // multiple spaces - now valid
    }

    @Test
    public void constructor_phoneWithSpaces_stripsSpacesInStoredValue() {
        Phone phone = new Phone("9123 45 67");
        // Spaces should be stripped when storing
        assertTrue(phone.value.equals("91234567"));
        assertFalse(phone.value.contains(" "));
    }

    @Test
    public void constructor_phoneWithMultipleSpaces_stripsAllSpaces() {
        Phone phone = new Phone("91  23  45  67");
        // All spaces should be stripped
        assertTrue(phone.value.equals("91234567"));
        assertFalse(phone.value.contains(" "));
    }

    @Test
    public void isValidPhone_edgeCasesForLength_validatesCorrectly() {
        // Test minimum length (4 digits)
        assertTrue(Phone.isValidPhone("1234"));
        assertFalse(Phone.isValidPhone("123"));

        // Test maximum length (17 digits)
        assertTrue(Phone.isValidPhone("12345678901234567"));
        assertFalse(Phone.isValidPhone("123456789012345678"));

        // Test with spaces that result in valid digit count
        assertTrue(Phone.isValidPhone("1 2 3 4")); // 4 digits with spaces
        assertTrue(Phone.isValidPhone("1234 5678 9012 3456 7")); // 17 digits with spaces
        assertFalse(Phone.isValidPhone("1234 5678 9012 3456 78")); // 18 digits with spaces
    }

    @Test
    public void isValidPhone_internationalFormats_validatesCorrectly() {
        // Common international phone number formats with spaces
        assertTrue(Phone.isValidPhone("6591234567")); // Singapore without spaces
        assertTrue(Phone.isValidPhone("65 9123 4567")); // Singapore with spaces
        assertTrue(Phone.isValidPhone("1 234 567 8901")); // US format with spaces
        assertTrue(Phone.isValidPhone("44 20 1234 5678")); // UK format with spaces
        assertTrue(Phone.isValidPhone("86 138 0013 8000")); // China format with spaces
    }

    @Test
    public void equals() {
        Phone phone = new Phone("9999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("9999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("9995")));
    }
}
