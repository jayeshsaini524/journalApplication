package net.engineeringdigest.journalApp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.UserService;

//new:- indexed(unique = true), NotNull, DBRef
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> GetByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user = userService.findByUserName(username);
        if(user==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user ,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserEntry userEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntry DbUser = userService.findByUserName(username);
        if(DbUser != null) {
            DbUser.setUsername(userEntry.getUsername());
            DbUser.setPassword(userEntry.getPassword());
            userService.saveNewEntry(DbUser);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.DelEntry(authentication.getName());
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}