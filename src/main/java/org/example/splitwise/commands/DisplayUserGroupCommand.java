package org.example.splitwise.commands;

import org.example.splitwise.models.*;
import org.example.splitwise.services.ExpenseService;
import org.example.splitwise.services.ExpenseUserService;
import org.example.splitwise.services.GroupService;
import org.example.splitwise.services.UserService;
import org.example.splitwise.utils.InputHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class DisplayUserGroupCommand implements Command {

    private UserService userService;
    private GroupService groupService;
    private ExpenseUserService expenseUserService;
    private ExpenseService expenseService;

    public DisplayUserGroupCommand(UserService userService, GroupService groupService, ExpenseUserService expenseUserService, ExpenseService expenseService) {
        this.userService = userService;
        this.groupService = groupService;
        this.expenseUserService = expenseUserService;
        this.expenseService = expenseService;
    }

    @Override
    @Transactional
    public boolean execute() {
        User loggedInUser = userService.getCurrentUser();
        User user = userService.findByIdWithGroups(Long.valueOf(loggedInUser.getId())).orElseThrow(() -> new RuntimeException("User Not Found"));
        List<SplitwiseGroup> userGroups = user.getGroups();

        if(userGroups.isEmpty()) {
            System.out.println("You are not part of any group. Below are some groups you can be a part of...");
            List<SplitwiseGroup> allGroups = groupService.findAllGroups();
            for(SplitwiseGroup group: allGroups) {
                System.out.println(group.getId() + ". " + group.getName());
            }

            String groupId = InputHelper.getInput("Enter the group id to join it");
            userService.addUserToGroup(Long.valueOf(user.getId()), Long.parseLong(groupId));
        }
        else {
            System.out.println("Your Groups are: ");
            for(SplitwiseGroup group: userGroups) {
                System.out.println(group.getId() + ". " + group.getName());
            }

            String groupId = InputHelper.getInput("Enter the group id to add Expense");
            SplitwiseGroup group = groupService.findById(Long.valueOf(groupId)).
                    orElseThrow(() -> new RuntimeException("Group not found"));
            Expense expense = new Expense();
            expense.setGroup(group);
            expense.setCreatedBy(userService.getCurrentUser());
            expense.setExpenseType(ExpenseType.NORMAL);
            String description = InputHelper.getInput("Enter the Expense description");
            Double amount = Double.valueOf((InputHelper.getInput("Enter Amount")));
            expense.setAmount(amount);
            expense.setDescription(description);
            expenseService.save(expense);

            List<User> groupMembers = group.getMembers();
            System.out.println("Group members are: ");
            for(User member: groupMembers) {
                System.out.println(member.getId() + ". " + member.getName());
            }
            String expenseInvolvedUsers = InputHelper.getInput("Enter comma seperated id's of involved user");

            List<Long> ids = Arrays.stream(expenseInvolvedUsers.split(","))
                    .map(Long::parseLong)
                    .toList();
            for(Long userId: ids) {
                ExpenseUser expenseUser = new ExpenseUser();
                expenseUser.setUser(userService.findById(userId));
                expenseUser.setExpenseUserType(ExpenseUserType.HAD_TO_PAY);
                expenseUser.setAmount(amount/ids.size());
                expenseUser.setExpense(expense);
                expenseUserService.save(expenseUser);
                expenseService.addExpenseUser(expenseUser, expense);
            }
        }
        return true;
    }
}
