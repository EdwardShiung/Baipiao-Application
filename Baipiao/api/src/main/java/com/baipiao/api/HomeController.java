package com.baipiao.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Redirect the root URL to the Swagger UI
        return "redirect:/swagger-ui/index.html";
    }
}