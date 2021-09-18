package org.ewul.server.controller;

import org.ewul.core.service.AuthService;
import org.ewul.model.request.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    public AuthController() {
    }

    @GetMapping("/test")
    public boolean test() {
        return false;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request) {
    }

}
