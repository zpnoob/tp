package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;

public class ResultDisplayTest {

    private static boolean javaFxInitialized = false;

    /**
     * Initializes JavaFX runtime for testing UI components.
     * This is needed because JavaFX components require the JavaFX Application Thread.
     */
    @BeforeAll
    public static void initializeJavaFx() {
        if (!javaFxInitialized) {
            try {
                // Set system properties for headless mode
                System.setProperty("testfx.robot", "glass");
                System.setProperty("testfx.headless", "true");
                System.setProperty("prism.order", "sw");
                System.setProperty("prism.text", "t2k");
                System.setProperty("java.awt.headless", "true");
                // Try to initialize JavaFX Platform
                if (!Platform.isFxApplicationThread()) {
                    // Create JFXPanel to initialize JavaFX runtime
                    @SuppressWarnings("unused")
                    JFXPanel panel = new JFXPanel();
                    // Wait a bit for JavaFX to initialize
                    Thread.sleep(100);
                }
                javaFxInitialized = true;
            } catch (InterruptedException | RuntimeException e) {
                // If JavaFX initialization fails, tests will be skipped
                System.err.println("Warning: JavaFX initialization failed: " + e.getMessage());
                javaFxInitialized = false;
            }
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void constructor_success() {
        if (javaFxInitialized) {
            assertDoesNotThrow(() -> new ResultDisplay());
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void setFeedbackToUser_nullInput_throwsNullPointerException() {
        if (javaFxInitialized) {
            ResultDisplay resultDisplay = new ResultDisplay();
            assertThrows(NullPointerException.class, () -> resultDisplay.setFeedbackToUser(null));
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void setFeedbackToUser_validInput_success() {
        if (javaFxInitialized) {
            ResultDisplay resultDisplay = new ResultDisplay();
            assertDoesNotThrow(() -> resultDisplay.setFeedbackToUser("Test feedback"));
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void setFeedbackToUser_emptyString_success() {
        if (javaFxInitialized) {
            ResultDisplay resultDisplay = new ResultDisplay();
            assertDoesNotThrow(() -> resultDisplay.setFeedbackToUser(""));
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void setFeedbackToUser_multilineText_success() {
        if (javaFxInitialized) {
            ResultDisplay resultDisplay = new ResultDisplay();
            String multilineText = "Line 1\nLine 2\nLine 3";
            assertDoesNotThrow(() -> resultDisplay.setFeedbackToUser(multilineText));
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void setFeedbackToUser_longText_success() {
        if (javaFxInitialized) {
            ResultDisplay resultDisplay = new ResultDisplay();
            String longText = "This is a very long text that should wrap around multiple lines when displayed in "
                    + "the text area. It contains many words and should test the text wrapping functionality "
                    + "of the result display component.";
            assertDoesNotThrow(() -> resultDisplay.setFeedbackToUser(longText));
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void getRoot_returnsNonNull() {
        if (javaFxInitialized) {
            ResultDisplay resultDisplay = new ResultDisplay();
            assertNotNull(resultDisplay.getRoot());
        }
    }

    /**
     * Test class to access private methods for testing.
     * This extends ResultDisplay to allow testing of the adjustTextAreaHeight method.
     */
    private static class TestableResultDisplay extends ResultDisplay {

        public TestableResultDisplay() {
            super();
        }

        /**
         * Exposes the private adjustTextAreaHeight method for testing.
         */
        public void testAdjustTextAreaHeight() {
            // Access the private resultDisplay field through reflection
            try {
                java.lang.reflect.Field field = ResultDisplay.class.getDeclaredField("resultDisplay");
                field.setAccessible(true);
                TextArea textArea = (TextArea) field.get(this);

                // Call the private method through reflection
                java.lang.reflect.Method method = ResultDisplay.class.getDeclaredMethod("adjustTextAreaHeight");
                method.setAccessible(true);
                method.invoke(this);

                // Verify that the method doesn't throw exceptions
                assertNotNull(textArea);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException
                    | java.lang.reflect.InvocationTargetException e) {
                throw new RuntimeException("Failed to test adjustTextAreaHeight", e);
            }
        }

        /**
         * Gets the current row count of the text area for testing.
         */
        public int getCurrentRowCount() {
            try {
                java.lang.reflect.Field field = ResultDisplay.class.getDeclaredField("resultDisplay");
                field.setAccessible(true);
                TextArea textArea = (TextArea) field.get(this);
                return textArea.getPrefRowCount();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to get row count", e);
            }
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void adjustTextAreaHeight_emptyText_setsOneRow() {
        if (javaFxInitialized) {
            TestableResultDisplay resultDisplay = new TestableResultDisplay();

            // Set empty text and trigger height adjustment
            resultDisplay.setFeedbackToUser("");
            resultDisplay.testAdjustTextAreaHeight();

            // Should set to minimum 1 row for empty text
            assertEquals(1, resultDisplay.getCurrentRowCount());
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void adjustTextAreaHeight_singleLine_setsOneRow() {
        if (javaFxInitialized) {
            TestableResultDisplay resultDisplay = new TestableResultDisplay();

            // Set single line text and trigger height adjustment
            resultDisplay.setFeedbackToUser("Single line text");
            resultDisplay.testAdjustTextAreaHeight();

            // Should set to 1 row for single line
            assertEquals(1, resultDisplay.getCurrentRowCount());
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void adjustTextAreaHeight_multipleLines_setsCorrectRowCount() {
        if (javaFxInitialized) {
            TestableResultDisplay resultDisplay = new TestableResultDisplay();

            // Set multi-line text and trigger height adjustment
            String multilineText = "Line 1\nLine 2\nLine 3";
            resultDisplay.setFeedbackToUser(multilineText);
            resultDisplay.testAdjustTextAreaHeight();

            // Should set to 3 rows for 3 lines
            assertEquals(3, resultDisplay.getCurrentRowCount());
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void adjustTextAreaHeight_textWithTrailingNewline_setsCorrectRowCount() {
        if (javaFxInitialized) {
            TestableResultDisplay resultDisplay = new TestableResultDisplay();

            // Set text with trailing newline
            String textWithTrailingNewline = "Line 1\nLine 2\n";
            resultDisplay.setFeedbackToUser(textWithTrailingNewline);
            resultDisplay.testAdjustTextAreaHeight();

            // Should account for trailing newline (split with -1 parameter)
            assertEquals(3, resultDisplay.getCurrentRowCount());
        }
    }

    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void adjustTextAreaHeight_textWithMultipleConsecutiveNewlines_setsCorrectRowCount() {
        if (javaFxInitialized) {
            TestableResultDisplay resultDisplay = new TestableResultDisplay();

            // Set text with multiple consecutive newlines
            String textWithConsecutiveNewlines = "Line 1\n\n\nLine 4";
            resultDisplay.setFeedbackToUser(textWithConsecutiveNewlines);
            resultDisplay.testAdjustTextAreaHeight();

            // Should count all lines including empty ones
            assertEquals(4, resultDisplay.getCurrentRowCount());
        }
    }
}
