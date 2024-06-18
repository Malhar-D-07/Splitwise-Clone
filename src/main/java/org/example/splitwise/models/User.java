package org.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User extends BaseModel {
    private String name;
    private String phoneNumber;
    private String password;

    @ManyToMany(mappedBy = "members", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private List<SplitwiseGroup> groups;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ExpenseUser> userExpenses;
}
