package com.hiFive.FridgeCircle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TheFridgeController {
    @GetMapping("/thefridge")
    public String showFridgePage(){
        return "thefridge";
    }
}
