package org.patsimas.chat.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.dto.users.AuthenticationRequest;
import org.patsimas.chat.dto.users.AuthenticationResponse;
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

    @PostMapping(value = "/authenticate",produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> createAuthenticateToken(String username, String password) {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(username,password);
        log.info("Authenticate user {}", authenticationRequest.getUsername());

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        return ResponseEntity.ok(authenticationResponse);
    }

}
