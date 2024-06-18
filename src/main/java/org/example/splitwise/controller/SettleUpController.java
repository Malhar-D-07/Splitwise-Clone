package org.example.splitwise.controller;

import org.example.splitwise.dto.SettleUpGroupRequestDto;
import org.example.splitwise.dto.SettleUpGroupResponseDto;
import org.example.splitwise.dto.SettleUpUserRequestDto;
import org.example.splitwise.dto.SettleUpUserResponseDto;
import org.example.splitwise.models.Expense;
import org.example.splitwise.services.SettleUpService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SettleUpController {

    private SettleUpService settleUpService;

    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    public SettleUpUserResponseDto settleUser(SettleUpUserRequestDto userRequest) {
        List<Expense> transactions = settleUpService.settleUpUser(userRequest.getUserId());
        SettleUpUserResponseDto settleUpUserResponseDto = new SettleUpUserResponseDto();
        settleUpUserResponseDto.setTransactions(transactions);
        return settleUpUserResponseDto;
    }

    public SettleUpGroupResponseDto settleUser(SettleUpGroupRequestDto groupRequest) {
        List<Expense> transactions = settleUpService.settleUpUser(groupRequest.getGroupId());
        SettleUpGroupResponseDto settleUpGroupResponseDto = new SettleUpGroupResponseDto();
        settleUpGroupResponseDto.setTransactions(transactions);
        return settleUpGroupResponseDto;
    }

}
