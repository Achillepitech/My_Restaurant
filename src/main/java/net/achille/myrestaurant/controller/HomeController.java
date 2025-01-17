package net.achille.myrestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/error")
    public String handleError() {
        return "index";
    }


    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/plats")
    public String plats() {
        return "plats";
    }

    @GetMapping("/entree")
    public String entree() {
        return "entree";
    }

    @GetMapping("/dessert")
    public String dessert() {
        return "dessert";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping ("/signup")
    public String signup() {
        return "signup";
    }

}