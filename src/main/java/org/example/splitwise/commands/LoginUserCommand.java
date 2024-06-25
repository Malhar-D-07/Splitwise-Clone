package org.example.splitwise.commands;

import org.example.splitwise.models.User;
import org.example.splitwise.services.UserService;
import org.example.splitwise.utils.InputHelper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class LoginUserCommand implements Command {

    private Scanner sc;
    private UserService userService;

    public LoginUserCommand(UserService userService) {
        sc = new Scanner(System.in);
        this.userService = userService;
    }

    @Override
    public boolean execute() {
        System.out.println("Please Enter Your Login Credentials\n");
        String email = InputHelper.getInput("Please Enter your email:");

        String password = InputHelper.getInput("Please Enter your password:");
        Optional<User> user = userService.authenticate(email, password);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }
}
