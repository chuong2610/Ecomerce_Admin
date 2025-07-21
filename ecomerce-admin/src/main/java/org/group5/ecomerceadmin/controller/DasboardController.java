package org.group5.ecomerceadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DasboardController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
