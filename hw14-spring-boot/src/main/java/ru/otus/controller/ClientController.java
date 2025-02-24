package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping("/")
    public String getHomePage() {
        return "index.html";
    }

    @GetMapping("/clients")
    public String getClientsPage() {
        return "clients.html";
    }
}
