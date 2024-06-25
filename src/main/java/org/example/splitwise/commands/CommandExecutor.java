package org.example.splitwise.commands;

import jakarta.persistence.StoredProcedureQuery;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Data
@Component
public class CommandExecutor {
    private int commandNumber;
    private Scanner sc = new Scanner(System.in);
    private RegisterUserCommand registerUserCommand;
    private LoginUserCommand loginUserCommand;
    private CreateGroupCommand createGroupCommand;
    private AddMembersToGroupCommand addGroupMembers;
    private DisplayUserGroupCommand addExpenseToGroup;
    private SettleUpUserCommand settleUpUserCommand;

    public CommandExecutor(RegisterUserCommand registerUserCommand, LoginUserCommand loginUserCommand, CreateGroupCommand createGroupCommand, AddMembersToGroupCommand addGroupMembers, DisplayUserGroupCommand addExpenseToGroup, SettleUpUserCommand settleUpUserCommand) {
        this.registerUserCommand = registerUserCommand;
        this.loginUserCommand = loginUserCommand;
        this.createGroupCommand = createGroupCommand;
        this.addGroupMembers = addGroupMembers;
        this.addExpenseToGroup = addExpenseToGroup;
        this.settleUpUserCommand = settleUpUserCommand;
    }

    public void runCommand() {
        System.out.println("Press Enter");
        while (!sc.nextLine().equals("exit")) {
            System.out.println("Please Enter The Command Number: ");
            System.out.println("Press 1 for Registering User");
            System.out.println("Press 2 for Login");
            System.out.println("Press 3 to Create Group");
            System.out.println("Press 4 to Add Members to Group");
            System.out.println("Press 5 to Add Expense to Group");
            System.out.println("Press 6 to SettleUp Expenses");
            String input = sc.nextLine();
            executeCommand(input);
        }
    }

    public void executeCommand(String commandNumber) {
        try {
            switch (commandNumber) {
                case "1":
                    registerUserCommand.execute();
                    break;
                case "2":
                    while (!loginUserCommand.execute()) {
                        System.out.println("Incorrect email or password. Please Try Again");
                    }
                    System.out.println("Logged in successfully.....");
                    break;
                case "3":
                    createGroupCommand.execute();
                    break;
                case "4":
                    addGroupMembers.execute();
                    break;
                case "5":
                    addExpenseToGroup.execute();
                    break;
                case "6":
                    settleUpUserCommand.execute();
                    break;
            }
        } catch (Exception e) {
            System.out.println("******    Arrrrrr... Error Occured    ******");
            System.out.println(e.getMessage());
        }
    }
}
