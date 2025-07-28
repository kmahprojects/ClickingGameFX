# ClickingGameFX

This is a JavaFX-based clicking game where the player tries to click moving balls before they reach the edge of the screen. The game features three balls, speed increases with successful hits, and a simple pause/reset functionality.

---

## Project Structure

src/
 └── main/
      ├── java/
      │    └── lab9/
      │         ├── module-info.java
      │         └── lab9/
      │              ├── ClickingGameFX.java
      │              
      └── resources/
           └── lab9/
                └── lab9/
                     └── hello-view.fxml

*Note:* The nested `lab9/lab9` folder structure is due to original package naming.

---

## Requirements

- Java 11 or higher is recommended.
- JavaFX SDK installed on your machine or a JDK with JavaFX bundled.

---

## Setting Up JavaFX

JavaFX is not included in the standard JDK distribution. To run this project, you need to:

1. Download the JavaFX SDK from [OpenJFX](https://openjfx.io/) or install a JDK that bundles JavaFX, such as [LibericaFX](https://bell-sw.com/pages/downloads/#/javafx) or [ZuluFX](https://www.azul.com/downloads/?package=jdk-fx).

2. Note the path to your JavaFX SDK `lib` folder (e.g., `/path/to/javafx-sdk-20/lib`).

---

## Compiling the Game

From the root directory (where `src` is located), compile the Java source files with:

```bash
javac -d out --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls src/main/java/lab9/lab9/*.java
```


This compiles the code into an `out` folder.

---

## Running the Game

Run the program using:

```bash
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp out lab9.lab9.ClickingGameFX
```

Make sure to replace `/path/to/javafx-sdk/lib` with your actual JavaFX SDK library path.

---

## Using an IDE

If you prefer using IntelliJ IDEA or other IDEs, you can configure the JavaFX SDK in your project settings to run the application directly without the command line flags.

---

## License & Author

Created by Kevin Mah as a class project.

