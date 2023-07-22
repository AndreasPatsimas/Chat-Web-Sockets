package org.patsimas.chat.controllers;

import org.patsimas.chat.domain.Message;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.messages.ChatMessage;
import org.patsimas.chat.repositories.MessageRepository;
import org.patsimas.chat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class MessageController {

    private final Map<String, UserSession> userSessions = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat") // This will handle private messages
    public Message handlePrivateMessage(ChatMessage chatMessage) {

        System.out.println(chatMessage);

        Long senderId = chatMessage.getSender();
        Long recipientId = chatMessage.getRecipient();

        User sender = userRepository.getById(senderId);
        User recipient = userRepository.getById(recipientId);

        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(chatMessage.getContent());
        message.setRecordDate(Instant.now());

        if(message.getRecipient()!=null && userSessions.containsKey(message.getRecipient())){
            UserSession recipientSession = userSessions.get(message.getRecipient());
            if(recipientSession!=null){
                messagingTemplate.convertAndSendToUser(recipientSession.getSessionId(),"/queue/private",message);
            }
        }
        messageRepository.save(message);

        return message;
    }

    @SubscribeMapping("/user/queue/private") // This will subscribe the user to their private message queue
    public List<Message> subscribeToPrivateMessages(Principal principal) {
        String username = principal.getName();
        UserSession userSession = new UserSession(username,((StompHeaderAccessor) principal).getSessionId());
        userSessions.put(username, userSession);

        List<Message> previousMessages = messageRepository.findAllByRecipientAndSender(username, "You");
        return previousMessages;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getUser().getName();

        userSessions.remove(username);
    }

    // Inner class to hold user session details
    private static class UserSession {
        private final String userName;
        private final String sessionId;

        public UserSession(String userName, String sessionId) {
            this.userName = userName;
            this.sessionId = sessionId;
        }

        public String getUserName() {
            return userName;
        }

        public String getSessionId() {
            return sessionId;
        }
    }
}
