package org.patsimas.chat.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class ChatMessage {

    private MessageType messageType;
    private String content;
    private Long sender;
    private Long recipient;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
