package org.example.splitwise.commands;

import org.example.splitwise.services.SettleUpService;
import org.example.splitwise.utils.InputHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpUserCommand implements Command {

    private SettleUpService settleUpService;

    @Autowired
    public SettleUpUserCommand(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    @Override
    public boolean execute() {
        String groupId = InputHelper.getInput("Enter the group Id to settle up");
        settleUpService.settleUpGroup(Long.valueOf(groupId));
        return true;
    }
}
