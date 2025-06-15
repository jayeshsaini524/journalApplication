package net.engineeringdigest.journalApp.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;

@Service
public class JournalEntityService {

    @Autowired
    JournalEntryRepository journalEntryRepository;
    
    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(JournalEntityService.class);
    
    //CREATE
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        UserEntry user = userService.findByUserName(userName);
        JournalEntry entry = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(entry);
        userService.saveEntry(user);
        return;
    }
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    //GET ALL
    public List<JournalEntry> show() {
        return journalEntryRepository.findAll();
    }

    //GET BY ID
    public Optional<JournalEntry> GetById(String Id) {
        return journalEntryRepository.findById(Id);
    }

    //DELETE BY ID
    @Transactional
    public boolean DelEntry(String Id, String username) {
        boolean isRemoved = false;
        try {
            UserEntry user = userService.findByUserName(username);
            isRemoved = user.getJournalEntries().removeIf(x -> x.getId().equals(Id)); // Didn't understand this 
            if(isRemoved) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(Id);
            }
            return isRemoved;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //DELETE ALL
    public void deleteAllEntry() {
        journalEntryRepository.deleteAll();
    }
}
