package net.engineeringdigest.journalApp.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.engineeringdigest.journalApp.entity.UserEntry;

public interface UserRepository extends MongoRepository<UserEntry, ObjectId> {

    UserEntry findByusername(String username);

    void deleteByusername(String username);
    
}
