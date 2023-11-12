---
layout: page
title: Developer Guide
---
##Table of Contents
1. [Acknowledgements](#acknowledgements)
2. [Setting up](#setting-up-getting-started)
3. [Design](#design)
    1. [Architecture](#architecture)
    2. [UI](#ui-component)
    3. [Logic](#logic-component)
    4. [Model](#model-component)
    5. [Storage](#storage-component)
    6. [Common Classes](#common-classes)
4. [Implementation](#implementation)
5. [Documentation](#documentation-logging-testing-configuration-dev-ops)
6. [Appendix: Requirements](#appendix-requirements)
    1. [Product scope](#product-scope)
    2. [User Stories](#user-stories)
    3. [Use Cases](#use-cases)
    4. [Non-Functional Requirements](#non-functional-requirements)
    5. [Glossary](#glossary)
--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2324S1-CS2103T-W08-4/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2324S1-CS2103T-W08-4/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S1-CS2103T-W08-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2324S1-CS2103T-W08-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2324S1-CS2103T-W08-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2324S1-CS2103T-W08-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `PharmHubParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `PharmHubParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `PharmHubParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2324S1-CS2103T-W08-4/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the PharmHub data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `PharmHub`, which `Person` references. This allows `PharmHub` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2324S1-CS2103T-W08-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/PharmHubStorageClassDiagram.png" width="700" />

The `Storage` component,
* can save both PharmHub data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `PharmHubStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)
    * The Main Application has 3 JsonAdapted component in storage: `JsonAdaptedPerson`, `JsonAdaptedOrder`, `JsonAdaptedMedicine`.
      * `JsonAdaptedPerson` further contains `JsonAdaptedTag` and `JsonAdaptedAllergy`.
      * `JsonAdaptedOrder` further contains `JsonAdaptedStatus` and `JsonAdaptedMedicine`.`

### Common classes

Classes used by multiple components are in the `seedu.pharmHub.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.


### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedPharmHub`. It extends `PharmHub` with an undo/redo history, stored internally as an `pharmHubStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedPharmHub#commit()` — Saves the current pharmHub state in its history.
* `VersionedPharmHub#undo()` — Restores the previous pharmHub state from its history.
* `VersionedPharmHub#redo()` — Restores a previously undone pharmHub state from its history.

These operations are exposed in the `Model` interface as `Model#commitPharmHub()`, `Model#undoPharmHub()` and `Model#redoPharmHub()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedPharmHub` will be initialized with the initial pharmHub state, and the `currentStatePointer` pointing to that single pharmHub state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in PharmHub. The `delete` command calls `Model#commitPharmHub()`, causing the modified state of PharmHub after the `delete 5` command executes to be saved in the `pharmHubStateList`, and the `currentStatePointer` is shifted to the newly inserted pharmHub state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitPharmHub()`, causing another modified pharmHub state to be saved into the `pharmHubStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitPharmHub()`, so PharmHub state will not be saved into the `pharmHubStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoPharmHub()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous pharmHub state, and restores PharmHub to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial PharmHub state, then there are no previous PharmHub states to restore. The `undo` command uses `Model#canUndoPharmHub()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoPharmHub()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores PharmHub to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `pharmHubStateList.size() - 1`, pointing to the latest pharmHub state, then there are no undone PharmHub states to restore. The `redo` command uses `Model#canRedoPharmHub()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify PharmHub, such as `list`, will usually not call `Model#commitPharmHub()`, `Model#undoPharmHub()` or `Model#redoPharmHub()`. Thus, the `pharmHubStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitPharmHub()`. Since the `currentStatePointer` is not pointing at the end of the `pharmHubStateList`, all pharmHub states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire pharmHub.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

### Listing all orders

The listing all orders functionality is supported by the listPanelPlaceholder in the Ui.

On start of application, two listPanels (PersonListPanel, OrderListPanel) is created, and person list panel is attached 
to the ListPanel Placeholder as default.

On execution of the any listing commands (`listo` or `listp`), the resultant CommandResult contains details on which
panel to choose to display.

These are 3 different options for these details:
* Person - Panel will display list of people
* Order - Panel will display list of orders
* NoChange - Panel will keep whatever was there previously

Using these specifications, the CommandResult from executing `listo` and `listp` will inform the Ui of which
panel to attach to the listPanelPlaceHolder

### Adding an Order feature

#### Implementation
The adding an Order feature allows the user to add an order to a person.

#### Steps to Trigger
1. The User launches the application.
2. The User executes "addo 1 m/aspirin o/1" to add a new order.
3. The `AddOrderParser#parse()` checks whether all the prefixes and the required values are provided.
4. If the check is successful, the `ParseUtil#parseOrderNumber`, `ParseUtil#parseOrderNumber` and `ParseUtil#parseMedicine` 
will then check and create an `Index`, `OrderNumber` and `Medicine` passing it to `AddOrderCommand`.
5. Depending on whether an ignore allergy flag `ia/` is added in the command input, user can input medications in the order that the person is allergic to. 
6. `AddOrderCommand#execute` then checks if the person exist base on index and creates an Order with the
`person`, `orderNumber`, `medicine` and a `PENDING` Status, then `Model#hasOrder` also checks whether the order is a duplicated order.
7. If the order does not exist, then the `Model#addOrder` adds the order into the order list.

#### Design Consideration
1. The add order feature includes the ignore allergy flag to allow further flexibility for the pharmacist when assigning orders
to people. This is because they may have other considerations when it comes to assigning a medication for a person.
2. The add order feature do not allow duplicated order as we believe that each order should be distinct base on 
their order number, ensuring that no orders go missing or forgotten.

The following sequence diagram shows how `addo` works on an example input. `addo 1 m/aspirin o/1000`

![AddOrderSequenceDiagram](images/AddoSequenceDiagram.png)

### Finding an Order Feature

#### Implementation
The finding an Order feature allows the user to find other base on either the orderStatus, medication in the order or both.

#### Steps to Trigger
1. The User Launches the application. 
2. The User decides to find/filter through the order list after adding multiple orders.
3. The User executes "findo s/pd m/pan ibuprofen" to find orders that has `PENDING` status and either `PANADOL` or `IBUPROFEN` in the order.
4. The `FindOrderCommandParser#parse()` checks whether all the prefixes and the required values are provided.
5. If the check is successful, depending on the User input if `s/` is present `ParseUtil#parseStatus` will return a
`Status`, and if `m/` is present `ParseUtil#parseMedicines` will return the `Medicine/s`.
6. The `Status` and `Medicine` will then be passed to the FindOrderCommand.
7. The `Model#updateFilteredOrder` will then take either or both `Status` and `Medicine` as predicates to filter through the order list and return valid orders.
8. The filtered order list will then be returned and shown on the Displayed list.

#### Design Consideration
1. The find order feature allows independent Predicate finding.
   1. i.e. you can find order base on only `Status` or `Medicine`.
   2. We implemented this format to expend the utility of the feature.

![FindOrderSequenceDiagram](images/FindoSequenceDiagram.png)


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Pharmacists
* Working remotely

**Value proposition**: Provide easy access to patient details and allows pharmacists to quickly and accurately process
medication orders for the patients, optimised for CLI use.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                                         | So that I can…​                                                             |
|-------|--------------------------------------------|----------------------------------------------------------------------|-----------------------------------------------------------------------------|
| `* * *` | Pharmacist                                 | Add a new patient into PharmHub                               | Keep track of all information of my patients                                |
| `* * *` | Pharmacist                                 | Add a medication order for a patient                                 | keep track medications orders of patients                                   |
| `* * *` | Pharmacist                                 | Add allergies of a patient towards certain medications               | keep track of patient allergies                                             |
| `* * *` | Pharmacist                                 | View details of a medication order                                   |                                                                             |
| `* * *` | Pharmacist                                 | View the status of an order                                          | I can track the state of an order                                           |
| `* * *` | Pharmacist                                 | Edit status of an order                                              | Update the order records with the most current information                  |
| `* * *` | Pharmacist                                 | Edit the details of a patient                                        | Update records with the most current information                            |
| `* * *` | Pharmacist                                 | View all medication orders                                           | get a quick overview of all medication orders from all patients             |
| `* * *` | Pharmacist                                 | Delete a patient from my pharmHub                                 | remove a patient that is no longer under my care                            |
| `* * *` | New user                                   | View a summary of all the commands                                   | recall how to use the application                                           |
| `* * *` | Pharmacist                                 | Delete an order from the system                                      | Remove erroneous orders                                                     |
| `* * *` | Detail-oriented Pharmacist                 | Be alerted if a patient is given a medication that he is allergic to | Detect and prevent erroneous orders from going through                      |
| `* *` | Busy Pharmacist                            | Abbreviate medication names                                          | Maximise my efficiency                                                      |
| `* *` | Pharmacist                                 | View all details of a patient                                        | get the full patient profile, if required                                   |
| `* *` | Pharmacist                                 | Search orders by various attributes                                  | manage and convey information about orders to stakeholders efficiently      |
| `* *` | Pharmacist                                 | See the number of unfulfilled orders                                 | keep track of my progress in dispensing orders                              |
| `* *` | Pharmacist                                 | Add priorities to orders                                             | keep track of which orders I should prioritise                              |
| `* *` | Pharmacist                                 | Filter orders by their statuses                                      | manage my orders efficiently                                                |
| `* *` | Pharmacist                                 | Sort orders on various categories                                    | maximise my efficiency                                                      |
| `*`   | Pharmacist                                 | Edit an order                                                        | correct or update an added order                                            |
| `*`   | Pharmacist                                 | Track my medication inventory                                        | I have easy access to the amount of medication I have                       |
| `*`   | Pharmacist                                 | Calculate estimated time an order will take to ship                  | Notify patients of an estimated wait time before receiving their medication |
*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `PharmHub` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a person**

**MSS**

1.  User requests to list persons
2.  PharmHub shows a list of persons
3.  User requests to delete a specific person in the list
4.  PharmHub deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. PharmHub shows an error message.

      Use case resumes at step 2.

**Use Case: Add Medication Order for a Patient**

**MSS**

1. User, a pharmacist, requests to list persons.
2. PharmHub shows a list of persons.
3. User selects a patient from the list to add a medication order for.
4. PharmHub presents a form for entering medication order details, including medication name and dosage.
5. User enters the required order details and submits the order request.
6. PharmHub validates the order information, including the medication name.
7. PharmHub checks if the prescribed medication matches any of the patient's allergies.
8. If the prescribed medication is found in the patient's allergy list, PharmHub displays a warning message.
9. Pharmacist acknowledges the allergy warning but chooses to proceed by adding an "IA" (Ignore Allergy) flag to the command.
10. Pharmacist confirms the order by resubmitting with the "IA" flag.
11. PharmHub creates the order with the provided information, including the "IA" flag.
12. PharmHub confirms the successful creation of the order, noting the allergy warning and the "IA" flag.
13. Pharmacist reviews the order details, including patient information and prescribed medication.
14. User confirms the order request.
15. PharmHub creates the medication order for the patient and stores it in the system.
16. PharmHub confirms the successful creation of the order.
17. The system sends an order confirmation to the pharmacist.

**Use case ends.**

**Extensions**

* 2a. The list of persons is empty.

  Use case ends.

* 3a. The given index to select a patient is invalid.
    * 3a1. PharmHub shows an error message indicating an invalid index.
    * 3a2. Use case resumes at step 3.

* 3b. The given medication name is invalid.
    * 3b1. PharmHub shows an error message indicating an invalid medication name.
    * 3b2. Use case resumes at step 4.

In this combined use case, a pharmacist adds a medication order for a patient using PharmHub. 
The system first allows the pharmacist to select a patient from the list and provide order details. 
It then checks for potential contraindications based on the patient's allergies and issues a warning if necessary. 
The pharmacist can acknowledge the warning and proceed with the order by adding an "IA" flag to the command. 
Once confirmed, the system records the order and sends a confirmation to the pharmacist. The use case also addresses various extensions for error handling.

=======
      Use case resumes at step 2.

<br><br>**Use case: Update patient Order Status**

**MSS**

1.  User requests to list Order
2.  PharmHub shows a list of Order
3.  User requests to update order status for a order in the list
4.  PharmHub updates order status for the corresponding order

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. PharmHub shows an error message.

      Use case resumes at step 2.

* 3b. The given status is invalid.

    * 3a1. PharmHub shows an error message.

      Use case resumes at step 2.
      
*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should be able to hold up to 1000 orders without a noticeable sluggishness in performance for typical usage.
    *{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
