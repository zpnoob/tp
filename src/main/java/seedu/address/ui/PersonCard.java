package seedu.address.ui;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;



/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label age;
    @FXML
    private Label priority;
    @FXML
    private Label occupation;
    @FXML
    private Label incomeBracket;
    @FXML
    private Label lastContactedDate;
    @FXML
    private FlowPane tags;
    @FXML
    private Label dncLabel;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        setFieldVisibility(phone, person.getPhone().value, null);
        setFieldVisibility(address, person.getAddress().value, null);
        setFieldVisibility(email, person.getEmail().value, null);
        setFieldVisibility(occupation, person.getOccupation().toString(), "Occupation: ");
        setFieldVisibility(age, person.getAge().value, "Age: ");

        // Handle last contacted date - hide if N/A
        String lastContactedValue = person.getLastContactedDate().toDisplayString();
        if (lastContactedValue.equals("N/A")) {
            hideField(lastContactedDate);
        } else {
            lastContactedDate.setText("Last Contacted: " + lastContactedValue);
        }

        // Handle priority with styling
        if (isFieldEmpty(person.getPriority().getValue()) || person.getPriority().getValue().equalsIgnoreCase("NONE")) {
            hideField(priority);
        } else {
            priority.setText("Priority: " + person.getPriority().getValue());
            setPriorityStyle(person.getPriority());
        }

        if (person.isDncTagged()) {
            dncLabel.setVisible(true);
            dncLabel.setManaged(true);
            tags.setManaged(false);
            tags.setVisible(false);
        } else {
            dncLabel.setVisible(false);
            dncLabel.setManaged(false);
            
            if (person.getTags().isEmpty()) {
                tags.setManaged(false);
                tags.setVisible(false);
            } else {
                tags.setManaged(true);
                tags.setVisible(true);
                person.getTags().stream()
                        .sorted(Comparator.comparing(tag -> tag.tagName))
                        .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            }
        }

        // Handle income bracket
        setIncomeBracketText(person);
    }

    /**
     * Sets the visibility of a field based on whether its value is empty.
     */
    private void setFieldVisibility(Label label, String value, String prefix) {
        if (isFieldEmpty(value)) {
            hideField(label);
        } else {
            String displayText = (prefix != null) ? prefix + value : value;
            label.setText(displayText);
        }
    }

    /**
     * Hides a field by setting both managed and visible to false.
     */
    private void hideField(Label label) {
        label.setManaged(false);
        label.setVisible(false);
    }

    /**
     * Checks if a field value is empty or null.
     */
    private boolean isFieldEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Sets the CSS style for the priority label based on priority level.
     */
    private void setPriorityStyle(Priority priority) {
        this.priority.getStyleClass().clear();
        this.priority.getStyleClass().add("priority-label");
        switch (priority.getValue().toUpperCase()) {
        case "HIGH":
            this.priority.getStyleClass().add("priority-high");
            break;
        case "MEDIUM":
            this.priority.getStyleClass().add("priority-medium");
            break;
        case "LOW":
            this.priority.getStyleClass().add("priority-low");
            break;
        case "NONE":
        default:
            this.priority.getStyleClass().add("priority-none");
            break;
        }
    }

    /**
     * Sets the income bracket text and style.
     */
    private void setIncomeBracketText(Person person) {
        if (person.getIncomeBracket() == null) {
            hideField(this.incomeBracket);
        } else {
            this.incomeBracket.setText("Income Bracket: " + person.getIncomeBracket().getValue());
            this.incomeBracket.getStyleClass().clear();
            this.incomeBracket.getStyleClass().add("income-bracket-label");
            String bracketLevel = person.getIncomeBracket().getValue().toLowerCase().replace(" ", "-");
            this.incomeBracket.getStyleClass().add("income-bracket-" + bracketLevel);
        }
    }
}
