package org.patsimas.chat.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.config.security.JwtTokenProvider;
import org.patsimas.chat.domain.JwtResponse;
import org.patsimas.chat.domain.MessageResponse;
import org.patsimas.chat.dto.users.AuthenticationRequest;
import org.patsimas.chat.dto.users.AuthenticationResponse;
import org.patsimas.chat.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/authenticate",produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> createAuthenticateToken(String username, String password, HttpServletResponse response) {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(username,password);
        log.info("Authenticate user {}", authenticationRequest.getUsername());

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(){
        ResponseCookie cookie = jwtTokenProvider.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
