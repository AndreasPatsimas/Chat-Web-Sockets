package org.patsimas.chat.services;

import org.patsimas.chat.domain.Group;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.domain.UserGroup;
import org.patsimas.chat.repositories.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

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

}
