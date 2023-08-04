package org.patsimas.chat.repositories;

import org.patsimas.chat.dao.FriendsDAO;
import org.patsimas.chat.domain.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friends,Long> {

    @Query(value = "select user2.id as id, user2.username as username , user2.first_name as firstName, user2.last_name as lastName " +
            "from friends f join users as user1 on f.user_id = user1.id "+
            "join users as user2 on f.friend_id=user2.id WHERE f.user_id = :userId"
            , nativeQuery = true)
    List<FriendsDAO> findFriendsByUser(Long userId);
}
