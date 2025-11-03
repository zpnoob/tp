package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void constructor_tagNameExactly30Characters_success() {
        String tagName30Chars = "123456789012345678901234567890";
        new Tag(tagName30Chars);
    }

    @Test
    public void constructor_tagName31Characters_throwsIllegalArgumentException() {
        String tagName31Chars = "1234567890123456789012345678901";
        assertThrows(IllegalArgumentException.class, () -> new Tag(tagName31Chars));
    }

    @Test
    public void constructor_tagNameExceedsLimit_throwsIllegalArgumentException() {
        String longTagName = "this is a very long tag name that exceeds the limit";
        assertThrows(IllegalArgumentException.class, () -> new Tag(longTagName));
    }

}

