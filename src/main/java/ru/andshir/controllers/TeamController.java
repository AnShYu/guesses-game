package ru.andshir.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.andshir.controllers.dto.request.AddTeamDTO;
import ru.andshir.controllers.dto.response.TeamResponseDTO;
import ru.andshir.service.TeamService;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public TeamResponseDTO saveTeam(@RequestBody AddTeamDTO addTeamDTO) {
        return teamService.saveTeam(addTeamDTO);
    }

}
