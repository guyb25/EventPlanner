package com.eventPlanner.dataAccess.userEvents.services;

import com.eventPlanner.dataAccess.userEvents.repositories.UserRepository;
import com.eventPlanner.dataAccess.userEvents.schemas.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {
    private final UserRepository userRepository;

    @Autowired
    public UserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean doAllParticipantsExistByNames(List<String> names) {
        for (String name : names) {
            if (!userRepository.existsUserByName(name)) {
                return false;
            }
        }

        return true;
    }

    public String tryGetUsernameById(Long userId) {
        var user = userRepository.findUserById(userId);
        return user == null? null : user.getName();
    }

    public Long tryGetUserIdByName(String name) {
        var user = userRepository.findUserByName(name);
        return user == null ? null : user.getId();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public boolean isUsernameTaken(String username) {
        return userRepository.existsUserByName(username);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public boolean doUsernameAndPasswordMatch(String username, String password) {
        return userRepository.existsByNameAndPassword(username, password);
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
