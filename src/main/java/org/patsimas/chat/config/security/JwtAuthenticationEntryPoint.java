package org.patsimas.chat.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.config.MessageProcessor;
import org.patsimas.chat.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageProcessor processor;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) {

        log.error("Responding with unauthorized error. Message - {}", e.getMessage());

        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try {
            processor.handle(ErrorResponse.builder()
                    .errorCode(HttpStatus.UNAUTHORIZED.value())
                    .status(HttpStatus.UNAUTHORIZED)
                    .message(e.getMessage() + ". Token may have expired.")
                    .build(), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}