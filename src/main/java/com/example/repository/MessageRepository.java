package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;
import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    Optional<Message> findById(Integer messageId);
    List<Message> findByPostedBy(Integer accountId);

    @Query(value = "UPDATE Message SET messageText = :newMessageText WHERE messageId = :messageId")
    int updateMessageText(@Param("messageId") Integer messageId, @Param("newMessageText") String newMessageText);

}
