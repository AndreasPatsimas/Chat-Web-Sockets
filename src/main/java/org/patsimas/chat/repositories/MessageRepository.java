package org.patsimas.chat.repositories;

import org.patsimas.chat.domain.Message;
import org.patsimas.chat.dao.MessageDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select * from messages where (sender_id = :senderId or sender_id = :receiverId) and " +
            "(receiver_id = :senderId or receiver_id = :receiverId)",
            nativeQuery = true)
    Page<Message> findByUsers(@Param("senderId") long senderId,
                           @Param("receiverId") long receiverId,
                           Pageable pageable);

    @Query(value = "select count(*) from messages where (sender_id = :senderId or sender_id = :receiverId) and " +
            "(receiver_id = :senderId or receiver_id = :receiverId)",
            nativeQuery = true)
    int countByUsers(@Param("senderId") long senderId, @Param("receiverId") long receiverId);

    @Query(value = "SELECT m.id as id, m.group_id as groupId, m.content as content, u.first_name as senderFirstName, u.last_name as senderLastName, " +
            "m.recorddate as messageTimestamp "+
            "FROM messages as m " +
            "JOIN user_groups as usergroup ON usergroup.group_id = m.group_id " +
            "JOIN users as u ON m.sender_id=u.id "+
            "WHERE usergroup.user_id = :userId ORDER by m.recorddate DESC",
            nativeQuery = true)
    List<MessageDAO> findByUser(@Param("userId") long userId);

    @Query(value = "Select m.id as id,m.group_id as groupId, m.content as content, users.first_name as senderFirstName, users.last_name as senderLastName, "+
            "m.recorddate as messageTimestamp FROM messages as m JOIN groups on groups.id = m.group_id "+
            "JOIN users on users.id = m.sender_id "+
            "WHERE groups.id = :groupId ORDER BY m.recorddate DESC"
            , nativeQuery = true)
    List<MessageDAO> findMessageByGroup(@Param("groupId") long groupId);
}
