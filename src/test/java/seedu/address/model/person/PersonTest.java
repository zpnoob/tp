package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {
    @Test
    public void occupationField_setAndGet_correct() {
        Person person = new PersonBuilder().withOccupation("Doctor").build();
        assertEquals("Doctor", person.getOccupation().toString());
    }

    @Test
    public void priorityField_setAndGet_correct() {
        Person person = new PersonBuilder().withPriority("HIGH").build();
        assertEquals("HIGH", person.getPriority().toString());
    }

    @Test
    public void occupation_affectsEquality() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        Person editedAlice = new PersonBuilder(ALICE).withOccupation("Lawyer").build();
        assertFalse(aliceCopy.equals(editedAlice));
    }

    @Test
    public void priority_affectsEquality() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        Person editedAlice = new PersonBuilder(ALICE).withPriority("LOW").build();
        assertFalse(aliceCopy.equals(editedAlice));
    }

    @Test
    public void getTags_returnsUnmodifiableSet() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags()
            .add(new seedu.address.model.tag.Tag("test")));
    }

    @Test
        public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().withTags("testTag").build();
        seedu.address.model.tag.Tag tag = new seedu.address.model.tag.Tag("testTag");
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(tag));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, different phone -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same name and phone, other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true since its same person actually
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // same name, different phone -> returns false
        Person sameNameDifferentPhone = new PersonBuilder(ALICE).withPhone("99999999").build();
        assertFalse(ALICE.isSamePerson(sameNameDifferentPhone));

        // different name, same phone -> returns false
        Person differentNameSamePhone = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(differentNameSamePhone));
    }

    @Test
    public void hasSamePhone() {
        // same object -> returns true
        assertTrue(ALICE.hasSamePhone(ALICE));

        // null -> returns false
        assertFalse(ALICE.hasSamePhone(null));

        // same phone, different name -> returns true
        Person differentNameSamePhone = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.hasSamePhone(differentNameSamePhone));

        // same phone, all other attributes different -> returns true
        Person samePhoneOnly = new PersonBuilder(ALICE)
                .withName(VALID_NAME_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(ALICE.hasSamePhone(samePhoneOnly));

        // different phone, all other attributes same -> returns false
        Person differentPhone = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.hasSamePhone(differentPhone));
    }

    @Test
    public void hasSameEmail() {
        // same object -> returns true
        assertTrue(ALICE.hasSameEmail(ALICE));

        // null -> returns false
        assertFalse(ALICE.hasSameEmail(null));

        // same email, different name -> returns true
        Person differentNameSameEmail = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.hasSameEmail(differentNameSameEmail));

        // same email, all other attributes different -> returns true
        Person sameEmailOnly = new PersonBuilder(ALICE)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(ALICE.hasSameEmail(sameEmailOnly));

        // different email, all other attributes same -> returns false
        Person differentEmail = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.hasSameEmail(differentEmail));

        // empty email on first person -> returns false
        Person emptyEmailPerson = new PersonBuilder(ALICE).withEmail("").build();
        assertFalse(emptyEmailPerson.hasSameEmail(ALICE));

        // empty email on second person -> returns false
        assertFalse(ALICE.hasSameEmail(emptyEmailPerson));

        // both persons have empty email -> returns false
        Person anotherEmptyEmailPerson = new PersonBuilder(BOB).withEmail("").build();
        assertFalse(emptyEmailPerson.hasSameEmail(anotherEmptyEmailPerson));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different lastContactedDate -> returns false
        editedAlice = new PersonBuilder(ALICE).withLastContactedDate("2025-10-22").build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", occupations=" + ALICE.getOccupation()
                + ", tags=" + ALICE.getTags() + ", priority=" + ALICE.getPriority()
                + ", incomeBracket=" + ALICE.getIncomeBracket()
                + ", age=" + ALICE.getAge() + ", lastContactedDate=" + ALICE.getLastContactedDate() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
