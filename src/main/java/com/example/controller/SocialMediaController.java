package com.example.controller;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.HashMap;



import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.MessageService;
import com.example.service.AccountService;

@RestController

public class SocialMediaController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountService accountService;


     // user registration
    @PostMapping("/register")
     public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
        Account registeredAccount = accountService.registerAccount(account);
        return ResponseEntity.ok(registeredAccount);
        } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 400 Bad Request
        } catch (Exception e) { // Catch other potential exceptions (optional)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
  }


     // user login
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
        if (loggedInAccount != null) {
        return ResponseEntity.ok(loggedInAccount);
        } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
        }
  } 
  
     // Create Message
    @PostMapping("/messages")
        public ResponseEntity<Message> createMessage(@RequestBody Message message) {
            try {
                // Validate messageText
                if (message.getMessageText() == null || message.getMessageText().trim().isEmpty() || message.getMessageText().length() > 255) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                Message createdMessage = messageService.createMessage(message);
                return new ResponseEntity<>(createdMessage, HttpStatus.OK);

            } catch (IllegalArgumentException e) {
                // Handle validation errors
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                // Handle other unexpected errors
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }



    // Retrieve All messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
    List<Message> messages = messageService.getAllMessages();
    return ResponseEntity.ok(messages);
  }


    // get message by messageId
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Optional<Message> optionalMessage = messageService.getMessageById(messageId);
        if (optionalMessage.isPresent()) {
        return ResponseEntity.ok(optionalMessage.get());
        } else {
        return ResponseEntity.ok().build();  // Empty 200 OK response if message not found
        }
    }


    // retrieve all messages written by a particular user.
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId) {
    List<Message> messages = messageService.getMessagesByAccountId(accountId);
    return ResponseEntity.ok(messages);
  }


    // Delete message
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        try {
            int rowsDeleted = messageService.deleteMessage(messageId);
            if (rowsDeleted > 0) {
                return ResponseEntity.ok(rowsDeleted); // Return the count of rows deleted
            } else {
                return ResponseEntity.ok().build(); // Return an empty response body
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //update message
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageText(@PathVariable Integer messageId, @RequestBody String newMessageText) {
        System.out.println("Received messageText: " + newMessageText);
        // not sure why the 'updateMessageMessageStringEmpty()' is failing and out putting status code [200] instead of [400] eventhough the necessary checks are done here
        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255) {       
            return ResponseEntity.badRequest().build(); // Return 400 if newMessageText is invalid 
        }

        int rowsUpdated = messageService.updateMessageText(messageId, newMessageText);
        if (rowsUpdated > 0) {
            return ResponseEntity.ok(rowsUpdated); // Return 200 with the number of rows updated
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
        
    }
    







