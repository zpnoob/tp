---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# InsuraBook Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org)._

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="900" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

#### Person Class

For a detailed view of the `Person` class and its associated attribute classes (Name, Phone, Email, etc.), refer to the dedicated diagram below:

<puml src="diagrams/PersonClassDiagram.puml" width="1000" />

The `Person` class encapsulates all contact information for InsuraBook users. Key design decisions:
* **Required Fields**: Only Name and Phone are required fields. All other fields (Email, Address, Occupation, Age, Priority, Income Bracket, Tags) are optional.
* **Validation**: Each attribute class (Name, Phone, Email, etc.) performs its own validation upon construction.
* **Editability**: Person fields can be modified through the `edit` command, allowing users to update contact information as needed.
* **Tag Support**: Persons can have multiple tags, including a special `DncTag` for "Do Not Call" compliance

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

<div style="page-break-after: always;"></div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)
* uses Jackson-friendly adapter classes (`JsonAdaptedPerson`, `JsonAdaptedTag`) to bridge between domain models and JSON representation

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

<div style="page-break-after: always;"></div>

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* is reasonably comfortable using CLI apps
* has a need for reminders

**Value proposition**:
* saves time
* prevent lost information
* ensures timely follow-ups
* streamlines management


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​              | I want to …​                                                       | So that I can…​                                                |
|----------|----------------------|--------------------------------------------------------------------|----------------------------------------------------------------|
| `* * *`    | telemarketing agent  | filter contacts by tags, demographics, priority, or status         | create targeted campaigns                                      |
| `* * *`    | telemarketing agent  | import contacts from CSV/Excel/raw text files                      | save time entering bulk data                                   |
| `* * *`    | telemarketing agent  | add a new contact with a customer’s name and phone number          |                                                                |
| `* * *`    | telemarketing agent  | tag contacts (e.g., “interested,” “follow-up,” “do not call”)      | segment my leads                                               |
| `* * *`    | telemarketing agent  | search for a contact by name, number, or tag                       | quickly retrieve their details                                 |
| `* * *`    | telemarketing agent  | edit a contact’s details                                           |                                                                |
| `* * *`    | telemarketing agent  | prioritise contacts (high, medium, low)                            | focus on the most promising leads                              |
| `* * *`    | telemarketing agent  | mark contacts as “Do Not Call”                                     | comply with regulations                                        |
| `* * *`    | telemarketing agent  | delete outdated or duplicate contacts                              | keep my address book clean                                     |
| `* * *`    | telemarketing agent  | record a contact’s age, occupation, and income bracket             | match them to suitable insurance products                      |
| `* * *`    | telemarketing agent  | see the last contact date for each lead                            | know when to follow up                                         |
| `* * *`    | team leader          | assign leads to different agents                                   | workload is distributed fairly                                 |
| `* *`      | team leader          | view the call notes of my team members                             | track progress                                                 |
| `* *`      | telemarketing agent  | store multiple phone numbers and emails per contact                | reach them through different channels                          |
| `* *`      | telemarketing agent  | log existing insurance policies a contact already has              | avoid pitching irrelevant products                             |
| `* *`      | telemarketing agent  | sort contacts by last contacted date or priority                   | plan my day’s calls                                            |
| `* *`      | telemarketing agent  | bookmark or star important contacts                                | access them quickly                                            |
| `* *`      | telemarketing agent  | securely store sensitive customer information                      | avoid data leaks                                               |
| `* *`      | telemarketing agent  | quickly remove all personal data of a contact upon request         | respect privacy rights                                         |
| `* *`      | telemarketing agent  | view the number of follow-ups pending                              | manage my workload                                             |
| `*`        | team leader          | reassign contacts from one agent to another                        | ensure no lead is neglected if someone is unavailable          |
| `*`        | team leader          | see a summary dashboard of call outcomes                           | evaluate team performance                                      |
| `*`        | telemarketing agent  | note important dates such as policy renewal or birthday            | time my calls effectively                                      |
| `*`        | telemarketing agent  | record notes after each call                                       | avoid forgetting what was discussed                            |
| `*`        | telemarketing agent  | schedule follow-up reminders                                       | never miss important calls                                     |
| `*`        | telemarketing agent  | export selected contacts into a call list                          | use them with auto-dialer tools                                |
| `*`        | telemarketing agent  | log when I obtained consent to contact someone                     | prove compliance if needed                                     |
| `*`        | telemarketing agent  | track my call success rate                                         | monitor my personal performance                                |
| `*`        | telemarketing agent  | see which products generate the most interest among leads          | guide my focus                                                 |
| `*`        | telemarketing agent  | generate a weekly report of my calls and outcomes                  | share progress with my supervisor                              |

<div style="page-break-after: always;"></div>

### Use cases

#### UC1 - Delete a Person
<box>
Software System: InsuraBook<br>
Use case: UC1 - Delete a Person<br>
Actor: User<br>

<pre>
MSS: 
   1.  User requests to list persons
   2.  InsuraBook shows a list of persons
   3.  User requests to delete a specific person in the list
   4.  InsuraBook deletes the person
   Use case ends.

Extensions:
   2a. The list is empty.
   Use case ends.

   3a. The given index is invalid.
      3a1. InsuraBook shows an error message.
      Use case resumes at step 2.
</pre>
</box>

#### UC2 - Add New Contact
<box>
Software System: InsuraBook<br>
Use case: UC2 - Add New Contact<br>
Actor: User<br>

<pre>
MSS: 
   1.  User requests to add a new contact with details (name, phone, email, etc.)
   2.  InsuraBook validates the contact information
   3.  InsuraBook adds the contact to the list
   4.  InsuraBook displays success message with contact details
   Use case ends.

Extensions:
   2a. Required fields are missing.
      2a1. InsuraBook shows an error message indicating missing fields.
      Use case ends.

   2b. Phone number format is invalid.
      2b1. InsuraBook shows an error message about invalid phone format.
      Use case ends.

   2c. Email format is invalid.
      2c1. InsuraBook shows an error message about invalid email format.
      Use case ends.

   2d. Contact with same name and phone already exists.
      2d1. InsuraBook shows an error message about duplicate contact.
      Use case ends.
</pre>
</box>

#### UC3 - Edit Contact Details
<box>
Software System: InsuraBook<br>
Use case: UC3 - Edit Contact Details<br>
Actor: User<br>

<pre>
MSS: 
   1.  User requests to list persons
   2.  InsuraBook shows a list of persons
   3.  User requests to edit a specific person's details with new information
   4.  InsuraBook validates the new information
   5.  InsuraBook updates the contact details
   6.  InsuraBook displays success message with updated details
   Use case ends.

Extensions:
   2a. The list is empty.
   Use case ends.

   3a. The given index is invalid.
      3a1. InsuraBook shows an error message.
      Use case resumes at step 2.

   4a. New information format is invalid.
      4a1. InsuraBook shows an error message indicating invalid format.
      Use case resumes at step 3.

   4b. Edited contact would become a duplicate of an existing contact.
      4b1. InsuraBook shows an error message about duplicate contact.
      Use case resumes at step 3.
</pre>
</box>

#### UC4 - Find Contacts by Keywords
<box>
Software System: InsuraBook<br>
Use case: UC4 - Find Contacts by Keywords<br>
Actor: User<br>

<pre>
MSS: 
   1.  User requests to find contacts using search keywords
   2.  InsuraBook searches for matching contacts by name
   3.  InsuraBook displays a list of matching contacts
   Use case ends.

Extensions:
   1a. No keywords provided.
      1a1. InsuraBook shows an error message about missing keywords.
      Use case ends.

   3a. No contacts match the search criteria.
      3a1. InsuraBook displays an empty list with a message indicating no matches.
      Use case ends.
</pre>
</box>

#### UC5 - Tag a Contact
<box>
Software System: InsuraBook<br>
Use case: UC5 - Tag a Contact<br>
Actor: User<br>

<pre>
MSS: 
   1.  User requests to list persons
   2.  InsuraBook shows a list of persons
   3.  User requests to add tags to a specific person
   4.  InsuraBook validates the tag format
   5.  InsuraBook adds the tags to the contact
   6.  InsuraBook displays success message with updated contact
   Use case ends.

Extensions:
   2a. The list is empty.
   Use case ends.

   3a. The given index is invalid.
      3a1. InsuraBook shows an error message.
      Use case resumes at step 2.

   4a. Tag format is invalid (e.g., contains special characters).
      4a1. InsuraBook shows an error message about invalid tag format.
      Use case resumes at step 3.
</pre>
</box>

#### UC6 - Set Contact Priority
<box>
Software System: InsuraBook<br>
Use case: UC6 - Set Contact Priority<br>
Actor: User<br>

<pre>
MSS: 
   1.  User requests to list persons
   2.  InsuraBook shows a list of persons
   3.  User requests to set priority for a specific person (HIGH, MEDIUM, or LOW)
   4.  InsuraBook validates the priority value
   5.  InsuraBook updates the contact's priority
   6.  InsuraBook displays success message with updated priority
   Use case ends.

Extensions:
   2a. The list is empty.
   Use case ends.

   3a. The given index is invalid.
      3a1. InsuraBook shows an error message.
      Use case resumes at step 2.

   4a. Priority value is invalid.
      4a1. InsuraBook shows an error message indicating valid priority levels.
      Use case resumes at step 3.
</pre>
</box>

<div style="page-break-after: always;"></div>

### Non-Functional Requirements

#### Technical Requirements
1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should work without requiring an installer or any additional software installation beyond Java.
3. Should function offline without requiring an internet connection for core features.

#### Usability Requirements
4. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5. The system should be usable by telemarketing agents who are novice users of CLI applications, with minimal training required.
6. Error messages should be clear and provide actionable guidance to help users correct their inputs.
7. The user interface should remain responsive and not freeze during operations.

#### Reliability and Data Integrity
8. Should not lose any data during normal operations or graceful shutdowns.
9. Should automatically save data after each modification to prevent data loss.
10. Should provide data validation to prevent corruption of the contact database.
11. Should handle corrupted data files gracefully by alerting the user rather than crashing.

#### Scalability Requirements
12. Should maintain performance even when the database grows from 100 to 1000 contacts.
13. Sorting and filtering operations should scale linearly with the number of contacts.

#### Portability Requirements
14. Should store data in a human-editable text format (JSON) for easy portability and backup.
15. Should be able to run from any directory without requiring installation to a specific location.
16. Data files should be portable across different operating systems without modification.

#### Compliance and Privacy Requirements
17. Should provide a "Do Not Call" (DNC) feature to comply with telemarketing regulations.

#### Documentation Requirements
18. Should provide comprehensive user documentation covering all features and commands.
19. Should include example commands and use cases in the user guide for common tasks.
20. Developer documentation should be sufficient for new developers to set up and contribute to the project.

#### Constraints
21. The project should be completed within the semester timeline with regular iterative releases.
22. The system should work without requiring elevated permissions or administrator rights.
23. Should not use any paid third-party libraries or services.

#### Quality Requirements
24. The user interface should be intuitive enough that common tasks can be performed without referring to documentation.
25. Command syntax should be consistent across all features to reduce learning curve.

### Glossary

* **CLI**: Command Line Interface - a text-based interface where users type commands to interact with the application
* **DNC**: Do Not Call - a regulatory compliance feature that marks contacts who should not be contacted for telemarketing
* **GUI**: Graphical User Interface - a visual interface that allows users to interact through graphical elements
* **JavaFX**: A Java library used for creating desktop applications with graphical user interfaces
* **JSON**: JavaScript Object Notation - a lightweight data format used for storing and exchanging data
* **Mainstream OS**: Windows, Linux, Unix, MacOS

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

<box>

**Test: Initial launch**

**Preconditions**: Java 17 or above is installed on the system.

1. Download the jar file and copy into an empty folder. 
2. Open a command terminal and navigate to the folder containing the jar file. Run the command `java -jar InsuraBook.jar`.

**Expected**: Shows the GUI with a set of sample contacts. The window size may not be optimum.

----

**Test: Saving window preferences**

**Preconditions**: Application has been launched at least once.

1. Resize the window to an optimum size and move the window to a different location.
2. Close the window.
3. Re-launch the app by running `java -jar InsuraBook.jar` in the command terminal.

**Expected**: The most recent window size and location is retained.

----

**Test: Graceful shutdown**

**Preconditions**: Application is running with some contacts added.

1. Add or modify some contacts.
2. Close the application using the window's close button (X).
3. Re-launch the application.

**Expected**: All changes are persisted and displayed when relaunching.

----

**Test: Exit command**

**Preconditions**: Application is running.

1. Type `exit` in the command box.
2. Press Enter.

**Expected**: Application closes gracefully.

</box>


### Deleting a person

<box>

**Test: Deleting a person while all persons are being shown**

**Preconditions**: List all persons using the `list` command. Multiple persons in the list.

1. Execute `delete 1`.

**Expected**: First contact is deleted from the list. Details of the deleted contact shown in the message box.

----

**Test: Delete with invalid index zero**

**Preconditions**: List all persons using the `list` command. Multiple persons in the list.

1. Execute `delete 0`.

**Expected**: No person is deleted. Error message: "Invalid command format! Index must be a positive integer."

----

**Test: Delete with negative index**

**Preconditions**: List all persons using the `list` command. Multiple persons in the list.

1. Execute `delete -1`.

**Expected**: No person is deleted. Error message shown indicating invalid index.

----

**Test: Delete with out-of-bounds index**

**Preconditions**: List all persons using the `list` command. Multiple persons in the list.

1. Execute `delete 999` (where 999 is larger than the list size).

**Expected**: No person is deleted. Error message: "The person index provided is invalid."

----

**Test: Delete without index**

**Preconditions**: Application is running.

1. Execute `delete`.

**Expected**: No person is deleted. Error message about invalid command format shown.

----

**Test: Delete with non-numeric index**

**Preconditions**: Application is running.

1. Execute `delete abc`.

**Expected**: No person is deleted. Error message about invalid command format shown.

----

**Test: Deleting from filtered list**

**Preconditions**: Use `find` command to filter the list (e.g., `find Alice`).

1. Note the persons shown in the filtered list.
2. Execute `delete 1`.

**Expected**: First contact in the filtered list is deleted, not necessarily the first in the full list.

----

**Test: Deleting last person in list**

**Preconditions**: List has at least one person. Note the size of the list.

1. Execute `delete N` where N is the size of the list.

**Expected**: Last contact is deleted successfully. List size decreases by one.

</box>

### Saving data

<box>

**Test: Dealing with missing data files**

**Preconditions**: Application has been run at least once with data saved.

1. Close the application.
2. Navigate to the data folder and delete `insurabook.json`.
3. Relaunch the application.

**Expected**: Application starts with sample data loaded. No error messages displayed.

----

**Test: Dealing with corrupted data files**

**Preconditions**: Application has been run at least once with data saved.

1. Close the application.
2. Navigate to the data folder and open `insurabook.json` in a text editor.
3. Corrupt the file by removing a closing brace `}` or adding invalid JSON.
4. Save the file and relaunch the application.

**Expected**: Application starts with an empty contact list. Warning message is logged in the console indicating that the corrupted data file could not be loaded and will be starting with an empty contact list.

----

**Test: Data persistence after adding contacts**

**Preconditions**: Application is running.

1. Add a new contact using `add n/Test Person p/91234567 e/test@example.com a/123 Test St`.
2. Close the application without using any save command.
3. Relaunch the application.

**Expected**: The newly added contact "Test Person" appears in the contact list.

----

**Test: Data persistence after editing contacts**

**Preconditions**: Application is running with at least one contact.

1. Edit an existing contact using `edit 1 n/New Name`.
2. Close the application.
3. Relaunch the application.

**Expected**: The edited contact shows "New Name" as the name.

----

**Test: Data file location**

**Preconditions**: Application has been run at least once.

1. Close the application.
2. Navigate to the application folder.
3. Check for `data/insurabook.json` file.

**Expected**: File exists at `[JAR location]/data/insurabook.json` and is readable JSON.

</box>

### Adding a person

<box>

**Test: Adding a person with all fields**

**Preconditions**: Application is running.

1. Execute `add n/John Doe p/98765432 e/johnd@example.com a/123 Main St o/Engineer age/30 pr/HIGH ib/UPPER t/friend t/colleague`.

**Expected**: New contact "John Doe" is added with all specified fields. Success message displayed with contact details.

----

**Test: Adding a person with only required fields**

**Preconditions**: Application is running.

1. Execute `add n/Jane Smith p/87654321 e/janes@example.com a/456 Second Ave`.

**Expected**: New contact "Jane Smith" is added. Optional fields (occupation, age, priority, income bracket) use default values.

----

**Test: Adding a person with duplicate name and phone**

**Preconditions**: Contact "Alice Tan" with phone "91234567" already exists.

1. Execute `add n/Alice Tan p/91234567 e/different@email.com a/Different Address`.

**Expected**: Error message: "This person already exists in the address book."

----

**Test: Adding a person with invalid phone number**

**Preconditions**: Application is running.

1. Execute `add n/Bob Lee p/123 e/bob@example.com a/789 Third St`.

**Expected**: Error message about invalid phone number format.

----

**Test: Adding a person with invalid email**

**Preconditions**: Application is running.

1. Execute `add n/Charlie Brown p/98765432 e/invalid-email a/101 Fourth Ave`.

**Expected**: Error message about invalid email format.

----

**Test: Adding a person with missing required fields**

**Preconditions**: Application is running.

1. Execute `add n/David Lee`.

**Expected**: Error message indicating missing required field (phone number).

</box>

### Editing a person

<box>

**Test: Editing a person's name**

**Preconditions**: List has at least one person.

1. Execute `edit 1 n/New Name`.

**Expected**: First contact's name is changed to "New Name". Success message displayed.

----

**Test: Editing multiple fields**

**Preconditions**: List has at least one person.

1. Execute `edit 1 p/99998888 e/newemail@example.com pr/HIGH`.

**Expected**: First contact's phone, email, and priority are updated. Success message displayed.

----

**Test: Editing with invalid index**

**Preconditions**: List has N persons.

1. Execute `edit 999 n/Test` (where 999 > N).

**Expected**: Error message: "The person index provided is invalid."

----

**Test: Editing to create duplicate**

**Preconditions**: Two contacts exist: "Alice" with phone "91111111" and "Bob" with phone "92222222".

1. Execute `edit 2 n/Alice p/91111111`.

**Expected**: Error message: "This person already exists in the address book."

----

**Test: Editing tags**

**Preconditions**: First contact has existing tags.

1. Execute `edit 1 t/newTag`.

**Expected**: First contact's tags are replaced with only "newTag". Previous tags removed.

----

**Test: Clearing all tags**

**Preconditions**: First contact has existing tags.

1. Execute `edit 1 t/`.

**Expected**: All tags removed from first contact.

</box>

### Finding persons

<box>

**Test: Finding by single keyword**

**Preconditions**: Contact list contains "Alice Tan" and "Bob Lee".

1. Execute `find Alice`.

**Expected**: Only "Alice Tan" is shown in the filtered list.

----

**Test: Finding by multiple keywords**

**Preconditions**: Contact list contains "Alice Tan", "Alice Wong", and "Bob Lee".

1. Execute `find Alice Bob`.

**Expected**: "Alice Tan", "Alice Wong", and "Bob Lee" are shown.

----

**Test: Case-insensitive search**

**Preconditions**: Contact list contains "Alice Tan".

1. Execute `find alice`.

**Expected**: "Alice Tan" is found and displayed.

----

**Test: Finding with no matches**

**Preconditions**: Application is running with contacts.

1. Execute `find NonexistentName`.

**Expected**: Empty list displayed with message "0 persons listed!"

----

**Test: Finding without keywords**

**Preconditions**: Application is running.

1. Execute `find`.

**Expected**: Error message about invalid command format.

----

**Test: Returning to full list after find**

**Preconditions**: A find operation has filtered the list.

1. Execute `list`.

**Expected**: Full contact list is displayed again.

</box>

### Priority management

<box>

**Test: Setting priority to HIGH**

**Preconditions**: List has at least one person.

1. Execute `edit 1 pr/HIGH`.

**Expected**: First contact's priority is set to HIGH. Priority indicator updated in display.

----

**Test: Setting priority to MEDIUM**

**Preconditions**: List has at least one person.

1. Execute `edit 1 pr/MEDIUM`.

**Expected**: First contact's priority is set to MEDIUM.

----

**Test: Setting priority to LOW**

**Preconditions**: List has at least one person.

1. Execute `edit 1 pr/LOW`.

**Expected**: First contact's priority is set to LOW.

----

**Test: Setting invalid priority**

**Preconditions**: List has at least one person.

1. Execute `edit 1 pr/URGENT`.

**Expected**: Error message about invalid priority value. Valid values shown.

</box>

### Income bracket management

<box>

**Test: Setting income bracket**

**Preconditions**: List has at least one person.

1. Execute `edit 1 ib/UPPER`.

**Expected**: First contact's income bracket is set to UPPER.

----

**Test: Testing all income bracket levels**

**Preconditions**: List has at least one person.

1. Execute each command: `edit 1 ib/LOWER`, `edit 1 ib/MIDDLE`, `edit 1 ib/UPPER`.

**Expected**: Each command successfully updates the income bracket.

----

**Test: Setting invalid income bracket**

**Preconditions**: List has at least one person.

1. Execute `edit 1 ib/RICH`.

**Expected**: Error message about invalid income bracket. Valid values shown.

</box>

### Do Not Call (DNC) tag management

<box>

**Test: Adding DNC tag**

**Preconditions**: First contact does not have DNC tag.

1. Execute `edit 1 t/Do Not Call`.

**Expected**: Contact is marked with DNC indicator. Special visual indication shown.

----

**Test: Removing DNC tag**

**Preconditions**: First contact has DNC tag.

1. Execute `edit 1 t/` to remove all tags, or replace with other tags.

**Expected**: DNC indicator removed from contact display.

----

**Test: DNC tag persistence**

**Preconditions**: Contact with DNC tag exists.

1. Close and relaunch application.

**Expected**: DNC tag and indicator persist across sessions.

</box>

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Future Enhancements**

Team size: 5

1. **Allow multiple phone number fields with international format support:**
   Currently, the phone number field only accepts numeric digits and each contact can have only one
   phone number. However, telemarketers frequently need to store multiple contact numbers (home, mobile,
   office) for the same person and require support for special characters like `-`, `+`, and `( )` to
   accommodate international country codes and phone extensions.

2. **Add confirmation dialog for clear command:**
   Currently, the `clear` command immediately deletes all contacts from InsuraBook without
   requesting user confirmation. A single typo or accidental command execution can permanently
   delete an insurance agent's entire client database. Although data is saved to file after
   each operation, there is no recovery mechanism to restore cleared contacts. This poses a
   significant risk for professionals managing hundreds of client records. To address this, we
   plan to implement a confirmation dialog that requires the user to explicitly confirm the
   action before deletion proceeds.

3. **Reduce restriction on DNC feature:**
   Currently, the "Do Not Call" (DNC) feature prevents editing any field of contacts tagged with DNC, including
   non-call-related information like address or occupation. For example, if a client with a DNC tag moves, the agent
   cannot update the address without removing the DNC tag first. To address this, we plan to reduce the restrictions to
   only prevent editing of name and phone number fields, while allowing updates to other contact information. We also
   plan to add a dedicated reversal command to eliminate the need to delete/re-add workaround.
