package org.example.splitwise.services;

import org.example.splitwise.models.Expense;
import org.example.splitwise.models.ExpenseUser;
import org.example.splitwise.models.SplitwiseGroup;
import org.example.splitwise.models.User;
import org.example.splitwise.repo.ExpenseRepo;
import org.example.splitwise.repo.ExpenseUserRepo;
import org.example.splitwise.repo.GroupRepo;
import org.example.splitwise.repo.UserRepo;
import org.example.splitwise.strategy.SettleUpStrategy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SettleUpService {
    private UserRepo userRepo;
    private ExpenseUserRepo expenseUserRepo;
    private SettleUpStrategy settleUpStrategy;
    private GroupRepo groupRepo;
    private ExpenseRepo expenseRepo;

    public SettleUpService(UserRepo userRepo, ExpenseUserRepo expenseUserRepo, SettleUpStrategy settleUpStrategy, GroupRepo groupRepo, ExpenseRepo expenseRepo) {
        this.userRepo = userRepo;
        this.expenseUserRepo = expenseUserRepo;
        this.settleUpStrategy = settleUpStrategy;
        this.groupRepo = groupRepo;
        this.expenseRepo = expenseRepo;
    }

    public List<Expense> settleUpUser(Long userId) {
        Optional<User> optionalUser = this.userRepo.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("User not present....!!!!");
        }

        User user = optionalUser.get();
        List<ExpenseUser> expenseUsers = expenseUserRepo.findAllByUser(user);
        Set<Expense> expenseSet = new HashSet<>();

        for (ExpenseUser expenseUser: expenseUsers) {
            expenseSet.add(expenseUser.getExpense());
        }

        List<Expense> expenses = settleUpStrategy.settleUp(expenseSet.stream().toList());
        return expenses;
    }

    public List<Expense> settleUpGroup(Long groupId) {
        Optional<SplitwiseGroup> optionalSplitwiseGroup = groupRepo.findById(groupId);
        if(optionalSplitwiseGroup.isEmpty()) {
            throw new RuntimeException("Group id does not exist");
        }
        SplitwiseGroup group = optionalSplitwiseGroup.get();
        List<Expense> groupExpenses = expenseRepo.findAllByGroup(group);

        return settleUpStrategy.settleUp(groupExpenses);
    }
}
