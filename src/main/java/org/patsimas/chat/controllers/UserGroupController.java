package org.patsimas.chat.controllers;

import org.patsimas.chat.dto.groups.GroupDTO;
import org.patsimas.chat.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;


    @PostMapping("/userGroup/{groupId}/{userId}")
    public ResponseEntity<String> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        userGroupService.addUserToGroup(groupId,userId);
        return ResponseEntity.ok("Added");
    }

    @GetMapping("/userGroup/{userId}")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable Long userId) {
        List<GroupDTO> groupDTOList = userGroupService.getUserGroups(userId);
        return ResponseEntity.ok(groupDTOList);
    }
}
