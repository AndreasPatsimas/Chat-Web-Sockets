package org.patsimas.chat.dto.users;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;
}
