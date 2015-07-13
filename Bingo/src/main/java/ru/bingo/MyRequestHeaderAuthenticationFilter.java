package ru.bingo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

public class MyRequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {
        super.unsuccessfulAuthentication(request, response, failed);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);   //Status 403
    }
}