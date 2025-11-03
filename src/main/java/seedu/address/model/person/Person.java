package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.DncTag;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Occupation occupation;
    private final Set<Tag> tags = new HashSet<>();
    private final Priority priority;
    private final Age age;
    private final IncomeBracket incomeBracket;
    private final LastContactedDate lastContactedDate;

    /**
     * Most fields must be present and not null. IncomeBracket can be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Occupation occupation, Set<Tag> tags,
        Priority priority, Age age, IncomeBracket incomeBracket, LastContactedDate lastContactedDate) {
        requireAllNonNull(name, phone, email, address, occupation, tags, priority, lastContactedDate);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.occupation = occupation;
        this.priority = priority;
        this.incomeBracket = incomeBracket; // Can be null
        this.age = age;
        this.tags.addAll(tags);
        this.lastContactedDate = lastContactedDate;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Priority getPriority() {
        return priority;
    }

    public IncomeBracket getIncomeBracket() {
        return incomeBracket;
    }
    public Age getAge() {
        return age;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public LastContactedDate getLastContactedDate() {
        return lastContactedDate;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
            && otherPerson.getName().equals(getName())
            && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same phone number.
     * This can be used to detect duplicate phone numbers regardless of name.
     */
    public boolean hasSamePhone(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same email address.
     * Returns false if either person has an empty email address.
     */
    public boolean hasSameEmail(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && !getEmail().value.isEmpty()
                && !otherPerson.getEmail().value.isEmpty()
                && otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && occupation.equals(otherPerson.occupation)
                && priority.equals(otherPerson.priority)
                && Objects.equals(incomeBracket, otherPerson.incomeBracket)
                && age.equals(otherPerson.age)
                && tags.equals(otherPerson.tags)
                && lastContactedDate.equals(otherPerson.lastContactedDate);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, occupation, priority, age,
        incomeBracket, tags, lastContactedDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("occupations", occupation)
                .add("tags", tags)
                .add("priority", priority)
                .add("incomeBracket", incomeBracket)
                .add("age", age)
                .add("lastContactedDate", lastContactedDate)
                .toString();
    }
    /**
     * Returns true if this person is marked as Do Not Call.
     */
    public boolean isDncTagged() {
        return tags.stream().anyMatch(t -> t instanceof DncTag);
    }

}
