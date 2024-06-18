package org.example.splitwise.dto;

import lombok.Data;
import org.example.splitwise.models.Expense;

import java.util.List;

@Data
public class SettleUpUserResponseDto {
    private List<Expense> transactions;
}
