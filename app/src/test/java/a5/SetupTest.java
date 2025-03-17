package a5;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;

public class SetupTest {

    @Test
    public void checkFiles(){
        /* Test to check that the java class was 
         * created in the correct location
         */
        File f = new File("src/main/java/a5/SpellChecker.java");
        assertTrue(f.isFile());
    }
}
