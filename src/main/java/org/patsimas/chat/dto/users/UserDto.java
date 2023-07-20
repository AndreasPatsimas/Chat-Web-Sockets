package org.patsimas.chat.dto.users;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String authorities;
    private boolean active;
}
