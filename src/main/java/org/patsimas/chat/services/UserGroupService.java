package org.patsimas.chat.services;

import org.patsimas.chat.domain.Group;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.domain.UserGroup;
import org.patsimas.chat.dto.groups.GroupDTO;
import org.patsimas.chat.repositories.GroupRepository;
import org.patsimas.chat.repositories.UserGroupRepository;
import org.patsimas.chat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    public void createUserGroup(Group group, User user){

        Optional<UserGroup> userGroupOptional = userGroupRepository.findByGroupIdAndUserId(group.getId(),user.getId());

        if(userGroupOptional.isPresent()){
            return;
        }

        UserGroup userGroup = new UserGroup();
        userGroup.setGroup(group);
        userGroup.setUser(user);

        userGroupRepository.save(userGroup);
    }

    public void addUserToGroup(Long groupId,Long userId){

        Group group = groupRepository.findById(groupId).get();

        User user = userRepository.findById(userId).get();

        UserGroup userGroup = new UserGroup();
        userGroup.setUser(user);
        userGroup.setGroup(group);

        userGroupRepository.save(userGroup);
    }

    @Transactional
    public List<GroupDTO> getUserGroups(Long userId){

        List<UserGroup> userGroupList = userGroupRepository.findByUserId(userId);
        List<GroupDTO> groupDTOList = new ArrayList<>();
        if(!userGroupList.isEmpty()){
            List<UserGroup> userGroups = userGroupList;

            userGroups.forEach(userGroup -> {
                GroupDTO groupDTO = new GroupDTO();
                groupDTO.setId(userGroup.getGroup().getId());
                groupDTO.setGroupName(userGroup.getGroup().getGroupName());
                groupDTO.setUserFirstName(userGroup.getUser().getFirstName());
                groupDTO.setUserLastName(userGroup.getUser().getLastName());

                groupDTOList.add(groupDTO);
            });
        }

        return groupDTOList;
    }
}
