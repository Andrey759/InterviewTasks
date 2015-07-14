package ru.bingo;

import org.junit.Test;
import ru.bingo.model.AuthToken;
import ru.bingo.utils.ChiperUtils;

import static org.junit.Assert.assertEquals;

public class AuthTokenTest {

    @Test
    public void createTest() throws Exception {
        AuthToken token = new AuthToken(ChiperUtils.encrypt("user:ROLE_USER,ROLE_ADMIN"));
        assertEquals("user", token.getLogin());
        assertEquals("ROLE_USER", token.getAuthorities().get(0).toString());
        assertEquals("ROLE_ADMIN", token.getAuthorities().get(1).toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createExceptionTest() throws Exception {
        AuthToken token = new AuthToken(ChiperUtils.encrypt("user"));
    }

    @Test
    public void cookieTest() throws Exception {
        AuthToken token = new AuthToken(ChiperUtils.encrypt("user:ROLE_USER,ROLE_ADMIN"));
        String cookie = token.generateCookie();
        assertEquals("user:ROLE_USER,ROLE_ADMIN", ChiperUtils.decrypt(cookie));
    }

}
