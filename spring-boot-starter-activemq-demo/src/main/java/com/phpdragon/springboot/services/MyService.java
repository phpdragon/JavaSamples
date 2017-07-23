package com.phpdragon.springboot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Value("${application.name:unknown}")
    private String applicationName;

    public String getMessage() {
        return getMessage(applicationName);
    }

    public String getMessage(String name) {
        return "Hello " + name;
    }
}
