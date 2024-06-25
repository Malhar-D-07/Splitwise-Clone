package org.example.splitwise.services;

import org.example.splitwise.models.Expense;
import org.example.splitwise.models.ExpenseUser;
import org.example.splitwise.repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ShellComponent
@Service
public class ExpenseService {
    private ExpenseRepo expenseRepo;

    @Autowired
    public ExpenseService(ExpenseRepo expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    @Transactional
    public void save(Expense expense) {
        expenseRepo.saveAndFlush(expense);
    }

    @ShellMethod(key = "add-expense-user")
    @Transactional
    public void addExpenseUser(ExpenseUser expenseUser, Expense expense) {
        List<ExpenseUser> expenseUsers = expense.getExpenseUsers();
        if (expenseUsers.isEmpty()) {
            expense.setExpenseUsers(expenseUsers);
        }
        else {
            expense.getExpenseUsers().add(expenseUser);
        }
    }
}
