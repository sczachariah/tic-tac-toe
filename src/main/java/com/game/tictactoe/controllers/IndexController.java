package com.game.tictactoe.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/")
@ApiIgnore
public class IndexController {
    @RequestMapping(method = RequestMethod.GET)
    public String getIndexHomePage() {
        return "redirect:/swagger-ui.html";
    }

    @RequestMapping(path = "/error")
    public String getSwagger() {
        return "redirect:/swagger-ui.html";
    }
}
