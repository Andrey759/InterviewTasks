package ru.bingo;

import org.junit.Test;
import ru.bingo.utils.ChiperUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CryptTest {

    @Test
    public void test() throws Exception {
        String in = "test";
        String crypt = ChiperUtils.encrypt(in);
        assertNotEquals(in, crypt);
        String decrypt = ChiperUtils.decrypt(crypt);
        assertEquals(in, decrypt);
    }

}
