package org.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ExpenseUser extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double amount;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseUserType expenseUserType;
}
