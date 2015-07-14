package ru.bingo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.bingo.dao.AuthorityDAO;
import ru.bingo.dao.UserDAO;
import ru.bingo.model.AuthToken;
import ru.bingo.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AuthorityDAO authorityDAO;

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @RequestMapping(value = "/")
    @ResponseBody
    public String index() {
        return "Bingooo!!!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    public void auth(HttpServletResponse resp,
                     @RequestParam String username,
                     @RequestParam String pass) throws Exception {

        User user = userDAO.getByUserNameAndPass(username, pass);
        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            AuthToken token = new AuthToken(user);
            resp.addCookie(new Cookie("token", token.generateCookie()));
            resp.sendRedirect("/");
        }
    }

}
