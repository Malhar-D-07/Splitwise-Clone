package org.example.splitwise.commands;

import org.example.splitwise.models.SplitwiseGroup;
import org.example.splitwise.models.User;
import org.example.splitwise.services.GroupService;
import org.example.splitwise.services.UserService;
import org.example.splitwise.utils.InputHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateGroupCommand implements Command {

    private UserService userService;
    private GroupService groupService;

    public CreateGroupCommand(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    @Transactional
    public boolean execute() {
        String groupName = InputHelper.getInput("Enter Group name");
        User currentUser = userService.getCurrentUser();
        SplitwiseGroup splitwiseGroup = new SplitwiseGroup();
        splitwiseGroup.setCreatedBy(userService.findById(Long.valueOf(currentUser.getId())));
        splitwiseGroup.setName(groupName);
        groupService.save(splitwiseGroup);
        return false;
    }
}
