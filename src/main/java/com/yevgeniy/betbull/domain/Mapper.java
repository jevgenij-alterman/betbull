package com.yevgeniy.betbull.domain;

import com.yevgeniy.betbull.dto.PlayerDTO;
import com.yevgeniy.betbull.dto.TeamDTO;

public class Mapper {

    private Mapper() {
    }

    public static Player toPlayer(PlayerDTO playerDTO, Long id) {
        return new Player(id, playerDTO.getName(), playerDTO.getMonthOfExperience());
    }

    public static Team toTeam(TeamDTO teamDTO, Long id) {
        return new Team(id, teamDTO.getTeamName());
    }

}
