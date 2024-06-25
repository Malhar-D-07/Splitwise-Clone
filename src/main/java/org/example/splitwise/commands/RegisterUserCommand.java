package org.example.splitwise.commands;

import org.example.splitwise.services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class RegisterUserCommand implements Command {
    private Scanner sc;
    private UserService userService;

    public RegisterUserCommand(UserService userService) {
        sc = new Scanner(System.in);
        this.userService = userService;
    }

    @Override
    public boolean execute() {
        System.out.println("Thanks!!!! You are registering on our app now\n");

        System.out.println("Please Enter your name:");
        String name = sc.nextLine();

        System.out.println("Please Enter your phone number:");
        String phoneNumber = sc.nextLine();

        System.out.println("Please Enter your email:");
        String email = sc.nextLine();

        System.out.println("Please Enter your password:");
        String password = sc.nextLine();

        userService.registerUser(email, password, name, phoneNumber);
        return true;
    }
}
