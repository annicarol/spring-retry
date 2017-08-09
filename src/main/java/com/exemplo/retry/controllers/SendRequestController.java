package com.exemplo.retry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.retry.RetryCall;

@RestController
@EnableAutoConfiguration
public class SendRequestController {
	
	@Autowired
	RetryCall retry;
	
    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    @ResponseBody void sendRequest(@RequestBody String requestBody) {
        retry.chamadaComRetry();
    }

}
