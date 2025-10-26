package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCUPATION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

public class ArgumentMultimapTest {

    @Test
    public void verifyNoDuplicatePrefixesFor_noDuplicates_doesNotThrow() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, "Alice");
        assertDoesNotThrow(() -> map.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_OCCUPATION));
    }

    @Test
    public void verifyNoDuplicatePrefixesFor_duplicateOccupation_throwsSpecialOccupationMessage() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_OCCUPATION, "Eng1");
        map.put(PREFIX_OCCUPATION, "Eng2");

        ParseException ex = assertThrows(ParseException.class, () -> map.verifyNoDuplicatePrefixesFor(
            PREFIX_OCCUPATION));

        assertEquals("Only one o/ (occupation) input is allowed. Remove the extra occurrences and try again.",
                ex.getMessage());
    }

    @Test
    public void verifyNoDuplicatePrefixesFor_duplicateName_throwsMessageWithName() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, "Alice");
        map.put(PREFIX_NAME, "Bob");

        ParseException ex = assertThrows(ParseException.class, () -> map.verifyNoDuplicatePrefixesFor(
            PREFIX_NAME));

        String expected = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME);
        assertEquals(expected, ex.getMessage());
    }
}
