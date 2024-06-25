package org.example.splitwise.services;

import org.example.splitwise.models.User;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private static ThreadLocal<User> currentUser = new ThreadLocal<>();

    public void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public void clear() {
        currentUser.remove();
    }
}
