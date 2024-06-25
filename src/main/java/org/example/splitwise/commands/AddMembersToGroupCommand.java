package org.example.splitwise.commands;

import org.example.splitwise.models.SplitwiseGroup;
import org.example.splitwise.models.User;
import org.example.splitwise.services.GroupService;
import org.example.splitwise.services.UserService;
import org.example.splitwise.utils.InputHelper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AddMembersToGroupCommand implements Command {

    private final UserService userService;
    private final GroupService groupService;

    public AddMembersToGroupCommand(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public boolean execute() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("No users present in system. Please add some users");
        } else {
            String groupId = InputHelper.getInput("Enter Group Id to add users into it");
            List<Long> userIdList = users.stream().map((u) -> Long.valueOf(u.getId())).toList();
            System.out.println("List of Users: " + userIdList);
            String userIds = InputHelper.getInput("Enter comma seperated user ids to add into group");
            List<Long> usersToAdd = Arrays.stream(userIds.split(","))
                    .map(Long::parseLong)
                    .toList();

             for (Long userId: usersToAdd) {
                 groupService.addMember(userId, Long.valueOf(groupId));
             }
            return true;
        }

    }
}
