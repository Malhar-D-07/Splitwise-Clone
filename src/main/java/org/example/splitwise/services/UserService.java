package org.example.splitwise.services;

import org.example.splitwise.models.SplitwiseGroup;
import org.example.splitwise.models.User;
import org.example.splitwise.repo.GroupRepo;
import org.example.splitwise.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepo userRepository;
    private PasswordEncoder passwordEncoder;
    private SessionService sessionService;
    private GroupRepo groupRepo;

    public UserService(UserRepo userRepository, PasswordEncoder passwordEncoder, SessionService sessionService, GroupRepo groupRepo) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
        this.groupRepo = groupRepo;
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(passwordEncoder.matches(password, user.getPassword())) {
                sessionService.setCurrentUser(user);
                return Optional.of(user);
            }
        }

        System.out.println("Invalid Credentials. Please login with valid Credentials");
        return null;
    }

    public void registerUser(String email, String password, String name, String phoneNumber) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
        System.out.println("User Registration Successful :-)");
    }

    public User getCurrentUser() {
        return sessionService.getCurrentUser();
    }

    public void logout() {
        sessionService.clear();
    }

    @Transactional
    public void addUserToGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        SplitwiseGroup group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        user.addUserToGroup(group);

        userRepository.save(user);
        groupRepo.save(group);
    }

    public List<User> findAllById(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id +  "not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByIdWithGroups(Long id) {
        return userRepository.findByIdWithGroup(id);
    };
}
