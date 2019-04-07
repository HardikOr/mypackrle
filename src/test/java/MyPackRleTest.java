import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyPackRleTest {
    @Test
    public void test() {
        // All files are in ././src/main/resources/
        MyPackRle.main("-z ././src/main/resources/test.txt -out ././src/main/resources/boba.out".split(" "));
        MyPackRle.main("-u -out ././src/main/resources/bobaout.out ././src/main/resources/boba.out".split(" "));

        assertTrue(Pack.compare("././src/main/resources/test.txt", "././src/main/resources/bobaout.out"));
    }
}