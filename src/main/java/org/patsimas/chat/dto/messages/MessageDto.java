package org.patsimas.chat.dto.messages;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private long messageId;
    private String senderEmail;
    private String senderFullName;
    private String receiverEmail;
    private String receiverFullName;
    private String content;
}
