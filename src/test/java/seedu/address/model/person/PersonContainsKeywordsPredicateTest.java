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

        // Note: if StringUtil.containsWordIgnoreCase requires whole-word matches,
        // partials like "9123" should NOT match.
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("9123"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("91234567").build()));
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
    }

    @Test
    public void test_noMatchAnywhere_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("omega", "delta"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob")
                .withPhone("91234567")
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
