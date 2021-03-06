package com.exemplo.retry.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class PropertyController {

    @RequestMapping(value = "/saveProperty", method = RequestMethod.POST)
    @ResponseBody String saveProperty(@RequestParam int maxTentativas, @RequestParam int maxTempo) {
        return "Properties atualizadas!";
    }

}