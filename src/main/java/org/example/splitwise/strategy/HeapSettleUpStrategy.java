package org.example.splitwise.strategy;

import org.example.splitwise.models.Expense;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeapSettleUpStrategy implements  SettleUpStrategy{
    @Override
    public List<Expense> settleUp(List<Expense> expenses) {
//        TODO: Complete this
        return null;
    }
}
