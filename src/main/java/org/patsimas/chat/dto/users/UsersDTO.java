package org.patsimas.chat.dto.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersDTO {

    private Long id;
    private String fullName;
    private String userName;

    public UsersDTO(Long id,String fullName,String userName){
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
    }
}
