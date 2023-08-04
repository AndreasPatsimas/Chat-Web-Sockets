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
        String senderFullName = buildFullName(sender);

        return MessageDto.builder()
                .messageId(message.getId())
                .senderFullName(senderFullName)
                .content(message.getContent())
                .build();
    }

    private String buildFullName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
