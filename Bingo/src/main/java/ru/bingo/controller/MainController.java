package ru.bingo.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    //Logger log = Logger.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "index2.jsp";
    }

}
