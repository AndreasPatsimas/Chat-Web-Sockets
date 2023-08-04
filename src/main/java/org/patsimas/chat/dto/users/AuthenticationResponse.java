package org.patsimas.chat.dto.users;

import lombok.*;
import org.patsimas.chat.enums.AuthenticationStatus;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwt;
    private AuthenticationStatus authenticationStatus;
    private String email;
    private String username;
}
