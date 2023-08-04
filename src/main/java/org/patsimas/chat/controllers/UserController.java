package org.patsimas.chat.controllers;

import org.patsimas.chat.dto.friends.FriendsDTO;
import org.patsimas.chat.dto.users.UsersDTO;
import org.patsimas.chat.services.FriendsService;
import org.patsimas.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UsersDTO>> getUsers() {
        List<UsersDTO> usersDTOList = userService.getUsers();
        return ResponseEntity.ok(usersDTOList);
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<FriendsDTO>> getFriends(@PathVariable Long userId){
        List<FriendsDTO> friendsDTOList = friendsService.getFriends(userId);
        return ResponseEntity.ok(friendsDTOList);
    }
}
