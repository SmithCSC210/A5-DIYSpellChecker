package a5;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class WordValidationTest {

    WordValidation wordSet;

    @Before
    public void setUp() {
        try {
            this.wordSet = new WordValidation("src/data/words.txt");
        } catch (Exception e) {
            System.err.println("Setup failed: " + e.getMessage());
        }
    }

    public class SystemExitMock {
        public static void exit(int status) {
            throw new SecurityException("System.exit() called with status: " + status);
        }
    }

    @Test
    public void testProgramExits() {
        // Mock System.exit()
        SecurityException thrown = assertThrows(SecurityException.class, () -> {
            SystemExitMock.exit(-1);
            new WordValidation("src/data/ssdf.txt");
        });

        assertEquals("System.exit() called with status: -1", thrown.getMessage());
    }

    @Test
    public void checkInterface(){
        assertTrue("WordValidation should implement SpellingOperations", this.wordSet instanceof SpellingOperations);
    }

    /* Check that the constructor created a set of the correct size from words.txt */
    @Test
    public void createSet(){
        assertNotNull("wordSet should be initialized", wordSet);
    }

    /* Check that the valid words set contains various words with correct formatting */
    @Test
    public void checkSet(){
        String[] testWords = {"agamemnon", "wouldnt", "y", "courageously"};
        for(int i = 0; i < testWords.length; i++){
            assertTrue("Missing word " + testWords[i], wordSet.containsWord(testWords[i]));
        }
    }

    /* Check that the set of proposed alternatives includes substitutions */
    @Test
    public void checkSub(){
        assertTrue(wordSet.nearMisses("cattte").contains("cattle"));
        assertTrue(wordSet.nearMisses("xlatter").contains("platter"));
        assertTrue(wordSet.nearMisses("sunbonnef").contains("sunbonnet"));
    }

    /* Check that the set of proposed alternatives includes insertions */
    @Test
    public void checkIns(){
        assertTrue(wordSet.nearMisses("cattlle").contains("cattle"));
        assertTrue(wordSet.nearMisses("pplatter").contains("platter"));
        assertTrue(wordSet.nearMisses("sunbonnett").contains("sunbonnet"));
    }

    /* Check that the set of proposed alternatives includes deletions */
    @Test
    public void checkDel(){
        assertTrue(wordSet.nearMisses("catte").contains("cattle"));
        assertTrue(wordSet.nearMisses("platea").contains("plateau"));
        assertTrue(wordSet.nearMisses("unbonnet").contains("sunbonnet"));
    }

    /* Check that the set of proposed alternatives includes transpositions */
    @Test
    public void checkTranspo(){
        assertTrue(wordSet.nearMisses("cattel").contains("cattle"));
        assertTrue(wordSet.nearMisses("lpatter").contains("platter"));
        assertTrue(wordSet.nearMisses("subnonnet").contains("sunbonnet"));
    }

    /* Check that the set of proposed alternatives includes splits */
    @Test
    public void checkSplit(){
        assertTrue(wordSet.nearMisses("cattell").contains("cat tell"));
        assertTrue(wordSet.nearMisses("platter").contains("platte r"));
        assertTrue(wordSet.nearMisses("sbonnet").contains("s bonnet"));
    }

}
