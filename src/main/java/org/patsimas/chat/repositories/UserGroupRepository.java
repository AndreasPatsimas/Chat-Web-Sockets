package org.patsimas.chat.repositories;

import org.patsimas.chat.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup,Long> {

    @Query(value = "Select * from user_groups where group_id=:groupId and user_id=:userId",nativeQuery=true)
    Optional<UserGroup> findByGroupIdAndUserId(Long groupId, Long userId);
}
