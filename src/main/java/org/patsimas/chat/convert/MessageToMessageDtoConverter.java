package org.patsimas.chat.convert;

import org.patsimas.chat.domain.Message;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.messages.MessageDto;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class MessageToMessageDtoConverter implements Converter<Message, MessageDto> {


    @Override
    public MessageDto convert(Message message) {

        User sender = message.getSender();
        User receiver = message.getReceiver();

        String senderFullName = buildFullName(sender);
        String receiverFullName = buildFullName(receiver);

        return MessageDto.builder()
                .messageId(message.getId())
                .senderEmail(sender.getEmail())
                .senderFullName(senderFullName)
                .receiverEmail(receiver.getEmail())
                .receiverFullName(receiverFullName)
                .content(message.getContent())
                .build();
    }

    private String buildFullName(User user) {

        return user.getFirstName() + " " + user.getLastName();
    }
}
