package net.engineeringdigest.journalApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/public")
public class Default {

    @Autowired
    UserService userService;
    
    @PostMapping("/create-user")
    public ResponseEntity<?> postNewUserEntry(@RequestBody UserEntry userEntry) {
        try {
            userService.saveNewEntry(userEntry);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
