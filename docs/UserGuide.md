---
  layout: default.md
  title: "User Guide"
  pageNav: 3
--- 

# InsuraBook User Guide

<!-- * Table of Contents -->
<page-nav-print />

**Every sales day starts the same:** a long list of leads, a tighter list of callbacks, and too much information - spreadsheets, sticky notes, scattered files. In the middle of a call with an important client, one cannot afford to hunt for the right number, the last note, or the correct spelling of a client’s name. Miss a detail and just like that, a warm lead goes cold!

InsuraBook exists to stop that from happening. Designed for busy **telemarketing agents** who sell insurance - InsuraBook is tailor-made to handle **large lists of clients and new leads**. This means that you can focus on your sales calls as the information you need is always where you expect it to be.

InsuraBook keeps essential **client names, phone numbers and other miscellaneous information** organized. It's **optimized for use via a Command Line Interface (CLI)** - making it easy to find, add, and update records efficiently using simple commands. InsuraBook transforms messy binders filled with client details into a steady, repeatable workflow optimized for high-volume calling - so busy agents can spend their energy making deals, and not chasing information.

<div style="text-align: center; font-style: italic; font-size: 1.1em; margin: 20px 0; padding: 15px; background-color: #f0f0f0; border-left: 4px solid #0366d6;">
<strong>InsuraBook is a one-stop solution for telemarketers and their daily contact management needs.</strong>
</div>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## How to Use This Guide

<div style="background-color: #f5f5f5; padding: 20px; border-radius: 8px; margin-bottom: 15px;">

**New to InsuraBook?**

* Start with the [Quick start](#quick-start) section to install and run the application.
* Refer to the [Command summary](#command-summary) for a quick overview of all commands.

</div>

<div style="background-color: #f5f5f5; padding: 20px; border-radius: 8px; margin-bottom: 15px;">

**Experienced user looking for specific information?**

* Use the [Features](#features) section to find detailed explanations of specific commands.
* Check out [Parameters](#parameters) for information on the available contact parameters.

</div>

<div style="background-color: #f5f5f5; padding: 20px; border-radius: 8px; margin-bottom: 15px;">

**Need help with something specific?**

* Browse the [FAQ](#faq) section for answers to common questions.
* Check the [Known issues](#known-issues) section if you're experiencing problems.

</div>

<box type="tip" seamless>

**Pro Tip:** Use `Ctrl+F` (or `Cmd+F` on Mac) to search for specific keywords/commands in this guide.

</box>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>

   **To check if Java 17+ is installed:**
   Open a command terminal/command prompt and type the following:
   ```
   java -version
   ```
   If Java 17 or higher is installed, you should see output similar to:

   ```
   java version "17.0.x" or higher
   ```
   If Java is not installed, or the Java version is below Java 17, proceed to install Java 17+.
 
   **Installation links:**
   * **Mac users:** Follow the precise installation guide [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
   * **Windows/Linux users:** Download Java 17+ from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/install/).
<br><br>
2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F15a-2/tp/releases/tag/v1.5).

3. Copy the file to the folder you want to use as the _home folder_ for your InsuraBook.

4. **To run the application:**<br>
    Open a command terminal/command prompt.<br>
    Navigate to the folder containing the `.jar` file:
    ```
    cd path/to/your/folder
    ```
    Run the application:
    ```
    java -jar insurabook.jar
    ```
    A GUI similar to the one displayed below should appear within a few seconds. Note how the app contains some sample data.<br>
    <div style="text-align: center; ">
        <img src="images/features/Labelled.png" alt="Ui" style="max-width: 70%; height: auto; margin: 20px 0px;"/>
        <p>Insurabook User Interface labelled</p> 
    </div>

5. Type the command in the command box and press Enter to execute it, e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe`, with phone number `98765432`, email `johnd@example.com`, and address `John street, block 123, #01-01`.

   * `priority 1 HIGH` : Sets the priority of the 1st contact to HIGH.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

Click [here](#) to go back to the content page.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Parameters 

The following are the parameters we will be using in InusraBook.

### Reading Parameter Formats

Before diving into each parameter, here's how to read the command formats:

| Format | Meaning | Example |
|--------|---------|---------|
| `PARAMETER` | Required, must provide | `n/NAME` must be included |
| `[PARAMETER]` | Optional, can skip | `[e/EMAIL]` can be omitted |
| `[PARAMETER]...` | Can repeat multiple times | `[t/TAG]...` → `t/friend t/vip` |
| `PARAMETER/` (empty) | Clears the field | `i/` removes income bracket |

### Common Parameters 
The following are **compulsory** parameters to the `add` and `edit` command.

Parameter | Description | Format | Constraints
----------|-------------|--------|------------
`n/NAME` | Client's name | `n/NAME` | Must consist of alphabets or the following characters: `-`/ `'`/ `/`/ `@`.
`p/PHONE_NUMBER` | Client's phone number | `p/PHONE_NUMBER` | Must be a number between 4-17 digits.


### Optional Parameters: PERSON_PARAM 
The following are **optional** parameters to the `add` and `edit` command. We denote them as `[PERSON_PARAM]`

Parameter | Description | Format | Constraints
----------|-------------|--------|------------
`e/EMAIL` | Email address | `e/EMAIL` | Must be a valid email format (e.g., `user@example.com`).
`a/ADDRESS` | Physical address | `a/ADDRESS` | Any text string.
`o/OCCUPATION` | Client's occupation | `o/OCCUPATION` | Any text string.
`age/AGE` | Client's age | `age/AGE` | Must be a non-negative integer between 10-120 inclusive.
`lc/LAST_CONTACTED` | Last contact date | `lc/LAST_CONTACTED` | Must not be a future date. Format: `YYYY-MM-DD` (e.g., `2023-10-15`)
`pr/PRIORITY` | Contact priority level | `pr/PRIORITY` | Must be one of: `NONE`, `LOW`, `MEDIUM`, `HIGH` (case-insensitive).
`i/INCOME_BRACKET` | Income bracket classification | `i/INCOME_BRACKET` | Must be one of: `NONE`, `LOW`, `MIDDLE`, `HIGH` (case-insensitive).
`t/TAG` | Tags for categorization | `t/TAG` | Alphanumeric and spaces allowed. Maximum 30 characters. **Can be used multiple times** (e.g., `t/friend t/colleague`).

<box type="info" seamless>

**Notes about PERSON_PARAMS:**
* These parameters can be used in any combination with the `add` and `edit` commands.
* Parameters can be specified in any order.
* For the `edit` command, at least one parameter must be provided.
* When editing tags with the `edit` command, existing tags will be replaced (not added to).
* To remove any field, use the prefix without specifying a value after it (e.g. `t/` to remove all tags, `e/` to remove email, `a/` to remove address, etc.).

</box>

Click [here](#) to go back to the content page.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Features
<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

<div style="page-break-after: always;"></div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

<div style="text-align: center;">
    <img src="images/helpMessage.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 70%; height: auto; margin: 20px 0px;"/>
</div>

Format: `help`

<div style="page-break-after: always;"></div>

### Adding a client: `add`

Adds a client to InsuraBook.

<div style="text-align: center;">
    <img src="images/features/addCommand.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 50%; height: auto; margin: 20px 0px;"/>
    <p>Adding a client on InsuraBook</p>
</div>

Format: `add n/NAME p/PHONE_NUMBER [PERSON_PARAMS]`

* For details on available `PERSON_PARAMS`, click [here](#optional-parameters-person-param).

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 age/28`
  * Adds a client named `John Doe` with the phone number `98765432`, email `johnd@example.com`,
  address `John street, block 123, #01-01`, age `28`.
* `add n/Betsy Crowe p/82345678 t/interested a/The Gardens at Bishan o/Engineer i/high pr/HIGH`
    * Adds a client named `Betsy Crowe` with phone number `82345678`, tagged as `interested`,
      address `The Gardens at Bishan`, occupation `Engineer`, income bracket `high`,
      and priority level `HIGH`.

<box type="info" seamless>

**Note:** `n/NAME` and `p/PHONE_NUMBER` are required for `add`. All other prefixes under `PERSON_PARAMS` are optional.
- If an optional prefix is *omitted*, that field is left unset.
- If an optional prefix is *present but has no value* (for example `e/` or `a/`) when adding a client, the application accepts and stores that field as an empty string.
</box>

<box type="warning" seamless>

**Warning:** Adding another client with the same `PHONE_NUMBER` as an existing client will be counted as a duplicate and is not allowed.

</box>

<div style="page-break-after: always;"></div>

### Listing all clients : `list`

Shows a list of all contacts sorted by index in the InsuraBook.

<div style="text-align: center;">
    <img src="images/features/listCommand.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 50%; height: auto; margin: 20px 0px;"/>
    <p>Listing all clients on InsuraBook</p>
</div>

Format: `list [pr/asc] [pr/desc] [i/asc] [i/desc]`<br>

Examples:
* `list` 
  * Shows all clients sorted by index.
* `list pr/asc`  
  * Shows all clients sorted by priority in ascending order.
* `list pr/desc`  
  * Shows all clients sorted by priority in descending order.
* `list i/asc`  
  * Shows all clients sorted by income bracket in ascending order.
* `list i/desc`  
  * Shows all clients sorted by income bracket in descending order.

<box type="note" seamless>

**Note:** Clients that do not have values for `PRIORITY` and `INCOME_BRACKET` will be listed at the bottom when using sorting commands (for example, `list pr/asc` or `list i/desc`).

</box>

<box type="warning" seamless>

**Warning:** You can only have up to **one** of the optional parameters.

</box>

<div style="page-break-after: always;"></div>

### Editing a client : `edit`

Edits an existing contact in InsuraBook.

<div style="text-align: center;">
    <img src="images/features/editCommand.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 50%; height: auto; margin: 20px 0px;"/>
    <p>Editing details of the first client on InsuraBook</p>
</div>

Format: `edit INDEX [n/NAME] [p/PHONE] [PERSON_PARAMS]`

* For details on available `PERSON_PARAMS`, click [here](#optional-parameters-person-param).
* Edits the client at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com`
    * Edits the phone number and email address of the first contact to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/ pr/HIGH`
    * Edits the name of the second contact to be `Betsy Crower`, clears all existing tags and changes the priority to HIGH.

<box type="warning" seamless>

**Warning:** When editing tags, the existing tags of the contact will be removed i.e adding of tags is not cumulative.

</box>
<box type="tip" seamless>

**Tip:** You can remove any optional field by using the prefix without a value (e.g., `edit INDEX t/` removes all tags, `edit INDEX e/` removes email, `edit INDEX a/` removes address).

</box>

<div style="page-break-after: always;"></div>

### Locating clients by fields: `find`

Finds clients whose fields contain any of the given keywords.

<div style="text-align: center;">
    <img src="images/features/findCommand.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 50%; height: auto; margin: 20px 0px;"/>
    <p>Searching for a client by name</p>
</div>

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Partial words will be matched e.g. `find Han` will match `Hans`
* Clients matching at least one keyword will be returned (i.e. `OR` search).
    * e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Values from fields `NAME`, `PHONE`, `EMAIL`, `ADDRESS`, `OCCUPATION`, `AGE`, `LAST_CONTACTED`, `TAG` are searched.

Examples:
* `find John` 
  * Returns `john` and `John Doe`
* `find 87438807` 
  * Returns `Alex Yeoh` as it matches their phone number.
* `find family` 
  * Returns `David Li` as it matches their assigned tags.
* `find Alex family` 
  * Returns `Alex Yeoh`, `David Li`.
* `find alex david` 
  * Returns `Alex Yeoh`, `David Li`.
* `find 2023-12-25` 
  * Returns clients with `LAST_CONTACTED` = `2023-12-25`.<br>

<div style="page-break-after: always;"></div>

### Deleting a client : `delete`

Deletes the specified contact from InsuraBook.

<div style="text-align: center;">
    <img src="images/features/deleteCommand.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 50%; height: auto; margin: 20px 0px;"/>
    <p>Deleting the first client from InsuraBook</p>
</div>

Format: `delete INDEX`

* Deletes the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` 
  * Deletes the second contact in InsuraBook, after carrying out the `list` command.
* `find Betsy` followed by `delete 1` 
  * Deletes the first contact in the results of the `find` command.

<div style="page-break-after: always;"></div>

### Editing a tag: `tag`

Changes the tags of an existing client in Insurabook. This is a convenient shortcut for the edit command when you only want to change the tags.

<div style="text-align: center;">
    <img src="images/features/tagCommand.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 50%; height: auto; margin: 20px 0px;"/>
    <p>Adding a tag to the first client on InsuraBook</p>
</div>

Format: `tag INDEX t/TAG_NAME [t/TAG_NAME]...`

* Changes the tags of the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …​
* `tag_name` must be  Alphanumeric and spaces allowed. 
    * Maximum 30 characters.
    * Case-insensitive (eg, Interested = interested). 
    * Leading/trailing spaces are trimmed.
* This command is equivalent to `edit INDEX t/tag_name`.

Examples:
* `tag 1 t/interested` Sets the tag of the first contact to `interested`.
* `tag 2 t/follow up` Sets the tag of the second client to `follow up`.
* `tag 5 t/do not call` Sets the tag of the fifth client to `do not call`.
* `tag 7 t/follow up t/interested` Sets the tags of the seventh client to `follow up` and `interested`.

<box type="note" seamless>

**Note:** The `tag` command cannot be used to clear existing tags or remove all tags by supplying an empty `t/`. To remove all tags, use the `edit` command with an empty tag prefix, e.g. `edit INDEX t/`.

</box>


<box type="tip" seamless>

**Tip:** Use the `tag` command for quick tag changes, or the `edit` command when changing multiple fields at once.
</box>

<box type="warning" seamless>

**Warning:** Similar to the `edit`command, when adding tags, the existing tags of the contact will be removed i.e adding of tags is not cumulative.

</box>

<div style="page-break-after: always;"></div>

### Marking a contact as Do Not Call: `dnc`

Marks a contact as Do Not Call (DNC) in InsuraBook.

<div style="text-align: center;">
    <img src="images/features/dncCommand.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 50%; height: auto; margin: 20px 0px;"/>
    <p>Marking the second client on InsuraBook as DNC</p>
</div>

Format: `dnc INDEX`

* Marks the contact at the specified `INDEX` as Do Not Call.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …​
* A special "Do Not Call" tag (displayed in red) will be applied to the contact.
* The DNC status **cannot be removed** from a contact.

Examples:
* `dnc 1` Marks the first contact on the list as Do Not Call.
* `dnc 3` Marks the third contact on the list as Do Not Call.

<box type="warning" seamless>

**Warning:** The `dnc` command applies an irreversible Do Not Call status by setting a special DNC tag. Executing `dnc INDEX` will remove all other tags on the contact. Because DNC contacts cannot be edited, tags cannot be restored.

</box>

<box type="tip" seamless>

**Tip:** If a contact was wrongly assigned as DNC, delete the contact and re-add them to the system.
</box>

<div style="page-break-after: always;"></div>

### Editing the priority: `priority`

Changes the priority of an existing client in InsuraBook. This is a convenient shortcut for the edit command when you only want to change the priority.

<div style="text-align: center;">
    <img src="images/features/priorityCommand.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 50%; height: auto; margin: 20px 0px;"/>
    <p>Setting the priority of the first client on InsuraBook as high</p>
</div>

Format: `priority INDEX PRIORITY`

* Changes the priority of the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …​
* `PRIORITY` must be one of: `NONE`, `LOW`, `MEDIUM`, `HIGH` (case-insensitive).

Examples:
* `priority 1 HIGH` 
  * Sets the priority of the first contact to `HIGH`.
* `priority 3 NONE` 
  * Sets the priority of the third contact to `NONE`.
* `priority 2 medium` 
  * Sets the priority of the second contact to `MEDIUM` (case-insensitive).

<box type="tip" seamless>

**Tip:** Use the `priority` command for quick priority changes, or the `edit` command when changing multiple fields at once.
</box>

<div style="page-break-after: always;"></div>

### Clearing all entries : `clear`

Clears all entries from InsuraBook.

<div style="text-align: center;">
    <img src="images/features/clearCommand.png" alt="Ui" style="border: 2px solid black; border-radius: 10px; max-width: 50%; height: auto; margin: 20px 0px;"/>
    <p>Clearing details of all clients from InsuraBook</p>
</div>

Format: `clear`

<!-- Insert after the Clear command description in `docs/UserGuide.md` -->
<box type="warning" seamless>

**Warning:** The `clear` command permanently deletes *all* contacts and cannot be undone. Only use this command if you are sure you want to permanently remove all data. Confirm carefully before proceeding.

</box>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

InsuraBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

InsuraBook data are saved automatically as a JSON file `[JAR file location]/data/insurabook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, InsuraBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br><br>
Furthermore, certain edits can cause the InsuraBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

Click [here](#) to go back to the content page.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER [PERSON_PARAMS]` <br><br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 pr/HIGH t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br><br> e.g., `delete 3`
**DNC**    | `dnc INDEX`<br><br> e.g., `dnc 1`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE] [PERSON_PARAMS]`<br><br> e.g.,`edit 2 n/James Lee e/jameslee@example.com pr/MEDIUM`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br><br> e.g., `find James Jake`
**List**   | `list [pr/asc] [pr/desc] [i/asc] [i/desc]`
**Tag**    | `tag INDEX t/TAG_NAME [t/TAG_NAME]...` <br><br> e.g., `tag 1 t/interested t/follow up`
**Priority** | `priority INDEX PRIORITY`<br><br> e.g., `priority 1 HIGH`
**Help**   | `help`

Click [here](#) to go back to the content page.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite `[JAR file location]/data/insurabook.json` with the file that contains the data of your previous InsuraBook home folder.

Click [here](#) to go back to the content page.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

Click [here](#) to go back to the content page.
