package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keyword not in name
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("xyz"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameContainsPartialMatch_returnsTrue() {
        // Partial match at beginning
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Ali"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Partial match at end
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("ice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Partial match in middle
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("lic"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // Exact phone match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("91234567").build()));

        // Multiple keywords where one matches phone
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("notit", "91234567"));
        assertTrue(predicate.test(new PersonBuilder().withName("Carol Danvers").withPhone("91234567").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Non-matching phone
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("98765432"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("91234567").build()));
    }

    @Test
    public void test_phoneContainsPartialMatch_returnsTrue() {
        // Partial phone match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("9123"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("91234567").build()));

        // Partial match at end
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("4567"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("91234567").build()));
    }

    @Test
    public void test_tagsContainKeywords_returnsTrue() {
        // Single tag match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bernice Yu")
                .withPhone("97555555")
                .withTags("friends", "colleagues")
                .build()));

        // Mixed-case keyword vs tag
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("FrIeNdS"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bernice Yu")
                .withPhone("97555555")
                .withTags("friends", "colleagues")
                .build()));

        // Multiple keywords where one matches a tag
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("vip", "colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withName("Carl Khoo")
                .withPhone("91230000")
                .withTags("team", "colleagues")
                .build()));
    }

    @Test
    public void test_tagsDoNotContainKeywords_returnsFalse() {
        // No tag matches
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("family"));
        assertFalse(predicate.test(new PersonBuilder().withName("David Li")
                .withPhone("93334444")
                .withTags("friends", "colleagues")
                .build()));

        // Empty tags should not match any tag keyword
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Eve")
                .withPhone("98887777")
                .withTags()
                .build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // Email match - exact match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob")
                .withEmail("alice@example.com").build()));

        // Multiple keywords where one matches email
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("nomatch", "bob@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob")
                .withEmail("bob@example.com").build()));
    }

    @Test
    public void test_emailContainsPartialMatch_returnsTrue() {
        // Partial email match - now supported
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob")
                .withEmail("alice@example.com").build()));

        // Partial match on domain
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("example.com"));
        assertTrue(predicate.test(new PersonBuilder().withName("Carol")
                .withEmail("carol@example.com").build()));

        // Specific example: lidavid should match lidavid@example.com
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("lidavid"));
        assertTrue(predicate.test(new PersonBuilder().withName("David Li")
                .withEmail("lidavid@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Non-matching email
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("bob@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice")
                .withEmail("alice@example.com").build()));

        // Completely different email
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("xyz"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice")
                .withEmail("alice@example.com").build()));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // Address match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice")
                .withAddress("123, Jurong West Ave 6, #08-111").build()));

        // Multiple keywords where one matches address
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("nomatch", "Clementi"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob")
                .withAddress("311, Clementi Ave 2, #02-25").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Non-matching address
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Tampines"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice")
                .withAddress("123, Jurong West Ave 6, #08-111").build()));
    }

    @Test
    public void test_occupationContainsKeywords_returnsTrue() {
        // Occupation match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice")
                .withOccupation("Engineer").build()));

        // Mixed-case occupation match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("tEaChEr"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob")
                .withOccupation("Teacher").build()));

        // Multiple keywords where one matches occupation
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("nomatch", "Manager"));
        assertTrue(predicate.test(new PersonBuilder().withName("Carol")
                .withOccupation("Manager").build()));
    }

    @Test
    public void test_occupationDoesNotContainKeywords_returnsFalse() {
        // Non-matching occupation
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Doctor"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice")
                .withOccupation("Engineer").build()));
    }

    @Test
    public void test_ageContainsKeywords_returnsTrue() {
        // Age match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("25"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice")
                .withAge("25").build()));

        // Multiple keywords where one matches age
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("nomatch", "30"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob")
                .withAge("30").build()));
    }

    @Test
    public void test_ageDoesNotContainKeywords_returnsFalse() {
        // Non-matching age
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("40"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice")
                .withAge("25").build()));
    }

    @Test
    public void test_lastContactedDateContainsKeywords_returnsTrue() {
        // Last contacted date match - exact match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("2025-10-21"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice")
                .withLastContactedDate("2025-10-21").build()));

        // Multiple keywords where one matches last contacted date
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("nomatch", "2024-12-05"));
        assertTrue(predicate.test(new PersonBuilder().withName("Carol")
                .withLastContactedDate("2024-12-05").build()));
    }

    @Test
    public void test_lastContactedDateContainsPartialMatch_returnsTrue() {
        // Partial date match (year) - now supported
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("2025"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob")
                .withLastContactedDate("2025-10-21").build()));

        // Partial match on month-day
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("10-21"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice")
                .withLastContactedDate("2025-10-21").build()));
    }

    @Test
    public void test_lastContactedDateDoesNotContainKeywords_returnsFalse() {
        // Non-matching last contacted date
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("2024-01-01"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice")
                .withLastContactedDate("2025-10-21").build()));

        // Completely different date part
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("1999"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob")
                .withLastContactedDate("2025-10-21").build()));
    }

    @Test
    public void test_mixedFieldsKeywordsMatchAcrossDifferentFields_returnsTrue() {
        // One keyword matches tag; other keywords do not match name/phone
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("random", "friends", "xxyyzz"));
        assertTrue(predicate.test(new PersonBuilder().withName("Foo Bar")
                .withPhone("90000000")
                .withTags("friends")
                .build()));

        // One keyword matches phone; none match name/tags
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("not-in-name", "90000000", "nope"));
        assertTrue(predicate.test(new PersonBuilder().withName("Zed Zee")
                .withPhone("90000000")
                .withTags("team")
                .build()));

        // One keyword matches email; others don't match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("xyz", "alice@example.com", "abc"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice")
                .withEmail("alice@example.com")
                .build()));

        // One keyword matches occupation; others don't match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("random", "Engineer", "xyz"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob")
                .withOccupation("Engineer")
                .build()));

        // One keyword matches age; others don't match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("nomatch", "25", "abc"));
        assertTrue(predicate.test(new PersonBuilder().withName("Carol")
                .withAge("25")
                .build()));
    }

    @Test
    public void test_noMatchAnywhere_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("omega", "delta"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withAddress("123 Street")
                .withOccupation("Engineer")
                .withAge("25")
                .withLastContactedDate("2025-10-21")
                .withTags("friends", "colleagues")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
