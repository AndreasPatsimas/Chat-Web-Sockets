package org.patsimas.chat.services;

import org.patsimas.chat.dao.FriendsDAO;
import org.patsimas.chat.domain.Friends;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.friends.FriendsDTO;
import org.patsimas.chat.repositories.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendsService {

    @Autowired
    private FriendRepository friendRepository;

    public List<FriendsDTO> getFriends(Long userId){

        List<FriendsDAO> friendsDAOList = friendRepository.findFriendsByUser(userId);
        List<FriendsDTO> friendsList = new ArrayList<>();

        if(!friendsDAOList.isEmpty()){
            friendsDAOList.forEach(friend->{
                FriendsDTO friendsDTO = new FriendsDTO(friend.getId(),friend.getUsername(), friend.getFirstName()+" "+friend.getLastName());
                friendsList.add(friendsDTO);
            });
        }

        return friendsList;
    }
}
