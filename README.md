# **Gantt Manager** #
A Gantt diagram manager implemented in Java and the Swing framework for the Software Development course for the Dept of Computer Engineering and Science at the University of Ioannina

**Members** \
Georgiou Nikolaos ([`nikgeorg`](https://github.com/nikgeorg)) \
Konstantina Knekna ([`email`](cs04548@uoi.gr))

**Professor** \
Panos Vassiliadis ([`pvassil`](https://github.com/pvassil))

<br><br>

# Requirements #
- Java 15 and later
- Eclipse IDE for Java Developers
- ObjectAid (for the UML diagram)
<br><br>

# How it works #
## Summary ##
The user runs the application through the Eclipse IDE. The user can then select an input file of a valid format (.txt/.tsv, .md or .html) and the window will now represent all data given into a table. The user can then, if they so desire, output the data to another file of the aforementioned formats (.tsv, .md or .html)
<br>

Some sample input files are given in the `src/main/resources/input` folder to test the functionality.

## Valid formatting of the input files ##
Each main task that either has children, or is standalone, needs to begin with a number divisible by 100. Its children follow suit by having a start time that follows the parent right away, or their task IDs, and they are sorted out in the same priority (start time first, ID afterwards). 
The child task IDs must be from the X01-X99 number series, X is the hundredth number used for the parent. i.e. 100 is the parent ID and 101, 102, etc are the children IDs.
<br><br>

# Running the application #
Open the project in Eclipse and click on the play button to execute the main function of the `MainControllerFactory.java` file in `src/main/java/backend.`
<br>

You'll need to make sure the AssertJ library is used and enabled in the project in order to run the JUnit tests provided. The library is provided in the `src/main/resources/lib` folder.
<br><br>

# Not working #
Loading another file while one has already been loaded will cause the data/table to show both data at once, or wrong values. To work around this, anytime you've loaded a file and want to load another one, close the application and run it through the IDE again.
<br>

Not following the rules for the input files will also result in the same behavior. The application hasn't been fixed to get around all the possible bugs (yet).
