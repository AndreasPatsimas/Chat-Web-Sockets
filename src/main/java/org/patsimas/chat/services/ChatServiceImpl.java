package org.patsimas.chat.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.domain.Message;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.messages.MessageDto;
import org.patsimas.chat.dto.messages.MessageRequestDto;
import org.patsimas.chat.repositories.MessageRepository;
import org.patsimas.chat.utils.UserUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.patsimas.chat.config.security.Authorization.authorizeRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final ConversionService conversionService;
    private final UserUtils userUtils;

    @Override
    public List<MessageDto> fetchByUsers(String senderEmail, String receiverEmail, Pageable pageable) {

        log.info("Fetch messages for sender[email:{}] and receiver[email{}] process begins", senderEmail, receiverEmail);

        User sender = userUtils.fetch(senderEmail);
        User receiver = userUtils.fetch(receiverEmail);

        List<MessageDto> messageDtoList = new ArrayList<>();
        Page<Message> messagePage = messageRepository.findByUsers(sender.getId(), receiver.getId(), pageable);
        List<Message> messages = messagePage.getContent();
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message message = messages.get(i);
            messageDtoList.add(conversionService.convert(message, MessageDto.class));
        }

        log.info("Fetch messages for sender[email:{}] and receiver[email{}] process end", senderEmail, receiverEmail);

        return messageDtoList;
    }

    @Override
    public int countByUsers(String senderEmail, String receiverEmail) {

        log.info("Count messages for sender[email:{}] and receiver[email{}] process begins", senderEmail, receiverEmail);

        User sender = userUtils.fetch(senderEmail);
        User receiver = userUtils.fetch(receiverEmail);

        int numOfMessages = messageRepository.countByUsers(sender.getId(), receiver.getId());

        log.info("Count messages for sender[email:{}] and receiver[email{}] process end", senderEmail, receiverEmail);

        return numOfMessages;
    }

    @Override
    public MessageDto sendMessage(MessageRequestDto messageRequestDto, String principal) {

        String senderEmail = messageRequestDto.getSenderEmail();
        String receiverEmail = messageRequestDto.getReceiverEmail();

        log.info("Save message for sender[email:{}] and receiver[email{}] process begins", senderEmail, receiverEmail);

        User sender = userUtils.fetch(senderEmail);
        authorizeRequest(sender.getEmail(), principal, "This action is forbidden");
        User receiver = userUtils.fetch(receiverEmail);

        Message message = messageRepository.save(Message.builder()
                .id(messageRequestDto.getMessageId())
                .sender(sender)
                .receiver(receiver)
                .content(messageRequestDto.getContent())
                .recordDate(Instant.now())
                .build());

        MessageDto messageDto = conversionService.convert(message, MessageDto.class);

        log.info("Save message for sender[email:{}] and receiver[email{}] process end", senderEmail, receiverEmail);

        return messageDto;
    }
}
