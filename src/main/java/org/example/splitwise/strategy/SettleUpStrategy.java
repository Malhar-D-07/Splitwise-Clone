package org.example.splitwise.strategy;

import org.example.splitwise.models.Expense;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SettleUpStrategy {
    public List<Expense> settleUp(List<Expense> expenses);
}
