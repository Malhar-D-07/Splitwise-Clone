package org.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Expense extends BaseModel {

    private String description;
    private Double amount;

    @ManyToOne
    private User createdBy;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "splitwise_group_id")
    private SplitwiseGroup group;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;

    @OneToMany(mappedBy = "expense",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    List<ExpenseUser> expenseUsers = new ArrayList<>();
}
