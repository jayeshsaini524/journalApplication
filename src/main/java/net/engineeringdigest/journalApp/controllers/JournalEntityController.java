package net.engineeringdigest.journalApp.controllers;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.JournalEntityService;
import net.engineeringdigest.journalApp.service.UserService;

//controller --> service(component) --> repository(interface) ,  entity(@document())
//user ke liye specific function banayenge --> vid_14

@RestController
@RequestMapping("/journal")
public class JournalEntityController {

    @Autowired
    private JournalEntityService journalEntityService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUserJournalEntries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntry userEntry = userService.findByUserName(userName);
        ArrayList<JournalEntry> allJournalEntries = userEntry.getJournalEntries();

        if(allJournalEntries.isEmpty() || allJournalEntries == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allJournalEntries, HttpStatus.OK);
    }

    @GetMapping("id/{Id}")
    public ResponseEntity<?> getJournalEntry(@PathVariable String Id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntry user = userService.findByUserName(authentication.getName());
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(Id)).collect(Collectors.toList());

        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntityService.GetById(Id);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addData(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            journalEntityService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("id/{ID}")
    public ResponseEntity<JournalEntry> UpdateJournalEntry(@PathVariable String ID, @RequestBody JournalEntry newEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntry user = userService.findByUserName(authentication.getName());
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(ID)).collect(Collectors.toList());

        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntityService.GetById(ID); //eq1
            if(newEntry != null) {
                JournalEntry oldEntry = journalEntry.get();//eq2 using eq1
                //check for title
                if(newEntry.getTitle() != null) {
                    oldEntry.setTitle(newEntry.getTitle());
                }
                //check content
                if(newEntry.getContent() != null) {      
                    oldEntry.setContent(newEntry.getContent());
                }
                journalEntityService.saveEntry(oldEntry);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // @DeleteMapping//do not use
    // public ResponseEntity<?> Delete() {
    //     if(journalEntityService.show().isEmpty()) {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     journalEntityService.deleteAllEntry();
    //     return new ResponseEntity<>(HttpStatus.OK);
       
    // }

    @DeleteMapping("id/{ID}")
    public ResponseEntity<JournalEntry> DelFromEntry(@PathVariable String ID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        boolean isRemoved = journalEntityService.DelEntry(ID, username);
        if(isRemoved) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}