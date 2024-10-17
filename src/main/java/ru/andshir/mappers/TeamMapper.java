package ru.andshir.mappers;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.request.AddTeamDTO;
import ru.andshir.controllers.dto.response.TeamResponseDTO;
import ru.andshir.model.Team;

@Component
public class TeamMapper {

    public Team addTeamDtoToTeam(AddTeamDTO addTeamDTO) {
        Team team = new Team();
        team.setTeamName(addTeamDTO.getTeamName());
        return team;
    }

    public TeamResponseDTO teamToTeamResponseDto(Team team) {
        TeamResponseDTO teamResponseDTO = new TeamResponseDTO();
        teamResponseDTO.setId(team.getId());
        teamResponseDTO.setTeamName(team.getTeamName());
        return teamResponseDTO;
    }

}
