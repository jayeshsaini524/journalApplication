package net.engineeringdigest.journalApp.entity;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.NonNull;


import lombok.Data;

@Data
@Document(collection  = "UserEntries")
public class UserEntry {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;
    
    @DBRef
    private ArrayList<JournalEntry> journalEntries = new ArrayList<>();
    
    private String role;
}
