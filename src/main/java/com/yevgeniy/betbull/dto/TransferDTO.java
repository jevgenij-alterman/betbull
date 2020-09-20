package com.yevgeniy.betbull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    private Long playerId;
    private Long teamId;
}
