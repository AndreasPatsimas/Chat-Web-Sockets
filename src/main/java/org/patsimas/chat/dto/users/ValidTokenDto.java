package org.patsimas.chat.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidTokenDto {

    private boolean isValid;
    private String extractedTokenUsername;
}
