package org.patsimas.chat.controllers;

import org.patsimas.chat.dao.GroupDAO;
import org.patsimas.chat.domain.Group;
import org.patsimas.chat.domain.Message;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.groups.GroupDTO;
import org.patsimas.chat.dto.messages.ChatMessage;
import org.patsimas.chat.dto.messages.MessageDto;
import org.patsimas.chat.repositories.GroupRepository;
import org.patsimas.chat.repositories.MessageRepository;
import org.patsimas.chat.repositories.UserRepository;
import org.patsimas.chat.services.ChatService;
import org.patsimas.chat.services.GroupService;
import org.patsimas.chat.services.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private ChatService chatServiceImpl;

    @Autowired
    private GroupRepository groupRepository;

    @MessageMapping("/chat")
    public boolean handlePrivateMessage(ChatMessage chatMessage) {

        if(chatMessage==null || chatMessage.getSender()==null
                || chatMessage.getContent()==null || chatMessage.getRecipient()==null){
            logger.error("Invalid message {}",chatMessage);
            return false;
        }

        Long senderId = chatMessage.getSender();
        Long recipientId = chatMessage.getRecipient();

        String groupName = generateGroupId(senderId.toString(),recipientId.toString());

        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> recipient = userRepository.findById(recipientId);
        GroupDTO groupDTO = groupService.getGroup(groupName, sender.get());
        Group group = groupService.getGroup(groupDTO.getId());

        userGroupService.createUserGroup(group, sender.get());
        userGroupService.createUserGroup(group, recipient.get());

        Message message = new Message();
        message.setSender(sender.get());
        message.setContent(chatMessage.getContent());
        message.setGroup(group);
        message.setRecordDate(Instant.now());

        message = messageRepository.save(message);

        try {
            messagingTemplate.convertAndSendToUser(recipientId.toString(), "/queue/messages", message);
        }
        catch(Exception ex){
            logger.error("Exception occured {}",ex);
            return false;
        }
        return true;
    }

    @GetMapping("/oldChat/{senderId}/{recipientId}")
    public ResponseEntity<List<MessageDto>> getOldMessagesOfGroup(@PathVariable Long senderId,@PathVariable Long recipientId){

        String groupName = generateGroupId(senderId.toString(),recipientId.toString());
        Optional<User> userOptional = userRepository.findById(senderId);
        Optional<GroupDAO> groupOptional = groupRepository.findGroupByGroupNameAndUser(groupName,userOptional.get().getId());
        if(groupOptional.isPresent()){
            List<MessageDto> messages = chatServiceImpl.fetchOldChatByGroupId(groupOptional.get().getId());
            return ResponseEntity.ok(messages);
        }
        else{
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/oldChat/{groupId}")
    public ResponseEntity<List<MessageDto>> getOldMessagesOfGroup(@PathVariable Long groupId){

        List<MessageDto> messages = chatServiceImpl.fetchOldChatByGroupId(groupId);

        if(messages.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(messages);
        }
    }

    public static String generateGroupId(String user1Id, String user2Id) {
        String[] sortedIds = {user1Id, user2Id};
        Arrays.sort(sortedIds);
        return String.join("_", sortedIds);
    }
}
