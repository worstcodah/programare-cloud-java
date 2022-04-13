package com.example.SpringConfigClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {

    @Autowired
    private Environment environment;

    @GetMapping("/msg")
    public String getMessage(){
        return environment.getProperty("env.message");
    }

    @GetMapping("/title")
    public String getTitle(){
        return environment.getProperty("env.title");
    }
}
