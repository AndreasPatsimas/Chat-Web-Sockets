package org.patsimas.chat.dto.messages;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private long messageId;
    private String senderFullName;
    private String content;
    private String messageTimestamp;
    private long groupId;
}
