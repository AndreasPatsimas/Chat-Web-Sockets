package org.patsimas.chat.repositories;

import org.patsimas.chat.domain.Message;
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

    List<Message> findAllByRecipientAndSender(String recipient, String sender);
}
