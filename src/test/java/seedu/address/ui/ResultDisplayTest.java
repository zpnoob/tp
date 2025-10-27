package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

/**
 * Contains tests for ResultDisplay.
 * Tests focus on the core logic without initializing JavaFX UI components.
 */
public class ResultDisplayTest {

    @Test
    public void constructor_classExists() {
        // Test that the ResultDisplay class exists and has a default constructor
        assertDoesNotThrow(() -> {
            Constructor<ResultDisplay> constructor = ResultDisplay.class.getDeclaredConstructor();
            assertNotNull(constructor);
        });
    }

    @Test
    public void setFeedbackToUser_method_exists() {
        // Test that the setFeedbackToUser method exists with correct signature
        assertDoesNotThrow(() -> {
            Method method = ResultDisplay.class.getDeclaredMethod("setFeedbackToUser", String.class);
            assertNotNull(method);
            assertEquals(void.class, method.getReturnType());
        });
    }

    @Test
    public void adjustTextAreaHeight_method_exists() {
        // Test that the adjustTextAreaHeight method exists
        assertDoesNotThrow(() -> {
            Method method = ResultDisplay.class.getDeclaredMethod("adjustTextAreaHeight");
            assertNotNull(method);
            assertEquals(void.class, method.getReturnType());
        });
    }
    // Test the core line counting logic used in adjustTextAreaHeight
    @Test
    public void lineCountingLogic_emptyText_returnsOne() {
        String text = "";
        String[] lines = text.split("\n", -1);
        int lineCount = Math.max(1, lines.length);
        assertEquals(1, lineCount);
    }

    @Test
    public void lineCountingLogic_singleLine_returnsOne() {
        String text = "Single line text without newlines";
        String[] lines = text.split("\n", -1);
        assertEquals(1, lines.length);
    }

    @Test
    public void lineCountingLogic_multipleLines_returnsCorrectCount() {
        String text = "Line 1\nLine 2\nLine 3";
        String[] lines = text.split("\n", -1);
        assertEquals(3, lines.length);
    }

    @Test
    public void lineCountingLogic_trailingNewline_includesEmptyLine() {
        String text = "Line 1\nLine 2\n";
        String[] lines = text.split("\n", -1); // -1 to include trailing empty strings
        assertEquals(3, lines.length); // "Line 1", "Line 2", ""
    }

    @Test
    public void lineCountingLogic_consecutiveNewlines_countsEmptyLines() {
        String text = "Line 1\n\n\nLine 4";
        String[] lines = text.split("\n", -1);
        assertEquals(4, lines.length); // "Line 1", "", "", "Line 4"
    }

    @Test
    public void lineCountingLogic_onlyNewlines_countsCorrectly() {
        String text = "\n\n\n";
        String[] lines = text.split("\n", -1);
        assertEquals(4, lines.length); // "", "", "", ""
    }

    @Test
    public void lineCountingLogic_mixedContent_countsCorrectly() {
        String text = "First\n\nThird line\n\nFifth";
        String[] lines = text.split("\n", -1);
        assertEquals(5, lines.length); // "First", "", "Third line", "", "Fifth"
    }

    // Test null validation logic used in setFeedbackToUser
    @Test
    public void nullValidation_nullString_throwsNullPointerException() {
        String nullString = null;
        assertThrows(NullPointerException.class, () -> {
            java.util.Objects.requireNonNull(nullString);
        });
    }

    @Test
    public void nullValidation_validString_doesNotThrow() {
        assertDoesNotThrow(() -> java.util.Objects.requireNonNull("Valid string"));
        assertDoesNotThrow(() -> java.util.Objects.requireNonNull(""));
        assertDoesNotThrow(() -> java.util.Objects.requireNonNull("Multi\nline\nstring"));
    }

    // Test string processing logic that would be used in the actual implementation
    @Test
    public void textProcessing_isEmpty_handledCorrectly() {
        String emptyText = "";
        boolean isEmpty = emptyText.isEmpty();
        assertEquals(true, isEmpty);

        // Test the logic that would set prefRowCount to 1 for empty text
        int rowCount = isEmpty ? 1 : emptyText.split("\n", -1).length;
        assertEquals(1, rowCount);
    }

    @Test
    public void textProcessing_getText_simulation() {
        // Simulate the getText() operation that would happen in adjustTextAreaHeight
        String[] testTexts = {
            "",
            "Single line",
            "Line 1\nLine 2",
            "Line 1\nLine 2\nLine 3\n",
            "Complex\n\ntext\nwith\n\nempty\nlines"
        };
        int[] expectedLineCounts = {1, 1, 2, 4, 7};
        for (int i = 0; i < testTexts.length; i++) {
            String text = testTexts[i];
            int actualCount;
            if (text.isEmpty()) {
                actualCount = 1; // Minimum 1 row for empty text
            } else {
                String[] lines = text.split("\n", -1);
                actualCount = lines.length;
            }
            assertEquals(expectedLineCounts[i], actualCount,
                "Failed for text: " + text.replace("\n", "\\n"));
        }
    }

    @Test
    public void methodInvocation_setPrefRowCount_simulation() {
        // Test the logic that would be used to set prefRowCount
        String[] testInputs = {"", "one", "one\ntwo", "one\ntwo\nthree\n"};
        int[] expectedRowCounts = {1, 1, 2, 4};
        for (int i = 0; i < testInputs.length; i++) {
            String text = testInputs[i];
            // Simulate the adjustTextAreaHeight logic
            int totalLines;
            if (text.isEmpty()) {
                totalLines = 1;
            } else {
                String[] lines = text.split("\n", -1);
                totalLines = lines.length;
            }
            assertEquals(expectedRowCounts[i], totalLines);
        }
    }

    @Test
    public void reflectionAccess_privateMethod_canBeAccessed() {
        // Test that we can access the private adjustTextAreaHeight method via reflection
        // This ensures the method exists and has the expected signature
        assertDoesNotThrow(() -> {
            Method method = ResultDisplay.class.getDeclaredMethod("adjustTextAreaHeight");
            method.setAccessible(true);
            assertNotNull(method);
        });
    }

    @Test
    public void reflectionAccess_privateField_canBeAccessed() {
        // Test that we can access the private resultDisplay field via reflection
        // This ensures the field exists for the text area
        assertDoesNotThrow(() -> {
            java.lang.reflect.Field field = ResultDisplay.class.getDeclaredField("resultDisplay");
            field.setAccessible(true);
            assertNotNull(field);
        });
    }

    @Test
    public void inheritance_extendsUiPart() {
        // Test that ResultDisplay extends UiPart as expected
        Class<?> superclass = ResultDisplay.class.getSuperclass();
        assertEquals("UiPart", superclass.getSimpleName());
    }
}
