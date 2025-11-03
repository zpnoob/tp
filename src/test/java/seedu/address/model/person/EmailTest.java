package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Email(" "));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertTrue(Email.isValidEmail("")); // empty string - now valid
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Email.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(Email.isValidEmail("peter..jack@example.com")); // local part has two consecutive periods - invalid
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Email.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Email.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.c")); // top level domain has less than two chars

        // consecutive periods - invalid
        assertFalse(Email.isValidEmail("test..user@example.com")); // consecutive periods
        assertFalse(Email.isValidEmail("a...b@test.com")); // multiple consecutive periods

        // consecutive other special characters - valid (hyphens, plus, underscores)
        assertTrue(Email.isValidEmail("user--name@example.com")); // consecutive hyphens - valid
        assertTrue(Email.isValidEmail("test++tag@example.com")); // consecutive plus signs - valid
        assertTrue(Email.isValidEmail("user__test@example.com")); // consecutive underscores - valid

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(Email.isValidEmail("PeterJack.1190@example.com")); // period in local part
        assertTrue(Email.isValidEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(Email.isValidEmail("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(Email.isValidEmail("a@bc")); // minimal
        assertTrue(Email.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Email.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Email.isValidEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Email.isValidEmail("e1234567@u.nus.edu")); // more than one period in domain

        // email length validation - testing 100 character limit
        // Invalid: emails exceeding 100 characters (must be > 100)
        assertFalse(Email.isValidEmail("a".repeat(50) + "@" + "b".repeat(50) + ".com"));
        assertFalse(Email.isValidEmail("a".repeat(95) + "@bc.de")); // 95+1+5 = 101 chars - too long
        assertFalse(Email.isValidEmail("abc@" + "d".repeat(97) + ".com")); // 3+1+97+4 = 105 chars - too long
        assertFalse(Email.isValidEmail("user@" + "x".repeat(200) + ".com")); // way too long
        assertFalse(Email.isValidEmail("x".repeat(60) + "@" + "y".repeat(60) + ".com"));

        // Valid: emails at or under 100 characters (must be <= 100)
        assertTrue(Email.isValidEmail("a".repeat(94) + "@bc.de")); // 94+1+5 = 100 chars - exactly at limit
        assertTrue(Email.isValidEmail("a".repeat(50) + "@" + "b".repeat(45) + ".com"));
        assertTrue(Email.isValidEmail("test@example.com")); // 16 chars - well under limit
        assertTrue(Email.isValidEmail("verylongemailaddress@verylongdomainname.com")); // 43 chars - valid
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email")));
    }
}
