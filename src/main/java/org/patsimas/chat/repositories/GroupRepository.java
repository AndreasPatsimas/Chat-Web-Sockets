package org.patsimas.chat.repositories;

import org.patsimas.chat.dao.GroupDAO;
import org.patsimas.chat.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findGroupByGroupNameEquals(String groupName);

    @Query(value = "SELECT groups.id as id,groups.group_name as groupName, users.first_name as userFirstName, users.last_name as userLastName " +
            "FROM groups JOIN users ON groups.created_by = users.id WHERE groups.group_name = :groupName And users.id = :userId"
            , nativeQuery = true)
    Optional<GroupDAO> findGroupByGroupNameAndUser(String groupName, Long userId);

    @Query(value = "SELECT groups.id as id,groups.group_name as groupName, users.first_name as userFirstName, users.last_name as userLastName " +
            "FROM groups JOIN users ON groups.created_by = users.id WHERE groups.created_by = :userId"
            , nativeQuery = true)
    List<GroupDAO> findGroupsByUser(Long userId);
}
