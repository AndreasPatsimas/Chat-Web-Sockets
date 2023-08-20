package org.patsimas.chat.controllers;

import org.patsimas.chat.domain.Group;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.friends.FriendsDTO;
import org.patsimas.chat.dto.users.UsersDTO;
import org.patsimas.chat.services.FriendsService;
import org.patsimas.chat.services.GroupService;
import org.patsimas.chat.services.UserGroupService;
import org.patsimas.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserGroupService userGroupService;

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

    @PostMapping("/users/{groupId}/{userId}")
    public ResponseEntity<String> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {

        Group group = groupService.getGroup(groupId);
        User user = userService.getUser(userId);

        userGroupService.createUserGroup(group, user);

        return ResponseEntity.ok("User added to group");
    }
}
