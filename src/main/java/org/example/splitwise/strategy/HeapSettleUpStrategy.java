package org.example.splitwise.strategy;

import lombok.Getter;
import lombok.Setter;
import org.example.splitwise.models.Expense;
import org.example.splitwise.models.ExpenseUser;
import org.example.splitwise.models.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Getter
@Setter
class Pair {
    private String name;
    private Double amount;

    public Pair(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }
}

@Component
public class HeapSettleUpStrategy implements SettleUpStrategy {

    @Override
    @Transactional
    public List<Expense> settleUp(List<Expense> expenses) {
        Map<String, Double> expenseMap = new HashMap<>();
        for (Expense expense: expenses) {
            User expenseDoneBy = expense.getCreatedBy();
            expenseMap.put(expenseDoneBy.getName(), expenseMap.getOrDefault(expenseDoneBy.getName(), 0.0) + expense.getAmount());

            for (ExpenseUser expenseUser: expense.getExpenseUsers()) {
                Double newAmount = expenseMap.getOrDefault(expenseUser.getUser().getName(), 0.0) - expenseUser.getAmount();
                expenseMap.put(expenseUser.getUser().getName(), newAmount);
            }
        }

        PriorityQueue<Pair> minHeap = new PriorityQueue<>(Comparator.comparingDouble(Pair::getAmount));
        PriorityQueue<Pair> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b.getAmount(), a.getAmount()));

        for (Map.Entry<String, Double> entry : expenseMap.entrySet()) {
            if (entry.getValue() < 0) {
                minHeap.offer(new Pair(entry.getKey(), entry.getValue()));
            } else if (entry.getValue() > 0) {
                maxHeap.offer(new Pair(entry.getKey(), entry.getValue()));
            }
        }

        List<String> transactions = new ArrayList<>();

        while (!minHeap.isEmpty() && !maxHeap.isEmpty()) {
            Pair debtor = minHeap.poll();
            Pair creditor = maxHeap.poll();

            double settleAmount = Math.min(-debtor.getAmount(), creditor.getAmount());
            transactions.add(debtor.getName() + " pays " + settleAmount + " to " + creditor.getName());

            debtor.setAmount(debtor.getAmount() + settleAmount);
            creditor.setAmount(creditor.getAmount() - settleAmount);

            if (debtor.getAmount() < 0) {
                minHeap.offer(debtor);
            }

            if (creditor.getAmount() > 0) {
                maxHeap.offer(creditor);
            }
        }

        System.out.println("Transactions: " + transactions);
        return null;
    }
}
