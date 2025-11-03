package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MessagesTest {

    @Test
    public void getErrorMessageForDuplicatePrefixes_singleOccupation_returnsSpecialMessage() {
        Prefix occ = new Prefix("o/");
        String message = Messages.getErrorMessageForDuplicatePrefixes(occ);
        assertTrue(message.contains("o/(occupation)"));
        assertTrue(message.startsWith("Please specify each of the following fields at most once:"));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_multiplePrefixes_containsAllFriendlyNames() {
        Prefix name = new Prefix("n/");
        Prefix phone = new Prefix("p/");
        Prefix email = new Prefix("e/");

        String message = Messages.getErrorMessageForDuplicatePrefixes(name, phone, email);

        assertTrue(message.startsWith("Please specify each of the following fields at most once:"));
        assertTrue(message.contains("n/(name)"));
        assertTrue(message.contains("p/(phone)"));
        assertTrue(message.contains("e/(email)"));
        assertTrue(message.endsWith(". Remove duplicate entries and try again."));
    }

    @Test
    public void format_person_allRelevantFieldsIncludedAndExcludedAsAppropriate() {
        Person p = new PersonBuilder()
                .withName("Alice")
                .withPhone("99999999")
                .withOccupation("Engineer")
                .withAge("30")
                .withPriority("HIGH")
                .withEmail("alice@example.com")
                .withAddress("1 Example Road")
                .withLastContactedDate("2020-01-01")
                .withTags("friend", "colleague")
                .build();

        String formatted = Messages.format(p);

        // Basic fields
        assertTrue(formatted.contains("Alice"));
        assertTrue(formatted.contains("Phone: 99999999"));

        // Occupation should be included
        assertTrue(formatted.contains("Occupation: Engineer"));

        // Age and Priority
        assertTrue(formatted.contains("Age: 30"));
        assertTrue(formatted.contains("Priority: HIGH"));

        // Email and Address
        assertTrue(formatted.contains("Email: alice@example.com"));
        assertTrue(formatted.contains("Address: 1 Example Road"));

        // Last contacted and tags
        assertTrue(formatted.contains("Last Contacted: 2020-01-01"));
        assertTrue(formatted.contains("Tags: "));
        assertTrue(formatted.contains("friend") && formatted.contains("colleague"));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_priorityPrefix_returnsFriendlyName() {
        Prefix priority = new Prefix("pr/");
        String message = Messages.getErrorMessageForDuplicatePrefixes(priority);

        assertTrue(message.contains("pr/(priority)"));
        assertTrue(message.startsWith("Please specify each of the following fields at most once:"));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_agePrefix_returnsFriendlyName() {
        Prefix age = new Prefix("age/");
        String message = Messages.getErrorMessageForDuplicatePrefixes(age);

        assertTrue(message.contains("age/(age)"));
        assertTrue(message.startsWith("Please specify each of the following fields at most once:"));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_unknownPrefix_returnsDefaultPrefix() {
        Prefix unknown = new Prefix("xyz/");
        String message = Messages.getErrorMessageForDuplicatePrefixes(unknown);

        assertTrue(message.contains("xyz/"));
        assertTrue(message.startsWith("Please specify each of the following fields at most once:"));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_multipleIncludingPriorityAndAge_containsAllFriendlyNames() {
        Prefix priority = new Prefix("pr/");
        Prefix age = new Prefix("age/");
        Prefix occupation = new Prefix("o/");

        String message = Messages.getErrorMessageForDuplicatePrefixes(priority, age, occupation);

        assertTrue(message.startsWith("Please specify each of the following fields at most once:"));
        assertTrue(message.contains("pr/(priority)"));
        assertTrue(message.contains("age/(age)"));
        assertTrue(message.contains("o/(occupation)"));
        assertTrue(message.endsWith(". Remove duplicate entries and try again."));
    }

    @Test
    public void format_personWithNoneIncomeBracket_doesNotIncludeIncome() {
        Person p = new PersonBuilder()
                .withName("Bob")
                .withPhone("88888888")
                .withEmail("bob@example.com")
                .withAddress("2 Example Road")
                .withOccupation("Teacher")
                .withAge("25")
                .withPriority("MEDIUM")
                .withIncomeBracket(seedu.address.model.person.IncomeBracket.Level.NONE)
                .withLastContactedDate("2020-01-01")
                .build();

        String formatted = Messages.format(p);

        // Should not contain income bracket when it's NONE
        assertTrue(!formatted.contains("Income:"));
        assertTrue(formatted.contains("Bob"));
        assertTrue(formatted.contains("Phone: 88888888"));
    }

    @Test
    public void format_personWithIncomeBracket_includesIncome() {
        Person p = new PersonBuilder()
                .withName("Charlie")
                .withPhone("77777777")
                .withEmail("charlie@example.com")
                .withAddress("3 Example Road")
                .withOccupation("Doctor")
                .withAge("35")
                .withPriority("HIGH")
                .withIncomeBracket("high")
                .withLastContactedDate("2020-01-01")
                .build();

        String formatted = Messages.format(p);

        // Should contain income bracket when it's not NONE
        assertTrue(formatted.contains("Income:"));
        assertTrue(formatted.contains("HIGH"));
    }

    @Test
    public void format_personWithNonePriority_doesNotIncludePriority() {
        Person p = new PersonBuilder()
                .withName("David")
                .withPhone("66666666")
                .withEmail("david@example.com")
                .withAddress("4 Example Road")
                .withOccupation("Nurse")
                .withAge("28")
                .withPriority("NONE")
                .withLastContactedDate("2020-01-01")
                .build();

        String formatted = Messages.format(p);

        // Should not contain priority when it's NONE
        assertTrue(!formatted.contains("Priority:"));
        assertTrue(formatted.contains("David"));
        assertTrue(formatted.contains("Phone: 66666666"));
    }

    @Test
    public void format_personWithHighPriority_includesPriority() {
        Person p = new PersonBuilder()
                .withName("Emma")
                .withPhone("55555555")
                .withEmail("emma@example.com")
                .withAddress("5 Example Road")
                .withOccupation("Lawyer")
                .withAge("40")
                .withPriority("HIGH")
                .withLastContactedDate("2020-01-01")
                .build();

        String formatted = Messages.format(p);

        // Should contain priority when it's not NONE
        assertTrue(formatted.contains("Priority: HIGH"));
        assertTrue(formatted.contains("Emma"));
    }
}
