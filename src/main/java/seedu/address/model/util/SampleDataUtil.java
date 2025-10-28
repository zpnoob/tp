package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.IncomeBracket;
import seedu.address.model.person.LastContactedDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Occupation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;
/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    // ...existing imports...
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email(""),
                new Address(""), new Occupation(""),
                getTagSet("no attempt"), new Priority(Priority.Level.NONE), new Age(""), null,
                new LastContactedDate("")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email(""),
                new Address(""), new Occupation("Engineer"),
                getTagSet("interested", "follow up"), new Priority(Priority.Level.HIGH), new Age(""), null,
                new LastContactedDate("2025-10-01")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email(""),
                new Address(""), new Occupation("Teacher"),
                getTagSet("attempted"), new Priority(Priority.Level.LOW), new Age("40"),
                new IncomeBracket(IncomeBracket.Level.LOW),
                new LastContactedDate("2025-10-20")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Occupation("Student"),
                getTagSet("interested"), new Priority(Priority.Level.HIGH), new Age("24"), null,
                new LastContactedDate("2025-05-20")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address(""), new Occupation("Designer"),
                getTagSet("no attempt", "follow up"), new Priority(Priority.Level.NONE), new Age("23"),
                new IncomeBracket(IncomeBracket.Level.MIDDLE),
                new LastContactedDate("")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Occupation("Chef"),
                getTagSet("converted"), new Priority(Priority.Level.MEDIUM),
                new Age("35"), new IncomeBracket(IncomeBracket.Level.HIGH), new LastContactedDate("2024-12-05"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
