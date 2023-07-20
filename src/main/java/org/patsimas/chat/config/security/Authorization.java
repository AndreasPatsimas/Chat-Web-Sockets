package org.patsimas.chat.config.security;

import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.exceptions.AuthorizationFailedException;
import org.springframework.util.ObjectUtils;

@Slf4j
public class Authorization {

    public static void authorizeRequest(String username, String principal, String message) {

        if(ObjectUtils.isEmpty(principal) || !principal.equals(username))
            throw new AuthorizationFailedException(message);
    }
}