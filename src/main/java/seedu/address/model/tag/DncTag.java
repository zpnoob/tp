package seedu.address.model.tag;

/**
 * Represents a Do Not Call tag in the address book.
 * This is a special tag that indicates a person should not be called and cannot be modified.
 */
public class DncTag extends Tag {

    public static final String DNC_TAG_NAME = "Do Not Call";

    /**
     * Constructs a new DNC tag.
     */
    public DncTag() {
        super(DNC_TAG_NAME);
    }
}
