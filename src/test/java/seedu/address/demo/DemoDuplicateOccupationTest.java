package seedu.address.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Demo test to show the ParseException message when duplicate occupation prefixes are used.
 */
public class DemoDuplicateOccupationTest {

    @Test
    public void demoDuplicateOccupation_showsFriendlyMessage() {
        AddressBookParser parser = new AddressBookParser();
        String input = "add n/Zamien p/123 o/Doctor o/Teacher";
        try {
            parser.parseCommand(input);
            fail("Expected ParseException due to duplicate occupation prefixes");
        } catch (ParseException e) {
            System.out.println("ParseException message: " + e.getMessage());
            assertTrue(e.getMessage().toLowerCase().contains("o/ (occupation")
                    || e.getMessage().toLowerCase().contains("occupation"));
        }
    }
}
