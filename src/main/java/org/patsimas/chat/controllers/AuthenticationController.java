package org.patsimas.chat.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.dto.users.AuthenticationRequest;
import org.patsimas.chat.services.AuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createAuthenticateToken(@RequestBody AuthenticationRequest authenticationRequest) {

        log.info("Authenticate user {}", authenticationRequest.getEmail());

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
