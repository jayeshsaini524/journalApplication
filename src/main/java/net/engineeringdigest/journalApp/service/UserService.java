package net.engineeringdigest.journalApp.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.engineeringdigest.journalApp.Repository.UserRepository;
import net.engineeringdigest.journalApp.entity.UserEntry;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    //CREATE
    public void saveNewEntry(UserEntry userEntry) {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRole("Basic");
        userRepository.save(userEntry);
        return;
    }

    public void saveEntry(UserEntry userEntry) {
        userRepository.save(userEntry);
    }

    //GET ALL
    public List<UserEntry> show() {
        return userRepository.findAll();
    }

    //GET BY ID
    public Optional<UserEntry> GetById(ObjectId Id) {
        return userRepository.findById(Id);
    }

    //DELETE BY ID
    public void DelEntry(String username) {
        userRepository.deleteByusername(username);
    }

    //GET FROM USERNAME
    public UserEntry findByUserName(String username) {
        return userRepository.findByusername(username);
    }

}
