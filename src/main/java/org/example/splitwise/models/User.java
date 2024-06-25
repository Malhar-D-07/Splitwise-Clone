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
    private String email;

    @ManyToMany(mappedBy = "members", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private List<SplitwiseGroup> groups;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ExpenseUser> userExpenses;

    @Override
    public String toString() {
        return STR."User { name='\{name}\{'\''}, phoneNumber='\{phoneNumber}\{'\''}, password='\{password}\{'\''}, email='\{email}\{'\''}\{'}' }";
    }

    public void addUserToGroup(SplitwiseGroup group) {
        if (!this.groups.contains(group)) {
            this.groups.add(group);
            group.addMember(this);
        }
    }
}
