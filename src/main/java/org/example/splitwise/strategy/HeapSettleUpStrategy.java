package org.example.splitwise.strategy;

import org.example.splitwise.models.Expense;
import org.example.splitwise.models.ExpenseUser;
import org.example.splitwise.models.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HeapSettleUpStrategy implements SettleUpStrategy {

    @Override
    @Transactional
    public List<Expense> settleUp(List<Expense> expenses) {
        Map<String, Double> expenseMap = new HashMap<>();
        for (Expense expense: expenses) {
            User expenseDoneBy = expense.getCreatedBy();
            expenseMap.put(expenseDoneBy.getName(), expenseMap.getOrDefault(expenseDoneBy.getName(), (double) 0) + expense.getAmount());

            for (ExpenseUser expenseUser: expense.getExpenseUsers()) {
                if (expenseMap.containsKey(expenseUser.getUser().getName())) {
                    Double newAmount = expenseMap.getOrDefault(expenseUser.getUser().getName(), (double) 0) - expenseUser.getAmount();
                    expenseMap.put(expenseUser.getUser().getName(), newAmount);
                }
                else {
                    Double existingAmount = expenseMap.getOrDefault(expenseUser.getUser().getName(), (double) 0);
                    expenseMap.put(expenseUser.getUser().getName(), existingAmount - expenseUser.getAmount());
                }
            }

        }
        System.out.println("Final Map: " + expenseMap.toString());
        return null;
    }
}
