package com.yevgeniy.betbull.dto;

import com.yevgeniy.betbull.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ContractFeeDTO {
    private BigDecimal price;
    private Currency currency;
}
