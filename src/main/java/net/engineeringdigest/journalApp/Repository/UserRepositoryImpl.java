package net.engineeringdigest.journalApp.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import net.engineeringdigest.journalApp.entity.UserEntry;

public class UserRepositoryImpl {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<UserEntry> getFromUserName() {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is("jayesh"));
        return mongoTemplate.find(query, UserEntry.class);
    }

}
