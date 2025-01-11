package net.achille.myrestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("src/main/resources/templates/index.html")
    public String home() {
        return "index";
    }

    // Add this to handle the error page
    @GetMapping("/error")
    public String handleError() {
        return "index";
    }
}