package org.patsimas.chat.dto.groups;

import lombok.Data;

@Data
public class GroupDTO {

    private Long id;
    private String groupName;
    private String userFirstName;
    private String userLastName;
}
