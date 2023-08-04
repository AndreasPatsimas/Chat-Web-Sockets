package org.patsimas.chat.services;

import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.users.UsersDTO;
import org.patsimas.chat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UsersDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UsersDTO> usersDTOList = new ArrayList<>();
        users.forEach(user->{
            UsersDTO userDTO = new UsersDTO(user.getId(),user.getFirstName()+" "+user.getLastName(), user.getUserName());
            usersDTOList.add(userDTO);
        });

        return usersDTOList;
    }
}
