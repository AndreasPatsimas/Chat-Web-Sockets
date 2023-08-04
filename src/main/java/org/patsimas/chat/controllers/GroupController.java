package org.patsimas.chat.controllers;

import org.patsimas.chat.dto.groups.GroupDTO;
import org.patsimas.chat.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/group/{groupName}/{userId}")
    public ResponseEntity<GroupDTO> createGroup(@PathVariable String groupName,@PathVariable Long userId) throws Exception{
        GroupDTO groupDTO = groupService.createGroup(groupName, userId);
        return ResponseEntity.ok(groupDTO);
    }

    @GetMapping("/group/{userId}")
    public ResponseEntity<List<GroupDTO>> getGroups(@PathVariable Long userId) {
        List<GroupDTO> groupDTOList = groupService.getGroups(userId);
        return ResponseEntity.ok(groupDTOList);
    }
}
