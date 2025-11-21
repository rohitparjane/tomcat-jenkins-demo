package com.test.tom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {

    @GetMapping("/HI")
    public String test(){
        return "Hello Motherfuckers jenkins worked 22th last time try bhenchod";
    }

}
