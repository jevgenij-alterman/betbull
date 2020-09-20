package com.yevgeniy.betbull.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(targetEntity = Player.class, fetch = FetchType.LAZY)
    private Player player;
    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
    private Team team;
    private BigDecimal contractPrice;
    private LocalDate signingDate;
}
