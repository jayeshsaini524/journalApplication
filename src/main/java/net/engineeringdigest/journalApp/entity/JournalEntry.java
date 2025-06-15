package net.engineeringdigest.journalApp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.NonNull;


import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "journal_entries")
@NoArgsConstructor //used for error with making jurnal entry inside userEntry
@Data
public class JournalEntry {

    @Id
    private String id;

    @NonNull
    private String title;

    private String content;

}
