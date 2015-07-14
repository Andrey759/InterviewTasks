package ru.bingo.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import ru.bingo.model.AuthToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter{


    public TokenAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException, IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            throw new InvalidCookieException("cookies must be present");
        }

        String tokenStr = null;
        for (Cookie c: cookies) {
            if (c.getName().equals("token")) {
                tokenStr = c.getValue();
                break;
            }
        }
        if (tokenStr == null) {
            throw new InvalidCookieException("token must be present");
        }

        AuthToken authToken = null;
        try {
            authToken = new AuthToken(tokenStr);
        } catch (Exception e) {
            throw new InvalidCookieException("token must be correct");
        }

        UsernamePasswordAuthenticationToken UserPassAuthToken = new UsernamePasswordAuthenticationToken(
                authToken.getLogin(), authToken.getPass(), authToken.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(UserPassAuthToken);

        return UserPassAuthToken;


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

}
