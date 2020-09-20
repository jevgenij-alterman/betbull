package com.yevgeniy.betbull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long id;
    private PlayerDTO player;
    private TeamDTO team;
    private BigDecimal contractPrice;
    private LocalDate signingDate;
}
