# A5: Spell Checking

The purpose of this assignment is to give you some experience working with hash tables and hash sets.
For this project, you will create a spell-checking program that stores valid words in a hash set (implemented in the Java code by class [`HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html)).
It will also use a HashTable to keep track of the words it has already checked.

Here, we will return to an object-oriented approach to programming.
You will create two classes, `WordValidation` and `SpellChecker`, that will allow you to implement spell checking on one or more word(s).

## Overview

The heart of this assignment is the `WordValidation` class.
This class will read in and store words with correct spellings from a file (`src/data/words.txt`).
In phase two you will write a `SpellChecker` class that allows you to check spelling in two different ways:
  - In command-line mode it provides feedback on the spelling of specific words passed as command-line arguments. 
  - In file reading mode it reads in a file and prints suggestions for any words that are misspelled.

## Phase 1:  WordValidation Class

The `WordValidation` class should implement the `SpellingOperations` interface. 
The first goal for this class is to create a `HashSet` of valid words and a method, `containsWord`, that checks whether a word is found in the dictionary.
The second goal is to handle misspelled words by identifying `nearMisses` (alternatives that are one letter off).
You should write at least one test for each kind of near miss.

### Constructor

The constructor should meet the following specifications:
1. Accepts a string as input, corresponding to a filename that contains a list of valid words. We have provided `src/data/words.txt` for you to use for this purpose.
2. Reads in a file (we have provided code to help you with this as a method called `readFile`)
3. Creates an empty `HashSet`
4. Populates the `HashSet` by reading words one at a time from the file.

### Getter & Setter Methods

The class will not provide any getters/setters because other parts of your program shouldn't need to access it directly. 
You don't want to create opportunities for a user to make accidental changes to the program's reference list of words.

### Implementing the Methods

The two operations your class should provide are listed in the interface `SpellingOperations`.
Some more information about them:
* You should provide a method `containsWord` to check  the dictionary to see whether a word is valid (e.g., is spelled correctly). It should take a string (representing a word) and return `true` or `false` depending on whether the word is found in the dictionary.
* You should also provide a `nearMisses` method that suggests words similar to the misspelled word. This method will be used to suggest alternative options when `containsWord` determines a word is misspelled. It should identify words in the dictionary that are exactly one edit away. To do this, construct all possible strings that are one edit away and check each against the dictionary. You will then want to return any that are real words. Make sure the list you return doesn't contain any duplicates (e.g., make sure you are using HashSets in this method).
Hint: Look up Java's StringBuilder class!

#### `nearMisses` Method

You should consider the following types of near misses:

1. **Deletions**: Delete one letter from the word. 
    - Example: **catttle** -> **cattle**
    - Number of Possibilities: *n* possibilities for a word of length *n*
2. **Insertions**: Insert one letter into the word at any point. 
    - Example: **catle** -> **cattle**
    - Number of Possibilities: 26*(*n*+1) possibilities for a word of length *n* 
3. **Substitutions**: Replace one character with another. 
    - Example: **caxtle** -> **cattle**
    - Number of Possibilities: 25**n* possibilities for a word of length *n*
4. **Transpositions**: Swap two adjacent characters. 
    - Example: **cattel** -> **cattle**
    - Number of Possibilities: *n*-1 possibilities for a word of length *n* 
5. **Splits**: Divide the word into two legal words. For this kind of near miss, the pair of words together should be recorded as a single entry, with a space between them. 
    - Example: **cattell** -> **cat tell**
    - Number of Possibilities: *n*-1 possibilities for a word of length *n*

Note: There may be more than one way to generate a particular near miss using the above rules. The list returned by `nearMisses` should still include it only once.

#### `Main` Method

Similar to our testing paradigm on the first assignment of the semester, you should write tests to verify that `WordValidation` is functioning properly.
We have provided some tests in the `WordValidationTest` class.
Your should add at least **one test for each type of near miss**.
Think about edge cases (like word endings and beginnings) and anything else that might be tricky.
You want to test your class thoroughly -- we have also provided some grading tests.

### Strategy Suggestions

* While debugging, you might want to have your program print out all the near-miss candidates it creates for some short (two-letter) nonsense word. Inspect this list carefully to make sure all desired candidates are present and correct, and there are no duplicates.

* Your program can ignore capitalization and punctuation. You can do this by converting all words to lower case before checking their spelling and by making sure that the ``Scanner`` skips over punctuation marks. We have provided an example `clean` function to help with this. *In future assignments, you will be asked to apply this type of modification, so make sure you understand what it's doing.* If you like, feel free to implement a more sophisticated handling of capitalization -- please describe the choice in your README if you do.

* `WordValidation` should not print anything or interact directly with the user -- that will be the job of other classes.  Imagine that this is a class we might hope to use in many ways -- for the text-based checking described here, in an email program, for a window-based word processor, etc.  Printing to the `System.out` would not make sense for many of these modalities.

## Phase 2: SpellChecker Class

The `WordValidation` class described above is not designed for user interaction.
That role falls to `SpellChecker`:  its primary job is to interact with the user. 
Ideally, there will be two modes: one where it checks words read in as arguments, and another where it checks all words in a file.

You have some freedom about how to arrange the code into methods within this class.
The tests do assume that you will have:
1. a method called `readFile` that takes a filename as input (`String`),
2. a method called `checkSpelling` that takes the query word as input (`String`),
3. an overloaded version of `checkSpelling` that takes a HashSet as input and checks the spelling of every word in the set.

## Running Your Code 

One way to check how your code is running is to use the tests.
However, if you want to run your code while you are testing it, here is how to do it.
Imagine that `qest questt quest` are the words you want to spell check.
To provide gradle with command line input, you need to run the following command:

    `run -PappArgs="qest questt quest"`

(This assumes you are clicking the > icon from the Gradle sidebar -- if you are typing in the terminal, you will need to use `gradle run -PappArgs="qest questt quest"` or `./gradlew run -PappArgs="qest questt quest"`).

Note: If you are not using gradle, you can run the program from the terminal by typing something like the following (make sure you compile your code first with something like `javac *.java`):

    java SpellChecker qest questt quest

In the example, we provided three words to be checked, and the program should check each one to see a) whether it's misspelled and b) generate near misses for any misspellings. 

Note that in this style of program invocation, the three words are **command line arguments**, and they are relayed to your program via the `String[] args` parameter of the `main` function.
When command line arguments are provided, the program prints a message about every word, whether it is correct or not, and makes suggestions for incorrect words.
This is the first mode of operation we want SpellChecker to run in.

### Reading Words from a File

In the event that it doesn't receive command line arguments, `SpellChecker` should call a `readFile` method to read in all the text from a file. 
You will know when to call this method because `args` in `main` will have a length of 0 (unlike the previous mode, where it will contain 1 or more words).
When your program detects that there are no command line arguments, you should then read the file in using a `Scanner`.
If you need help getting started, you can look at the `readFile` method we provided in the `WordValidation` class and adapt it for the specifics of this problem.

Here, you want the program to read all of the individual words from the input file.
If a word is spelled correctly, the program should move on to checking the next word.
If a word is misspelt, your program should offer suggestions, just as above.
However, if a word occurs multiple times, you only want to check it once.
Think critically about which data structures might help you make this happen efficiently.

Once you have your program working, you will want to test it out. Here's a sonnet in Shakespeare's original spelling that would make a good candidate (also found in `src/data/sonnet.txt`):

<pre style="left-margin:0.5in; font-style:italic">Shall I compare thee to a Summers day?
Thou art more louely and more temperate:
Rough windes do shake the darling buds of Maie,
And Sommers lease hath all too short a date:
Sometime too hot the eye of heauen shines,
And often is his gold complexion dimm'd,
And euery faire from faire some-time declines,
By chance, or natures changing course vntrim'd
But thy eternal Sommer shall not fade,
Nor loose possession of that faire thou ow'st,
Nor shall death brag thou wandr'st in his shade,
When in eternall lines to time thou grow'st,
So long as men can breath or eyes can see,
So long liues this, and this giues life to thee.
</pre>

Finally, you will want to each misspelled word with the appropriate nearMisses in a data structure that allows you to associate the word with the suggestions. Think carefully about which data structure(s) will let you do this most efficiently.s

## Going the Extra Mile ("Kudos")

See if you can modify the testProgramExits() test from `WordValidationTest` to check that your `SpellChecker.readFile` method properly fails to read in a non-existent file

### Notes on Design

This is a good opportunity to practice what you have learned in CSC 120 about class design. Specifically, try to put methods in logical places **based upon the roles of the classes**:
- Think of ``WordValidation`` as a class that might be used by other programs -- a word processor, for example, or an email program -- so try to keep it general, while still offering useful and powerful methods. 
- Your ``SpellCheck`` program is one specific application. If you design ``WordValidation`` well, then you won't have to write too much code for ``SpellCheck``... but you don't want ``WordValidation`` catering too much to ``SpellCheck``'s specific implementation!

### Acknowledgment

_The concept for this assignment was proposed by [David Eck](http://math.hws.edu/eck/)_