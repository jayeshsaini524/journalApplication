package net.engineeringdigest.journalApp.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.engineeringdigest.journalApp.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {
    
} 
