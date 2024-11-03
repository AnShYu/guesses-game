package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andshir.controllers.dto.request.AddTeamDTO;
import ru.andshir.controllers.dto.response.TeamResponseDTO;
import ru.andshir.mappers.TeamMapper;
import ru.andshir.model.Team;
import ru.andshir.repository.TeamsRepository;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamsRepository teamsRepository;
    private final TeamMapper teamMapper;

    @Transactional
    public TeamResponseDTO saveTeam(AddTeamDTO addTeamDTO) {
        Team team = teamMapper.addTeamDtoToTeam(addTeamDTO);
        Team savedTeam = teamsRepository.save(team);
        return teamMapper.teamToTeamResponseDto(savedTeam);
    }

}
