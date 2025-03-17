package a5;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

public class SpellCheckerTest {
    /* Holds a SpellChecker object to use in tests */
    SpellChecker words;

    /* Creates the SpellChecker object to use for testing */
    @Before
    public void setUp(){
        try {
            this.words = new SpellChecker("src/data/words.txt");
        } catch (Exception e) {
            System.err.println("Setup failed: " + e.getMessage());
        }
    }

    /* Check that the readFile method creates a HashSet of the correct size
     * based on the src/data/sonnet.txt file
    */
    @Test
    public void testReadFileReturnsHashSet(){
        HashSet<String> result= SpellChecker.readFile("src/data/sonnet.txt");
        assertThat(result, instanceOf(HashSet.class));
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        assertEquals(85, result.size());
    }

    /* Check that the checkSpelling method works correctly with string input */
    @Test
    public void testSpellCheckString(){
        assertEquals("Nonsense word returns empty set", 0, words.checkSpelling("qqxjzqq").get("qqxjzqq").size());
        assertEquals("Correctly spelled word returns empty set", 0, words.checkSpelling("awestruck").size());
        assertEquals("Valid misspelling returns non-empty set", 1, words.checkSpelling("pneumaticaly").get("pneumaticaly").size());
    }

    /* Check that checkSpelling properly handles a deletion at the beginning of
     * a string
     */
    @Test
    public void testSpellCheckStringDelStart() {
        Map<String, HashSet<String>> result = words.checkSpelling("westruck");

        // Confirm "westruck" was stored as a key
        assertTrue("Result should contain the key 'westruck'", result.containsKey("westruck"));

        // Check that the value is a set containing exactly ["awestruck", "we struck"]
        HashSet<String> expectedSet = new HashSet<>(Arrays.asList("awestruck", "we struck"));
        assertEquals("The set should contain only 'awestruck' and 'we struck'", expectedSet, result.get("westruck"));
    }

    /* Check that checkSpelling properly handles a deletion in the middle of
     * a string
     */
    @Test
    public void testSpellCheckStringDelMid(){
        Map<String, HashSet<String>> result = words.checkSpelling("awetruck");

        // confirm that awetruck is a valid key
        assertTrue("Result should contain the key 'awetruck'", result.containsKey("awetruck"));

        // confirm that awetruck's matches are exactly ["awestruck", "we struck"]
        HashSet<String> expectedSet = new HashSet<>(Arrays.asList("awestruck", "awe truck"));
        assertEquals("The set should contain only 'awestruck' and 'we struck'", expectedSet, result.get("awetruck"));
    }

    /* Check that checkSpelling properly handles a deletion at the end of
     * a string
     */
    @Test
    public void testSpellCheckStringDelEnd(){
        Map<String, HashSet<String>> result = words.checkSpelling("awestruc");

        // Then: The result should have "westruck" as a key
        assertTrue("Result should contain the key 'awestruck'", result.containsKey("awestruc"));

        // And: The value should be a set containing only "awestruck"
        HashSet<String> expectedSet = new HashSet<>(Collections.singletonList("awestruck"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("awestruc"));
    }

    /* Check that checkSpelling properly handles an insertion at the beginning of
     * a string
     */
    @Test
    public void testSpellCheckStringInsStart() {
        Map<String, HashSet<String>> result = words.checkSpelling("qawestruck");

        // Confirm "westruck" was stored as a key
        assertTrue("Result should contain the key 'qawestruck'", result.containsKey("qawestruck"));

        // And: The value should be a set containing only "awestruck"
        HashSet<String> expectedSet = new HashSet<>(Arrays.asList("awestruck", "q awestruck"));
        assertEquals("The set should contain only 'awestruck' and 'q awestruck'", expectedSet, result.get("qawestruck"));
    }

    /* Check that checkSpelling properly handles an insertion in the middle of
     * a string
     */
    @Test
    public void testSpellCheckStringInsMid(){
        Map<String, HashSet<String>> result = words.checkSpelling("aweqstruck");

        // confirm that awetruck is a valid key
        assertTrue("Result should contain the key 'aweqstruck'", result.containsKey("aweqstruck"));

        // confirm that awetruck's matches are exactly ["awestruck", "we struck"]
        HashSet<String> expectedSet = new HashSet<>(Collections.singletonList("awestruck"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("aweqstruck"));
    }

    /* Check that checkSpelling properly handles an insertion at the end of
     * a string
     */
    @Test
    public void testSpellCheckStringInsEnd(){
        Map<String, HashSet<String>> result = words.checkSpelling("awestruckq");

        // The result should have "awestruc" as a key
        assertTrue("Result should contain the key 'awestruckq'", result.containsKey("awestruckq"));

        // The value should be a set containing only "awestruck" and "q awestruck"
        HashSet<String> expectedSet = new HashSet<>(Arrays.asList("awestruck", "awestruck q"));
        assertEquals("The set should contain only 'awestruck' and 'awestruck q'", expectedSet, result.get("awestruckq"));
    }

    /* Check that checkSpelling properly handles a substitution at the beginning of
     * a string
     */
    @Test
    public void testSpellCheckStringSubStart(){
        Map<String, HashSet<String>> result = words.checkSpelling("qwestruck");

        // The result should have "qwestruck" as a key
        assertTrue("Result should contain the key 'qwestruck'", result.containsKey("qwestruck"));

        // The value should be a set containing only "awestruck" 
        HashSet<String> expectedSet = new HashSet<>(Collections.singletonList("awestruck"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("qwestruck"));
    }

    /* Check that checkSpelling properly handles a substitution in the middle of
     * a string
     */
    @Test
    public void testSpellCheckStringSubMid(){
        Map<String, HashSet<String>> result = words.checkSpelling("awestquck");

        // The result should have "awestquck" as a key
        assertTrue("Result should contain the key 'awestquck'", result.containsKey("awestquck"));

        // The value should be a set containing only "awestruck" 
        HashSet<String> expectedSet = new HashSet<>(Collections.singletonList("awestruck"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("awestquck"));
    }

    /* Check that checkSpelling properly handles a substitution at the end of
     * a string
     */
    @Test
    public void testSpellCheckStringSubEnd(){
        Map<String, HashSet<String>> result = words.checkSpelling("awestrucq");

        // The result should have "awestrucq" as a key
        assertTrue("Result should contain the key 'awestrucq'", result.containsKey("awestrucq"));

        // The value should be a set containing only "awestruck" 
        HashSet<String> expectedSet = new HashSet<>(Collections.singletonList("awestruck"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("awestrucq"));
    }

    /* Check that checkSpelling properly handles a transposition at the beginning of
     * a string
     */
    @Test
    public void testSpellCheckStringTranspoStart(){
        Map<String, HashSet<String>> result = words.checkSpelling("waestruck");

        // The result should have "waestruck" as a key
        assertTrue("Result should contain the key 'waestruck'", result.containsKey("waestruck"));

        // The value should be a set containing only "awestruck" 
        HashSet<String> expectedSet = new HashSet<>(Collections.singletonList("awestruck"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("waestruck"));
    }

    /* Check that checkSpelling properly handles a transposition in the middle of
     * a string
     */
    @Test
    public void testSpellCheckStringTranspoMid(){
        Map<String, HashSet<String>> result = words.checkSpelling("awetsruck");

        // The result should have "awetsruck" as a key
        assertTrue("Result should contain the key 'awetsruck'", result.containsKey("awetsruck"));

        // The value should be a set containing only "awestruck" 
        HashSet<String> expectedSet = new HashSet<>(Collections.singletonList("awestruck"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("awetsruck"));
    }

    /* Check that checkSpelling properly handles a transposition at the end of
     * a string
     */
    @Test
    public void testSpellCheckStringTranspoEnd(){
        Map<String, HashSet<String>> result = words.checkSpelling("awestrukc");

        // The result should have "awestrukc" as a key
        assertTrue("Result should contain the key 'awestrukc'", result.containsKey("awestrukc"));

        // The value should be a set containing only "awestruck" 
        HashSet<String> expectedSet = new HashSet<>(Collections.singletonList("awestruck"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("awestrukc"));
    }

    /* Check that checkSpelling properly handles mashed-together words*/
    @Test
    public void testSpellCheckStringSplit(){
        Map<String, HashSet<String>> result = words.checkSpelling("zitherawestruck");

        // The result should have "zitherawestruck" as a key
        assertTrue("Result should contain the key 'zitherawestruck'", result.containsKey("zitherawestruck"));

        // The value should be a set containing only "zither awestruck" 
        HashSet<String> expectedSet = new HashSet<>(Collections.singletonList("zither awestruck"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("zitherawestruck"));

        result = words.checkSpelling("awestruckzither");

        // The result should have "awestruckzither" as a key
        assertTrue("Result should contain the key 'awestruckzither'", result.containsKey("awestruckzither"));

        // The value should be a set containing only "zither awestruck" 
        expectedSet = new HashSet<>(Collections.singletonList("awestruck zither"));
        assertEquals("The set should contain only 'awestruck'", expectedSet, result.get("awestruckzither"));
    }

    /* Check that the HashMap returned from the analysis of the sonnet file matches what is expected
     */
    @Test
    public void testSpellCheckFile(){
        Map<String, HashSet<String>> result = words.checkSpelling(SpellChecker.readFile("src/data/sonnet.txt"));
        assertEquals(16, result.keySet().size());

        HashSet<String> expectedSet = new HashSet<>(Arrays.asList("somme rs", "summers", "sommes", "simmers"));
        assertEquals(expectedSet,result.get("sommers"));
        assertEquals(0, result.get("vntrimd").size());
    }
}
