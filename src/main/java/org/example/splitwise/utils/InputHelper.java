package org.example.splitwise.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

public class InputHelper {

    static final Scanner scanner = new Scanner(System.in);

    public static synchronized String getInput(String message) {
        System.out.println(message + "\n");
        return scanner.nextLine();
    }
}
