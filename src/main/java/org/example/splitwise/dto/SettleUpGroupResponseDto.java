package org.example.splitwise.dto;

import lombok.Data;
import org.example.splitwise.models.Expense;

import java.util.List;

@Data
public class SettleUpGroupResponseDto {
    private List<Expense> transactions;
}
