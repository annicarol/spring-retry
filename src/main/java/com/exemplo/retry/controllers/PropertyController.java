package com.exemplo.retry.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class PropertyController {

    @RequestMapping("/setProperty")
    String home() {
        return "Hello World!";
    }

}