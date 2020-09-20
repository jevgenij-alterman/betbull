package com.yevgeniy.betbull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class TeamHistory {
    private String teamName;
    private LocalDate signingDate;
}
