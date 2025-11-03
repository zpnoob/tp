package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Age;
import seedu.address.model.person.IncomeBracket;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_AGE = "abc";
    private static final String INVALID_INCOME_BRACKET = "INVALID";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_LAST_CONTACTED_DATE = "2025-13-45"; // Invalid date format
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_OCCUPATION = BENSON.getOccupation().toString();
    private static final String VALID_AGE = BENSON.getAge().toString();
    private static final String VALID_PRIORITY = BENSON.getPriority().toString();
    private static final String VALID_INCOME_BRACKET = BENSON.getIncomeBracket().toString();
    private static final String VALID_LAST_CONTACTED_DATE = BENSON.getLastContactedDate().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OCCUPATION,
                        VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET, VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET,
                VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OCCUPATION,
                        VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET, VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET,
                VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_OCCUPATION,
                        VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET, VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET,
                VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_OCCUPATION,
                        VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET, VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET,
                VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_invalidLastContactedDate_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OCCUPATION,
                        VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET, INVALID_LAST_CONTACTED_DATE, VALID_TAGS);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }

    @Test
    public void toModelType_nullLastContactedDate_success() {
        // Null lastContactedDate is allowed for backward compatibility and defaults to empty string
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET,
                null, VALID_TAGS);
        try {
            person.toModelType();
        } catch (IllegalValueException e) {
            throw new AssertionError("Should not throw exception for null lastContactedDate: " + e.getMessage());
        }
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, INVALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET,
                VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidIncomeBracket_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, INVALID_INCOME_BRACKET,
                VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        String expectedMessage = IncomeBracket.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OCCUPATION,
                        VALID_AGE, VALID_PRIORITY, VALID_INCOME_BRACKET, VALID_LAST_CONTACTED_DATE, invalidTags);
        assertThrows(IllegalValueException.class, () -> person.toModelType());
    }
    @Test
    public void toModelType_personWithDncTag_returnsPerson() throws Exception {
        List<JsonAdaptedTag> dncTags = new ArrayList<>();
        dncTags.add(new JsonAdaptedTag("Do Not Call"));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, "MIDDLE",
                VALID_LAST_CONTACTED_DATE, dncTags);
        seedu.address.model.person.Person modelPerson = person.toModelType();
        assertTrue(modelPerson.isDncTagged());
    }

    @Test
    public void toModelType_personWithDncTagLowercase_returnsPerson() throws Exception {
        List<JsonAdaptedTag> dncTags = new ArrayList<>();
        dncTags.add(new JsonAdaptedTag("do not call"));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, "MIDDLE",
                VALID_LAST_CONTACTED_DATE, dncTags);
        seedu.address.model.person.Person modelPerson = person.toModelType();
        assertTrue(modelPerson.isDncTagged());
    }

    @Test
    public void toModelType_personWithDncTagUppercase_returnsPerson() throws Exception {
        List<JsonAdaptedTag> dncTags = new ArrayList<>();
        dncTags.add(new JsonAdaptedTag("DO NOT CALL"));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, "MIDDLE",
                VALID_LAST_CONTACTED_DATE, dncTags);
        seedu.address.model.person.Person modelPerson = person.toModelType();
        assertTrue(modelPerson.isDncTagged());
    }

    @Test
    public void toModelType_personWithDncTagMixedCase_returnsPerson() throws Exception {
        List<JsonAdaptedTag> dncTags = new ArrayList<>();
        dncTags.add(new JsonAdaptedTag("Do NoT cAlL"));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, "MIDDLE",
                VALID_LAST_CONTACTED_DATE, dncTags);
        seedu.address.model.person.Person modelPerson = person.toModelType();
        assertTrue(modelPerson.isDncTagged());
    }

    @Test
    public void toModelType_nullIncomeBracket_returnsPersonWithNullIncomeBracket() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, null,
                VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        seedu.address.model.person.Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getIncomeBracket());
    }

    @Test
    public void toModelType_emptyIncomeBracket_returnsPersonWithNullIncomeBracket() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_OCCUPATION, VALID_AGE, VALID_PRIORITY, "",
                VALID_LAST_CONTACTED_DATE, VALID_TAGS);
        seedu.address.model.person.Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getIncomeBracket());
    }

    @Test
    public void constructor_personWithNoneIncomeBracket_savesAsNull() throws Exception {
        seedu.address.model.person.Person personWithNone = new seedu.address.testutil.PersonBuilder()
                .withName("Alice")
                .withPhone("99999999")
                .withEmail("alice@example.com")
                .withAddress("1 Example St")
                .withOccupation("Engineer")
                .withAge("30")
                .withPriority("HIGH")
                .withIncomeBracket(seedu.address.model.person.IncomeBracket.Level.NONE)
                .withLastContactedDate("2020-01-01")
                .build();

        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(personWithNone);
        // Convert back to model and verify income bracket is null
        seedu.address.model.person.Person modelPerson = jsonPerson.toModelType();
        assertEquals(null, modelPerson.getIncomeBracket());
    }

}

