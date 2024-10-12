# PhoneBook - Contacts Management System

This is a Java-based Contacts Management System that allows users to manage and organize their phone contacts efficiently. The project provides functionality for adding, importing, and viewing contact details, with additional user access controls.

## Features

- **User Authentication:** Users can securely log in to access their contact information.
- **Add/Edit Contacts:** Add new contacts or modify existing ones.
- **Import Contacts:** Import contacts from external sources.
- **Contact Search:** Quick search functionality using a trie data structure for fast lookup.
- **Access Control:** Assign access levels to different users.
- **User Interface:** A simple console-based interface for managing the phonebook.

## Project Structure

- `mainClass.java`: The main entry point for the application.
- `phoneBook.java`: Core functionality for managing the phonebook, including contact operations.
- `contactNode.java`: Defines the structure of a contact in the phonebook.
- `trie.java`: Implements the trie data structure used for fast contact searching.
- `userLoginPageClass.java`: Manages the login page and user authentication.
- `welcomePageClass.java`: Displays the welcome page after successful login.
- `contactImportPageClass.java`: Handles contact import functionality.
- `goodbyePageClass.java`: Manages the exit flow of the application.
- `design.drawio`: Contains the application design in diagram format.
- `softwarePath.drawio`: Represents the software architecture.

## How to Run

### Prerequisites:

- Java Development Kit (JDK) installed.
- An IDE such as Eclipse or IntelliJ IDEA.

### Steps:

1. Clone or download the project.
2. Open the project in your Java IDE.
3. Run the `mainClass.java` file to start the application.

### Running via Command Line:

1. Navigate to the project directory.
2. Compile the Java files using:

   ```bash
   javac mainClass.java

Run the application:

```bash
java mainClass


## Project Files

- **Class Files:** Precompiled `.class` files are included for each corresponding `.java` file.
- **Contact Info:** `contactInfo` contains stored contact data.
- **Login Data:** `credentialInfo` contains user credentials for login.

## Diagrams

- **Design Overview:** `design.drawio` contains a high-level design of the application's architecture.
- **Software Path:** `softwarePath.drawio` outlines the software components and how they interact.
