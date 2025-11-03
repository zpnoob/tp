package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME_BRACKET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_CONTACTED_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCUPATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "[" + PREFIX_NAME + "NAME] \n"
            + "[" + PREFIX_PHONE + "PHONE] \n"
            + "[" + PREFIX_EMAIL + "EMAIL] \n"
            + "[" + PREFIX_ADDRESS + "ADDRESS] \n"
            + "[" + PREFIX_OCCUPATION + "OCCUPATION] \n"
            + "[" + PREFIX_AGE + "AGE] \n"
            + "[" + PREFIX_PRIORITY + "PRIORITY] \n"
            + "[" + PREFIX_INCOME_BRACKET + "INCOME_BRACKET] \n"
            + "[" + PREFIX_LAST_CONTACTED_DATE + "LAST_CONTACTED_DATE] \n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_INCOME_BRACKET + "high"
            + PREFIX_LAST_CONTACTED_DATE + "2025-09-22";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_NAME =
            "A person with this name already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_EMAIL =
            "A person with this email address already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_PHONE =
            "A person with this phone number already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_NAME_AND_PHONE =
            "A person with this name and phone number already exists in the address book.";
    public static final String MESSAGE_DNC_CANNOT_MODIFY = "Cannot modify fields of a Do Not Call contact.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);
        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (personToEdit.isDncTagged() && editPersonDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_DNC_CANNOT_MODIFY);
        }

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        // Check for conflicts with existing persons
        if (!personToEdit.isSamePerson(editedPerson)) {
            checkForDuplicates(model, personToEdit, editedPerson);
        }

        if (!personToEdit.hasSameEmail(editedPerson) && model.hasPersonWithEmail(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS,
            Messages.format(editedPerson)));
    }

    /**
     * Checks if the edited person conflicts with any existing person in the address book.
     * A conflict occurs if another person (not the one being edited) has the same phone number,
     * or the same name AND phone number combination.
     *
     * @param model The model containing the address book.
     * @param personToEdit The original person being edited.
     * @param editedPerson The person with updated details.
     * @throws CommandException if a conflict is detected.
     */
    private void checkForDuplicates(Model model, Person personToEdit, Person editedPerson)
            throws CommandException {
        List<Person> allPersons = model.getAddressBook().getPersonList();

        boolean hasNameConflict = false;
        boolean hasPhoneConflict = false;

        for (Person existingPerson : allPersons) {
            if (existingPerson.equals(personToEdit)) {
                continue;
            }

            // Check for name conflict
            if (existingPerson.getName().equals(editedPerson.getName())) {
                hasNameConflict = true;
            }

            // Check for phone conflict
            if (existingPerson.getPhone().equals(editedPerson.getPhone())) {
                hasPhoneConflict = true;
            }
        }

        if (hasNameConflict && hasPhoneConflict) {
            throw new CommandException(MESSAGE_DUPLICATE_NAME_AND_PHONE);
        } else if (hasPhoneConflict) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit,
            EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Occupation updatedOccupation = editPersonDescriptor.getOccupation().orElse(personToEdit.getOccupation());
        Age updatedAge = editPersonDescriptor.getAge().orElse(personToEdit.getAge());
        Priority updatedPriority = editPersonDescriptor.getPriority().orElse(personToEdit.getPriority());
        IncomeBracket updatedIncomeBracket = editPersonDescriptor.getIncomeBracket()
                .orElse(personToEdit.getIncomeBracket());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        LastContactedDate updatedLastContactedDate = editPersonDescriptor.getLastContactedDate()
                .orElse(personToEdit.getLastContactedDate());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedOccupation, updatedTags, updatedPriority, updatedAge,
                updatedIncomeBracket,
                updatedLastContactedDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Occupation occupation;
        private Age age;
        private Set<Tag> tags;
        private Priority priority;
        private IncomeBracket incomeBracket;
        private LastContactedDate lastContactedDate;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setOccupation(toCopy.occupation);
            setAge(toCopy.age);
            setPriority(toCopy.priority);
            setIncomeBracket(toCopy.incomeBracket);
            setTags(toCopy.tags);
            setLastContactedDate(toCopy.lastContactedDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, occupation, age, priority,
                incomeBracket, tags, lastContactedDate);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setOccupation(Occupation occupation) {
            this.occupation = occupation;
        }

        public Optional<Occupation> getOccupation() {
            return Optional.ofNullable(occupation);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setIncomeBracket(IncomeBracket incomeBracket) {
            this.incomeBracket = incomeBracket;
        }

        public Optional<IncomeBracket> getIncomeBracket() {
            return Optional.ofNullable(incomeBracket);
        }

        public void setLastContactedDate(LastContactedDate lastContactedDate) {
            this.lastContactedDate = lastContactedDate;
        }

        public Optional<LastContactedDate> getLastContactedDate() {
            return Optional.ofNullable(lastContactedDate);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(occupation, otherEditPersonDescriptor.occupation)
                    && Objects.equals(age, otherEditPersonDescriptor.age)
                    && Objects.equals(priority, otherEditPersonDescriptor.priority)
                    && Objects.equals(incomeBracket, otherEditPersonDescriptor.incomeBracket)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(lastContactedDate, otherEditPersonDescriptor.lastContactedDate);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("occupation", occupation)
                    .add("age", age)
                    .add("priority", priority)
                    .add("incomeBracket", incomeBracket)
                    .add("tags", tags)
                    .add("lastContactedDate", lastContactedDate)
                    .toString();
        }
    }
}
