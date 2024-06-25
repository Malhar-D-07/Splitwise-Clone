package org.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "splitwise_group")
public class SplitwiseGroup extends BaseModel {
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private User createdBy;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "splitwise_group_user",
            joinColumns = @JoinColumn(name = "splitwise_group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    @Transactional
    public void addMember(User user) {
        if (!this.members.contains(user)) {
            this.members.add(user);
            user.getGroups().add(this);
        }
    }
}
