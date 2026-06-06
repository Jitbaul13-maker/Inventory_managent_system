package com.inv_managemnt.inv_backend.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {
    @GetMapping()
    public void healthCheck() {
        System.out.println("Running!");
    }
}
