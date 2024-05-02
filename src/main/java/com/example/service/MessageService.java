package com.example.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;
import java.util.List;

import com.example.entity.Message;
import com.example.repository.MessageRepository;




@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;

    // Create message
    public Message createMessage(Message message) {
       
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        if (message.getPostedBy() == null || message.getMessageText() == null || message.getTimePostedEpoch() == null) {
            throw new IllegalArgumentException("Message properties cannot be null");
        }
        if (message.getMessageText().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be empty");
        }
        if (message.getMessageText().length() > 254) {
            throw new IllegalArgumentException("Message text cannot exceed 254 characters");
        }
        
        return messageRepository.save(message);
    }   


    // get all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
      }


    //get message by id
    public Optional<Message> getMessageById(Integer messageId) {
    return messageRepository.findById(messageId);
    }


    // get message written by a particular user
    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
      }

    

    // delete message
    public int deleteMessage(Integer messageId) {
        try {
            Optional<Message> optionalMessage = messageRepository.findById(messageId);

            if (optionalMessage.isPresent()) {
                messageRepository.deleteById(messageId);
                return 1; // Message deleted successfully (1 row modified)
            } else {
                return 0; // Message not found (0 rows modified)
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate an error
        }
    }


    // update message
    public int updateMessageText(Message message) {
        Message existingMessage = messageRepository.findById(message.getMessageId()).orElse(null);
        if (existingMessage == null) {
            return 0; // Message not found
        }
    
        existingMessage.setMessageText(message.getMessageText());
        messageRepository.save(existingMessage);
        return 1; // Message updated successfully
    }
    

}


