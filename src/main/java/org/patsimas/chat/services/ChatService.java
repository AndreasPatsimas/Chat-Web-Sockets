package org.patsimas.chat.services;

import org.patsimas.chat.dto.messages.MessageDto;
import org.patsimas.chat.dto.messages.MessageRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {

    List<MessageDto> fetchByUsers(String senderEmail, String receiverEmail, Pageable pageable);

    int countByUsers(String senderEmail, String receiverEmail);

    MessageDto sendMessage(MessageRequestDto messageRequestDto, String principal);
}
