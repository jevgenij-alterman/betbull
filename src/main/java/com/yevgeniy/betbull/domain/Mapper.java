package com.yevgeniy.betbull.domain;

import com.yevgeniy.betbull.dto.PlayerDTO;

public class Mapper {

    private Mapper() {
    }

    public static Player toPlayer(PlayerDTO playerDTO, Long id) {
        return new Player(id, playerDTO.getName(), playerDTO.getMonthOfExperience());
    }

}
