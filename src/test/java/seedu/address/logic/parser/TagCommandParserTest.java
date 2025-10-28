package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;

/**
 * Unit tests for {@link TagCommandParser}.
 */
public class TagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    private final TagCommandParser parser = new TagCommandParser();

    // ---------- FAILURE CASES ----------

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, " t/friend", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-1 t/friend", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0 t/friend", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "abc t/friend", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingTagPrefix_failure() {
        assertParseFailure(parser, "1 friend", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTagValue_failure() {
        assertParseFailure(parser, "1 t/!!invalid!!", seedu.address.model.tag.Tag.MESSAGE_CONSTRAINTS);
    }
    // ---------- SUCCESS CASES ----------

    @Test
    public void parse_validSingleTag_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        Set<Tag> tagSet = Collections.singleton(new Tag(VALID_TAG_FRIEND));
        TagCommand expectedCommand = new TagCommand(targetIndex, tagSet);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validTagWithExtraWhitespace_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = "  " + targetIndex.getOneBased() + "   " + TAG_DESC_HUSBAND + "  ";
        Set<Tag> tagSet = Collections.singleton(new Tag(VALID_TAG_HUSBAND));
        TagCommand expectedCommand = new TagCommand(targetIndex, tagSet);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_tagWithSpaces_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " t/follow up";
        Set<Tag> tagSet = Collections.singleton(new Tag("follow up"));
        TagCommand expectedCommand = new TagCommand(targetIndex, tagSet);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTags_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " t/follow up t/interested";
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("follow up"));
        tagSet.add(new Tag("interested"));
        TagCommand expectedCommand = new TagCommand(targetIndex, tagSet);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
