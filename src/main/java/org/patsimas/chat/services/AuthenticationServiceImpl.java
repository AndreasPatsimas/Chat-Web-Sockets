package org.patsimas.chat.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.config.security.JwtTokenProvider;
import org.patsimas.chat.dto.users.AuthenticationRequest;
import org.patsimas.chat.dto.users.AuthenticationResponse;
import org.patsimas.chat.enums.AuthenticationStatus;
import org.patsimas.chat.exceptions.AuthenticationFailedException;
import org.patsimas.chat.exceptions.UnacceptableActionException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@PropertySource(value = "classpath:application.properties")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        log.info("Authentication process begins");

        Map<String, Object> claims = new HashMap<>();
        String jwt = getToken(authenticationRequest, claims);

        log.info("Authentication process completed");

        return AuthenticationResponse.builder()
                .jwt(jwt)
                .authenticationStatus(AuthenticationStatus.AUTHENTICATION_SUCCEEDED)
                .email(authenticationRequest.getEmail())
                .build();
    }

    private String getToken(AuthenticationRequest authenticationRequest, Map<String, Object> claims){

        authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                authenticationRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        return jwtTokenProvider.generateToken(claims, userDetails.getUsername());
    }

    private void authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){

        try {

            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Bad Credentials");
        }
        catch (DisabledException e) {
            throw new UnacceptableActionException("User disabled");
        }
    }
}
