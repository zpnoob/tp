package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.IncomeBracket;
import seedu.address.model.person.LastContactedDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String age;
    private final String priority;
    private final String incomeBracket;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String lastContactedDate;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("age") String age, @JsonProperty("priority") String priority,
            @JsonProperty("incomeBracket") String incomeBracket,
            @JsonProperty("lastContactedDate") String lastContactedDate,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.age = age;
        this.priority = priority;
        this.incomeBracket = incomeBracket;
        this.lastContactedDate = lastContactedDate;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        age = source.getAge().toString();
        priority = source.getPriority().toString();
        // Store income bracket as the enum name for consistent serialization
        incomeBracket = source.getIncomeBracket() != null ? source.getIncomeBracket().value.name() : null;
        lastContactedDate = source.getLastContactedDate().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        // Handle age field - default to empty if missing for backward compatibility
        Age modelAge;
        if (age == null) {
            modelAge = new Age("");
        } else {
            if (!Age.isValidAge(age)) {
                throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
            }
            modelAge = new Age(age);
        }

        // Handle priority field - default to NONE if missing for backward compatibility
        Priority modelPriority;
        if (priority == null) {
            modelPriority = new Priority(Priority.Level.NONE);
        } else {
            if (!Priority.isValidPriority(priority)) {
                throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
            }
            modelPriority = new Priority(priority);
        }

        // Handle income bracket field - can be null
        IncomeBracket modelIncomeBracket = null;
        if (incomeBracket != null && !incomeBracket.trim().isEmpty()) {
            try {
                // First try to parse as enum name (e.g., "LOW", "MIDDLE", "HIGH")
                IncomeBracket.Level level = IncomeBracket.Level.valueOf(incomeBracket.toUpperCase());
                modelIncomeBracket = new IncomeBracket(level);
            } catch (IllegalArgumentException e) {
                // Fallback: try to parse as user-friendly string (e.g., "low", "middle", "high")
                if (IncomeBracket.isValidIncomeBracket(incomeBracket)) {
                    modelIncomeBracket = new IncomeBracket(incomeBracket);
                } else {
                    throw new IllegalValueException(IncomeBracket.MESSAGE_CONSTRAINTS);
                }
            }
        }

        // Handle lastContactedDate field - default to empty string ("") if missing/null for backward compatibility
        LastContactedDate modelLastContactedDate;
        if (lastContactedDate == null) {
            modelLastContactedDate = new LastContactedDate("");
        } else {
            if (!LastContactedDate.isValidLastContactedDate(lastContactedDate)) {
                throw new IllegalValueException(LastContactedDate.MESSAGE_CONSTRAINTS);
            }
            modelLastContactedDate = new LastContactedDate(lastContactedDate);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelPriority, modelAge,
                modelIncomeBracket,
                modelLastContactedDate);
    }

}
