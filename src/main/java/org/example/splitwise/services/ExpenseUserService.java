package org.example.splitwise.services;

import org.example.splitwise.models.ExpenseUser;
import org.example.splitwise.repo.ExpenseUserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpenseUserService {
    private ExpenseUserRepo expenseUserRepo;

    public ExpenseUserService(ExpenseUserRepo expenseUserRepo) {
        this.expenseUserRepo = expenseUserRepo;
    }

    @Transactional
    public void save(ExpenseUser expenseUser) {
        expenseUserRepo.save(expenseUser);
    }
}
