
package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;
/**
 * A utility class to help with building Person objects for tests.
 */
public class PersonBuilder {
    public static final String DEFAULT_OCCUPATION = "Engineer";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_AGE = "25";
    public static final String DEFAULT_PRIORITY = "NONE";
    public static final String DEFAULT_LAST_CONTACTED_DATE = ""; // Empty string for no date set

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Occupation occupation;
    private Age age;
    private Priority priority;
    private IncomeBracket incomeBracket;
    private LastContactedDate lastContactedDate;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        occupation = new Occupation(DEFAULT_OCCUPATION);
        age = new Age(DEFAULT_AGE);
        priority = new Priority(DEFAULT_PRIORITY);
        incomeBracket = null; // Default to null for new persons
        lastContactedDate = new LastContactedDate(DEFAULT_LAST_CONTACTED_DATE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        occupation = personToCopy.getOccupation();
        age = personToCopy.getAge();
        priority = personToCopy.getPriority();
        incomeBracket = personToCopy.getIncomeBracket();
        lastContactedDate = personToCopy.getLastContactedDate();
        tags = new HashSet<>(personToCopy.getTags());
    }
    /**
     * Sets the occupation for the person being built.
     */
    public PersonBuilder withOccupation(String occupation) {
        this.occupation = new Occupation(occupation);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Person} that we are building.
     */
    public PersonBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Person} that we are building.
     */
    public PersonBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code IncomeBracket} of the {@code Person} that we are building.
     */
    public PersonBuilder withIncomeBracket(String incomeBracket) {
        this.incomeBracket = new IncomeBracket(incomeBracket);
        return this;
    }

    /**
     * Sets the {@code LastContactedDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withLastContactedDate(String date) {
        this.lastContactedDate = new LastContactedDate(date);
        return this;
    }

    /**
     * Builds and returns a new {@link seedu.address.model.person.Person} using the current
     * state of this builder.
     *
     * <p>Defaults used when fields are not modified:
     * <ul>
     *   <li>occupation: "Engineer"</li>
     *   <li>age: "25"</li>
     *   <li>priority: "NONE"</li>
     *   <li>incomeBracket: null</li>
     *   <li>lastContactedDate: empty string (no date)</li>
     *   <li>tags: empty set</li>
     * </ul>
     *
     * @return a newly constructed Person with the builder's current values.
     */
    public Person build() {
        return new Person(name, phone, email, address, occupation, tags, priority,
        age, incomeBracket, lastContactedDate);
    }

}
