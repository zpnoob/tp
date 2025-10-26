package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        // Make the TextArea resize to fit its content
        resultDisplay.setWrapText(true);
        resultDisplay.setPrefRowCount(1); // Start with 1 row
        resultDisplay.textProperty().addListener((observable, oldValue, newValue) -> {
            adjustTextAreaHeight();
        });
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    /**
     * Adjusts the height of the TextArea to fit its content.
     */
    private void adjustTextAreaHeight() {
        String text = resultDisplay.getText();
        if (text.isEmpty()) {
            resultDisplay.setPrefRowCount(1);
            return;
        }
        
        // Count explicit line breaks
        String[] lines = text.split("\n", -1); // -1 to include trailing empty strings
        int totalLines = lines.length;
        
        // For a more accurate calculation, we can use a simpler approach
        // since JavaFX TextArea handles wrapping internally
        // We'll just count the actual lines and add a small buffer if needed
        resultDisplay.setPrefRowCount(totalLines);
    }

}
