package com.yevgeniy.betbull.dto;

import com.yevgeniy.betbull.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Long id;
    private String teamName;
    private Currency currency;
}
