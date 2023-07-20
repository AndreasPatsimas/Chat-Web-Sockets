package org.patsimas.chat.services;

import org.patsimas.chat.dto.users.AuthenticationRequest;
import org.patsimas.chat.dto.users.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
