package com.example.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.List;


import com.example.entity.Message;
import com.example.repository.MessageRepository;




@Service
public class MessageService {

    // private final MessageRepository messageRepository;

    // @Autowired
    // public MessageService(MessageRepository messageRepository) {
    //     this.messageRepository = messageRepository;
    // }

    
    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        // Add your validation logic here
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
        // You can add more validations as needed
        
        // Save the message to the repository
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


    // update message
    // public int updateMessage(Integer messageId, String newMessageText) {
    //     if (newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255) {
    //       return 0; // Update not successful
    //     }
    
    //     // Check if message exists before update
    //     Optional<Message> existingMessage = messageRepository.findById(messageId);
    //     if (!existingMessage.isPresent()) {
    //       return 0; // Update not successful (message not found)
    //     }
    
    //     try {
    //       return messageRepository.updateMessageText(messageId, newMessageText);
    //     } catch (Exception e) {
    //       // Log the exception for debugging
    //     //   log.error("Error updating message: {}", e.getMessage());
    //       return 0; // Update not successful
    //     }
    //   }
    

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
            // Log the error or handle it appropriately
            e.printStackTrace();
            return -1; // Return -1 to indicate an error
        }
    }




    public int updateMessageText(Integer messageId, String newMessageText) {
        Message existingMessage = messageRepository.findById(messageId).orElse(null);
        if (existingMessage == null) {
            return 0; // Message not found
        }

        existingMessage.setMessageText(newMessageText);
        messageRepository.save(existingMessage);
        return 1; // Message updated successfully
    }
}
