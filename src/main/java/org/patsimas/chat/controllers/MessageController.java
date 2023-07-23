package org.patsimas.chat.controllers;

import org.hibernate.Hibernate;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat")
    public Message handlePrivateMessage(ChatMessage chatMessage) {

        System.out.println(chatMessage);

        Long senderId = chatMessage.getSender();
        Long recipientId = chatMessage.getRecipient();

        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> recipient = userRepository.findById(recipientId);

        Message message = new Message();
        message.setSender(sender.get());
        message.setRecipient(recipient.get());
        message.setContent(chatMessage.getContent());
        message.setRecordDate(Instant.now());
        message = messageRepository.save(message);

        try {
            messagingTemplate.convertAndSendToUser(senderId.toString(), "/queue/messages", message);
        }
        catch(Exception ex){
            System.out.println("Exception occured "+ex);
        }
        return message;
    }
}
