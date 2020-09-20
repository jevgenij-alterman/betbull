package com.yevgeniy.betbull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Long id;
    private String name;
    private int age;
    private int monthOfExperience;
}
