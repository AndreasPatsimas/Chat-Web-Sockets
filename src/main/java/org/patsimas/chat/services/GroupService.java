package org.patsimas.chat.services;

import org.patsimas.chat.dao.GroupDAO;
import org.patsimas.chat.domain.Group;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.groups.GroupDTO;
import org.patsimas.chat.repositories.GroupRepository;
import org.patsimas.chat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public GroupDTO getGroup(String groupName, User user){

        Optional<GroupDAO> groupOptional = groupRepository.findGroupByGroupNameAndUser(groupName, user.getId());

        if(groupOptional.isPresent()){
            return prepareDTO(groupOptional.get());
        }

        Group group = new Group();
        group.setGroupName(groupName);
        group.setCreatedByUser(user);

        group = groupRepository.save(group);

       GroupDTO groupDTO = new GroupDTO();
       groupDTO.setId(group.getId());
       groupDTO.setGroupName(group.getGroupName());
       groupDTO.setUserFirstName(group.getCreatedByUser().getFirstName());
       groupDTO.setUserLastName(group.getCreatedByUser().getLastName());

        return groupDTO;
    }

    public Group getGroup(Long groupId){

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        return groupOptional.get();
    }

    private GroupDTO prepareDTO(GroupDAO groupDAO){

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(groupDAO.getId());
        groupDTO.setGroupName(groupDAO.getGroupName());
        groupDTO.setUserFirstName(groupDAO.getUserFirstName());
        groupDTO.setUserLastName(groupDAO.getUserLastName());

        return groupDTO;
    }

    public GroupDTO createGroup(String groupName, Long userId) throws Exception {

        Optional<GroupDAO> groupOptional = groupRepository.findGroupByGroupNameAndUser(groupName, userId);

        if(groupOptional.isPresent()){
            throw new Exception("Group name already exists");
        }

        Optional<User> userOptional = userRepository.findById(userId);

        Group group = new Group();
        group.setGroupName(groupName);
        group.setCreatedByUser(userOptional.get());

        group = groupRepository.save(group);

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setGroupName(group.getGroupName());
        groupDTO.setUserFirstName(group.getCreatedByUser().getFirstName());
        groupDTO.setUserLastName(group.getCreatedByUser().getLastName());

        return groupDTO;
    }

    public List<GroupDTO> getGroups(Long userId){

        List<GroupDTO> groupDTOList = new ArrayList<>();
        List<GroupDAO> groupDAOList = groupRepository.findGroupsByUser(userId);
        if(!groupDAOList.isEmpty()){
            groupDAOList.forEach(group -> {
                GroupDTO groupDTO = new GroupDTO();
                groupDTO.setId(group.getId());
                groupDTO.setGroupName(group.getGroupName());
                groupDTO.setUserLastName(group.getUserLastName());
                groupDTO.setUserFirstName(group.getUserFirstName());

                groupDTOList.add(groupDTO);
            });
        }
        return groupDTOList;
    }
}
