package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andshir.controllers.dto.request.AddTeamDTO;
import ru.andshir.controllers.dto.response.TeamResponseDTO;
import ru.andshir.mappers.TeamMapper;
import ru.andshir.model.Team;
import ru.andshir.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamResponseDTO saveTeam(AddTeamDTO addTeamDTO) {
        Team team = teamMapper.addTeamDtoToTeam(addTeamDTO);
        Team savedTeam = teamRepository.save(team);
        return teamMapper.teamToTeamResponseDto(savedTeam);
    }

}
