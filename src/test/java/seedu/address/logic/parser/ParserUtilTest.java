package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.LastContactedDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.DncTag;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = "invalid";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRIORITY = "INVALID";
    private static final String INVALID_AGE = "abc";
    private static final String INVALID_DATE = "2025/10/21"; // wrong format
    private static final String INVALID_DATE_2 = "2025-13-21"; // invalid month

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_PRIORITY = "HIGH";
    private static final String VALID_AGE = "25";
    private static final String VALID_DATE = "2025-10-21";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseName_validValueWithMultipleSpaces_returnsNormalisedName() throws Exception {
        String nameWithMultipleSpaces = "Rachel   Walker";
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithMultipleSpaces));
    }

    @Test
    public void parseName_validValueWithLeadingTrailingAndInternalSpaces_returnsNormalisedName() throws Exception {
        String nameWithExtraSpaces = "  Rachel    Walker  ";
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithExtraSpaces));
    }

    @Test
    public void parseName_validValueWithTabsAndNewlines_returnsNormalisedName() throws Exception {
        String nameWithMixedWhitespace = "Rachel\t\t\nWalker";
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithMixedWhitespace));
    }

    @Test
    public void parseName_validValueWithMultipleWordsAndSpaces_returnsNormalisedName() throws Exception {
        String nameWithMultipleSpaces = "John   Paul    George   Ringo";
        Name expectedName = new Name("John Paul George Ringo");
        assertEquals(expectedName, ParserUtil.parseName(nameWithMultipleSpaces));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }



    @Test
    public void parseAddress_emptyValue_returnsEmptyAddress() throws Exception {
        Address expectedAddress = new Address("");
        assertEquals(expectedAddress, ParserUtil.parseAddress(""));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseAddress_validValueWithMultipleSpaces_returnsNormalisedAddress() throws Exception {
        String addressWithMultipleSpaces = "123   Main   Street   #0505";
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithMultipleSpaces));
    }

    @Test
    public void parseAddress_validValueWithLeadingTrailingAndInternalSpaces_returnsNormalisedAddress()
            throws Exception {
        String addressWithExtraSpaces = "  123    Main    Street    #0505  ";
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithExtraSpaces));
    }

    @Test
    public void parseAddress_validValueWithTabsAndNewlines_returnsNormalisedAddress() throws Exception {
        String addressWithMixedWhitespace = "123\t\tMain\n\nStreet\t#0505";
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithMixedWhitespace));
    }

    @Test
    public void parseAddress_validValueWithComplexWhitespace_returnsNormalisedAddress() throws Exception {
        String addressWithComplexWhitespace = "  Blk   456,   Den    Road,    #01-355  ";
        Address expectedAddress = new Address("Blk 456, Den Road, #01-355");
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithComplexWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTag_dncTagExactCase_returnsDncTag() throws Exception {
        Tag result = ParserUtil.parseTag("Do Not Call");
        assertTrue(result instanceof DncTag);
        assertEquals("Do Not Call", result.tagName);
    }

    @Test
    public void parseTag_dncTagLowercase_returnsDncTag() throws Exception {
        Tag result = ParserUtil.parseTag("do not call");
        assertTrue(result instanceof DncTag);
        assertEquals("Do Not Call", result.tagName);
    }

    @Test
    public void parseTag_dncTagUppercase_returnsDncTag() throws Exception {
        Tag result = ParserUtil.parseTag("DO NOT CALL");
        assertTrue(result instanceof DncTag);
        assertEquals("Do Not Call", result.tagName);
    }

    @Test
    public void parseTag_dncTagMixedCase_returnsDncTag() throws Exception {
        Tag result = ParserUtil.parseTag("Do NoT cAlL");
        assertTrue(result instanceof DncTag);
        assertEquals("Do Not Call", result.tagName);
    }

    @Test
    public void parseTag_dncTagWithWhitespace_returnsDncTag() throws Exception {
        Tag result = ParserUtil.parseTag("  do not call  ");
        assertTrue(result instanceof DncTag);
        assertEquals("Do Not Call", result.tagName);
    }

    @Test
    public void parseTag_dncTagWithMultipleSpaces_returnsDncTag() throws Exception {
        Tag result = ParserUtil.parseTag("  do   not     call  ");
        assertTrue(result instanceof DncTag);
        assertEquals("Do Not Call", result.tagName);
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parsePriority_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePriority(null));
    }

    @Test
    public void parsePriority_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePriority(INVALID_PRIORITY));
        assertThrows(ParseException.class, () -> ParserUtil.parsePriority(""));
        assertThrows(ParseException.class, () -> ParserUtil.parsePriority(" "));
        assertThrows(ParseException.class, () -> ParserUtil.parsePriority("invalid"));
    }

    @Test
    public void parsePriority_validValueWithoutWhitespace_returnsPriority() throws Exception {
        Priority expectedPriority = new Priority(VALID_PRIORITY);
        assertEquals(expectedPriority, ParserUtil.parsePriority(VALID_PRIORITY));
    }

    @Test
    public void parsePriority_validValueWithWhitespace_returnsTrimmedPriority() throws Exception {
        String priorityWithWhitespace = "  " + VALID_PRIORITY + "  ";
        Priority expectedPriority = new Priority(VALID_PRIORITY);
        assertEquals(expectedPriority, ParserUtil.parsePriority(priorityWithWhitespace));
    }

    @Test
    public void parsePriority_validValuesCaseInsensitive_returnsPriority() throws Exception {
        // Test all valid priority levels with different cases
        assertEquals(new Priority("NONE"), ParserUtil.parsePriority("NONE"));
        assertEquals(new Priority("NONE"), ParserUtil.parsePriority("none"));
        assertEquals(new Priority("NONE"), ParserUtil.parsePriority("None"));

        assertEquals(new Priority("LOW"), ParserUtil.parsePriority("LOW"));
        assertEquals(new Priority("LOW"), ParserUtil.parsePriority("low"));
        assertEquals(new Priority("LOW"), ParserUtil.parsePriority("Low"));

        assertEquals(new Priority("MEDIUM"), ParserUtil.parsePriority("MEDIUM"));
        assertEquals(new Priority("MEDIUM"), ParserUtil.parsePriority("medium"));
        assertEquals(new Priority("MEDIUM"), ParserUtil.parsePriority("Medium"));

        assertEquals(new Priority("HIGH"), ParserUtil.parsePriority("HIGH"));
        assertEquals(new Priority("HIGH"), ParserUtil.parsePriority("high"));
        assertEquals(new Priority("HIGH"), ParserUtil.parsePriority("High"));
    }

    @Test
    public void parseAge_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAge(null));
    }

    @Test
    public void parseAge_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAge(INVALID_AGE));
        assertThrows(ParseException.class, () -> ParserUtil.parseAge("12.5"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAge("-5"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAge("20a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAge("1234")); // more than 3 digits
    }

    @Test
    public void parseAge_emptyValue_returnsEmptyAge() throws Exception {
        Age expectedAge = new Age("");
        assertEquals(expectedAge, ParserUtil.parseAge(""));
        assertEquals(expectedAge, ParserUtil.parseAge("   "));
    }

    @Test
    public void parseAge_validValueWithoutWhitespace_returnsAge() throws Exception {
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(VALID_AGE));
    }

    @Test
    public void parseAge_validValueWithWhitespace_returnsTrimmedAge() throws Exception {
        String ageWithWhitespace = "  " + VALID_AGE + "  ";
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(ageWithWhitespace));
    }

    @Test
    public void parseLastContactedDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLastContactedDate(null));
    }

    @Test
    public void parseLastContactedDate_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLastContactedDate(INVALID_DATE));
    }

    @Test
    public void parseLastContactedDate_invalidDate_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLastContactedDate(INVALID_DATE_2));
    }

    @Test
    public void parseLastContactedDate_validValueWithoutWhitespace_returnsLastContactedDate() throws Exception {
        LastContactedDate expectedDate = new LastContactedDate(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseLastContactedDate(VALID_DATE));
    }

    @Test
    public void parseLastContactedDate_validValueWithWhitespace_returnsTrimmedLastContactedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        LastContactedDate expectedDate = new LastContactedDate(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseLastContactedDate(dateWithWhitespace));
    }

    @Test
    public void parseLastContactedDate_emptyString_returnsEmptyLastContactedDate() throws Exception {
        LastContactedDate expectedDate = new LastContactedDate("");
        assertEquals(expectedDate, ParserUtil.parseLastContactedDate(""));
    }
}
