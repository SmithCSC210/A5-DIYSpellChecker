package a5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class WordValidation {
    
    private HashSet<String> words;

    /** This method will help you remove the punctuation from a query and convert
     *  it to lowercase
     *  @param query the word to process
     *  @return a lowercase string with no punctuation
     */
    private String clean(String query){
        return query.toLowerCase().replaceAll("[^\\sa-zA-Z0-9]", "");
    }
    
    /**
     * Creates a set of recognized words read in from a file containing one word
     * per line. If the file can't be found, it will throw an error (this is handled
     * using try/catch). If the file is found, it reads in the line, cleans the word to
     * remove punctuation and convert it to lowercase (and `trim` removes any leftover 
     * whitespace), and adds the word to the set.
     */
    public Scanner readFile(String fname){
        Scanner file = null; // readFile might throw FileNotFoundException
        try{
            file = new Scanner(new File(fname));
            while (file.hasNextLine()) {
                String line = file.nextLine();
                words.add(clean(line.trim()));
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate file.");
            System.exit(-1);
        }
        return file;
    }
}
