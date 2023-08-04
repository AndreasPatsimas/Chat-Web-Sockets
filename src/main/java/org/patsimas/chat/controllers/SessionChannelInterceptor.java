package org.patsimas.chat.controllers;

import org.patsimas.chat.config.security.JwtTokenProvider;
import org.patsimas.chat.services.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SessionChannelInterceptor implements ChannelInterceptor {

    private Logger logger = LoggerFactory.getLogger(SessionChannelInterceptor.class);
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        logger.debug("Interceptor for message = {} , command = {}",message,accessor.getCommand());
        // Check if it's a CONNECT message (new connection) or a MESSAGE message (actual data sent)
        if (StompCommand.CONNECT.equals(accessor.getCommand())
                || StompCommand.MESSAGE.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");

            if (jwtToken == null) {
                // If the token is missing or invalid, fail the request by throwing an exception
                throw new IllegalStateException("Unauthorized access. Please provide a valid JWT token.");
            }

            String token = extractToken(jwtToken);
            if (token != null) {
                UserDetails userDetails = getUserDetailsFromToken(token);
                if (userDetails == null) {
                    throw new IllegalStateException("Unauthorized access. Please provide a valid JWT token.");
                }
                logger.debug("Token is valid, User found");
                // Authenticate the user and set the user details in SecurityContextHolder
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        return message;
    }

    private UserDetails getUserDetailsFromToken(String token) {
        String username = jwtTokenProvider.extractUsername(token);
        return myUserDetailsService.loadUserByUsername(username);
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // The token is usually provided in the format "Bearer <token>"
            return authorizationHeader.substring(7); // Extract the token part after "Bearer "
        }
        return null;
    }
}
