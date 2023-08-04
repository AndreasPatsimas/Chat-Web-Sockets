package org.patsimas.chat.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.domain.Group;
import org.patsimas.chat.domain.Message;
import org.patsimas.chat.dao.MessageDAO;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.messages.MessageDto;
import org.patsimas.chat.dto.messages.MessageRequestDto;
import org.patsimas.chat.repositories.GroupRepository;
import org.patsimas.chat.repositories.MessageRepository;
import org.patsimas.chat.utils.UserUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.patsimas.chat.config.security.Authorization.authorizeRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final ConversionService conversionService;
    private final UserUtils userUtils;

    private final GroupRepository groupRepository;

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
                .content(messageRequestDto.getContent())
                .recordDate(Instant.now())
                .build());

        MessageDto messageDto = conversionService.convert(message, MessageDto.class);

        log.info("Save message for sender[email:{}] and receiver[email{}] process end", senderEmail, receiverEmail);

        return messageDto;
    }

    @Override
    public List<MessageDto> fetchOldChat(Long userId) {

        log.info("Fetch messages for user[id:{}] process begins", userId);

        User user = userUtils.fetch(userId);

        List<MessageDto> messageDtoList = new ArrayList<>();
        List<MessageDAO> messages = messageRepository.findByUser(user.getId());

        messageDtoList  = prepareMessageDtoFromDAO(messages);

        log.info("Fetch messages for user[id:{}] process ends", userId);

        return messageDtoList;
    }

    @Override
    public List<MessageDto> fetchOldChatByGroupId(Long groupId) {

        List<MessageDto> messageDtoList = new ArrayList<>();

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isPresent()){
            List<MessageDAO> messageDAOList = messageRepository.findMessageByGroup(groupOptional.get().getId());
            messageDtoList = prepareMessageDtoFromDAO(messageDAOList);
        }

        return messageDtoList;
    }

    private List<MessageDto> prepareMessageDtoFromDAO(List<MessageDAO> messageDAOList){

        List<MessageDto> messageDtoList = new ArrayList<>();

        messageDAOList.forEach(message -> {

            MessageDto messageDto = new MessageDto();
            messageDto.setMessageId(message.getId());
            messageDto.setContent(message.getContent());
            messageDto.setSenderFullName(message.getSenderFirstName()+" "+message.getSenderLastName());
            messageDto.setMessageTimestamp(message.getMessageTimestamp());
            messageDto.setGroupId(message.getGroupId());

            messageDtoList.add(messageDto);
        });

        return messageDtoList;
    }
}
