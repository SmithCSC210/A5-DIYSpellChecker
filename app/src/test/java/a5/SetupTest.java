package a5;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;

public class MainTest {

    @Test
    public void checkFiles(){
        /* Test to check that both java classes were 
         * created in the correct location
         */
        File f = new File("src/main/java/a5/WordValidation.java");
        assertTrue(f.isFile());

        f = new File("src/main/java/a5/SpellChecker.java");
        assertTrue(f.isFile());
    }
}
