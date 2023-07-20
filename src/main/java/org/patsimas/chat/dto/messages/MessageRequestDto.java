package org.patsimas.chat.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {

    private Long messageId;
    private String senderEmail;
    private String receiverEmail;
    private String content;
}
