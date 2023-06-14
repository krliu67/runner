package com.example.service;

import com.example.model.User;
import com.example.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepo userRepo;


    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User addUser(User user){
        //user.setUserId(UUID.randomUUID().toString());
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        return userRepo.save(user);
    }

    public User updateUser(User user){
        return userRepo.save(user);
    }

    public void deleteEmployee(String userId){
        userRepo.deleteUserByUserId(userId);
    }

    public User findUserByUserId(String userId){
        return userRepo.findUserByUserId(userId);
    }

    public List<User> findUserByUserIds(List<String> userIds){
        return userRepo.findUserByUserIds(userIds);
    }

    public User findUserByUserName(String username){
        return userRepo.findUserByUserName(username);
    }
}
