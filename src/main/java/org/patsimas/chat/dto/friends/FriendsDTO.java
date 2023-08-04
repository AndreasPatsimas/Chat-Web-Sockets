package org.patsimas.chat.dto.friends;

public class FriendsDTO {
    private Long id;
    private String username;

    private String name;

    public FriendsDTO(Long id,String username,String name){
        this.id = id;
        this.username = username;
        this.name = name;
    }
}
