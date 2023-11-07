import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Q5Test {

    @Test
    void testGetLastBlock() {
        assertArrayEquals(Q5.decodeHexString("86fa3f6e0e562d90a9039ad0ae9e587b"), Q5.getLastBlock(
                Q5.decodeHexString("5f58a07f218755e2b818f44b21c39e3186fa3f6e0e562d90a9039ad0ae9e587b")
        ));
    }

}
