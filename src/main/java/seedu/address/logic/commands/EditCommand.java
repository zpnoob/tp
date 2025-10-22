package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME_BRACKET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_CONTACTED_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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
<<<<<<< HEAD
        + "by the index number used in the displayed person list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_ADDRESS + "ADDRESS "
        + "o/OCCUPATION "
        + PREFIX_PRIORITY + "PRIORITY "
        + PREFIX_TAG + "TAG...\n"
        + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com";
=======
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_AGE + "AGE] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_INCOME_BRACKET + "INCOME_BRACKET] "
            + "[" + PREFIX_LAST_CONTACTED_DATE + "LAST_CONTACTED_DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_INCOME_BRACKET + "high"
            + PREFIX_LAST_CONTACTED_DATE + "2025-09-22";

>>>>>>> upstream
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
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
        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS,
            Messages.format(editedPerson)));
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
<<<<<<< HEAD
        Occupation updatedOccupation = editPersonDescriptor.getOccupation() != null
            ? editPersonDescriptor.getOccupation() : personToEdit.getOccupation();
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Priority updatedPriority = editPersonDescriptor.getPriority().orElse(personToEdit.getPriority());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
            updatedOccupation, updatedTags, updatedPriority);
=======
        Age updatedAge = editPersonDescriptor.getAge().orElse(personToEdit.getAge());
        Priority updatedPriority = editPersonDescriptor.getPriority().orElse(personToEdit.getPriority());
        IncomeBracket updatedIncomeBracket = editPersonDescriptor.getIncomeBracket()
                .orElse(personToEdit.getIncomeBracket());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        LastContactedDate updatedLastContactedDate = editPersonDescriptor.getLastContactedDate()
                .orElse(personToEdit.getLastContactedDate());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedTags, updatedPriority, updatedAge,
                updatedIncomeBracket,
                updatedLastContactedDate);
>>>>>>> upstream
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
<<<<<<< HEAD
        private Occupation occupation;
=======
        private Age age;
>>>>>>> upstream
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
<<<<<<< HEAD
            setOccupation(toCopy.occupation);
            setTags(toCopy.tags);
            setPriority(toCopy.priority);
=======
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
            return CollectionUtil.isAnyNonNull(name, phone, email, address, age, priority,
                incomeBracket, tags, lastContactedDate);
>>>>>>> upstream
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

<<<<<<< HEAD
        public void setOccupation(Occupation occupation) {
            this.occupation = occupation;
        }

        public Occupation getOccupation() {
            return occupation;
        }

        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
=======
        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
>>>>>>> upstream
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

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, occupation, tags, priority);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

<<<<<<< HEAD
            EditPersonDescriptor otherDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherDescriptor.name)
                    && Objects.equals(phone, otherDescriptor.phone)
                    && Objects.equals(email, otherDescriptor.email)
                    && Objects.equals(address, otherDescriptor.address)
                    && Objects.equals(occupation, otherDescriptor.occupation)
                    && Objects.equals(tags, otherDescriptor.tags)
                    && Objects.equals(priority, otherDescriptor.priority);
=======
            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(age, otherEditPersonDescriptor.age)
                    && Objects.equals(priority, otherEditPersonDescriptor.priority)
                    && Objects.equals(incomeBracket, otherEditPersonDescriptor.incomeBracket)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(lastContactedDate, otherEditPersonDescriptor.lastContactedDate);
>>>>>>> upstream
        }

        @Override
        public String toString() {
<<<<<<< HEAD
            return new seedu.address.commons.util.ToStringBuilder(this)
                .add("name", getName().orElse(null))
                .add("phone", getPhone().orElse(null))
                .add("email", getEmail().orElse(null))
                .add("address", getAddress().orElse(null))
                .add("occupation", getOccupation())
                .add("priority", getPriority().orElse(null))
                .add("tags", getTags().orElse(null))
                .toString();
=======
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("age", age)
                    .add("priority", priority)
                    .add("incomeBracket", incomeBracket)
                    .add("tags", tags)
                    .add("lastContactedDate", lastContactedDate)
                    .toString();
>>>>>>> upstream
        }
    }
}
