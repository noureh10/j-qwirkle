# j-qwirkle

A simple Java implementation of the famous puzzle game Qwirkle.
## What's Qwirkle ?

Qwirkle is a tile-based game for two to four players that shares some characteristics with the games Rummikub and Scrabble. The game consists of aligning tiles with symbols of identical shape or color in order to create combinations that yield maximum points.

You can learn more about the rules on [this website](https://www.officialgamerules.org/qwirkle).
## How to run the program ?

To run the Qwirkle game, you'll need the Java Development Kit (JDK) installed on your machine. The JDK includes essential tools like `javac` for compiling Java source code and `java` for running Java applications.

### Prerequisites

- **Java Development Kit (JDK):** Ensure that you have the JDK installed on your system. You can download the latest version from the [official Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html) or use an alternative like OpenJDK.

### Steps

1. **Download the Source Code:**
   
Clone or download the Qwirkle game source code from the repository.

   ```bash
   git clone https://github.com/noureh10/j-qwirkle.git
   ```

2. **Compiling the project:**

Navigate to the root of the project and write those commands :

   ```bash
   javac -d target/classes src/main/java/g58146/qwirkle/model/*.java
   javac -d target/classes -cp target/classes src/main/java/g58146/qwirkle/view/View.java
   ```

3. **Run the program:**

Still on the root of the project, write this command : 
   
   ```bash
   java -cp target/classes g58146.qwirkle.app.App
   ```

There you have it, terminal based Qwirkle.

## Screenshots

<img src="https://raw.githubusercontent.com/noureh10/j-qwirkle/master/screenshots/C1.PNG" width="30%">
<img src="https://raw.githubusercontent.com/noureh10/j-qwirkle/master/screenshots/C2.PNG" width="29.7%">

## What's coming to this project in the future

The next two things that will be coming in a near future for this project is the implementation of a proper interface ; I'll be using [JavaFX](https://github.com/openjdk/jfx) for it. After that, the final step would be to introduce a online mode with the help of [SNF4J](https://github.com/snf4j/snf4j) networking framework.

## Known issues

There's a serie of known bugs in this project that I did not fixed yet. They are the following :

In the case of Qwirkle logic, the bugs are :

- The program doesn't behave as intended when trying to insert a tile between two identical tiles, or two tiles with no common point.
- The program doesn't behave as intended when when trying to add tiles on either side of a line with a hole in the middle.
- The program doesn't behave as intended when trying to add unaligned tiles.
- The program doesn't behave as intended when validly adding several tiles starting from a position not adjacent to tiles already placed.

The other and only thing that I could categorize as a known issue is how I handle save files, I get an exception when trying to load a file that doesn't exit. I want to handle that case so that it doesn't exit the program.
