package org.example.splitwise.repo;

import org.example.splitwise.models.Expense;
import org.example.splitwise.models.SplitwiseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    List<Expense> findAllByGroup(SplitwiseGroup group);
}
