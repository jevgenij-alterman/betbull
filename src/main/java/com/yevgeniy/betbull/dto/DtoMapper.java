package com.yevgeniy.betbull.dto;

import com.yevgeniy.betbull.domain.Player;
import com.yevgeniy.betbull.domain.Team;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DtoMapper {

    private DtoMapper() {
    }

    public static PlayerDTO toPlayerDTO(Player player) {
        return new PlayerDTO(player.getId(), player.getName(), player.getMonthOfExperience());
    }


    public static List<PlayerDTO> toPlayerDTOList(List<Player> playerList) {
        return playerList.stream()
                .filter(Objects::nonNull)
                .map(DtoMapper::toPlayerDTO)
                .collect(Collectors.toList());
    }


    public static TeamDTO toTeamDTO(Team team) {
        return new TeamDTO(team.getId(), team.getTeamName());
    }


    public static List<TeamDTO> toTeamDTOList(List<Team> teamList) {
        return teamList.stream()
                .filter(Objects::nonNull)
                .map(DtoMapper::toTeamDTO)
                .collect(Collectors.toList());
    }
}
