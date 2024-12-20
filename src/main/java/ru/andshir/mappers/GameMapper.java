package ru.andshir.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.request.AddGameDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.controllers.dto.response.GameStatusResponseDTO;
import ru.andshir.controllers.dto.response.RoundResponseDTO;
import ru.andshir.controllers.dto.response.TeamResponseDTO;
import ru.andshir.model.Game;
import ru.andshir.model.Question;
import ru.andshir.model.Round;
import ru.andshir.model.Team;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final RoundMapper roundMapper;
    private final TeamMapper teamMapper;

    public Game addGameDtoToGame(AddGameDTO addGameDTO) {
        Game game = new Game();
        game.setGameDate(addGameDTO.getGameDate());
        game.setRoundsWithQuestions(new ArrayList<>());
        game.setNumberOfRounds(addGameDTO.getNumberOfRounds());
        return game;
    }

    public GameResponseDTO gameToGameResponseDTO(Game game) {
        GameResponseDTO gameResponseDTO = new GameResponseDTO();
        gameResponseDTO.setId(game.getId());
        gameResponseDTO.setGameDate(game.getGameDate());
        gameResponseDTO.setNumberOfRounds(game.getNumberOfRounds());
        for (Round round: game.getRoundsWithQuestions()) {
            RoundResponseDTO roundResponseDTO = roundMapper.roundToRoundResponseDTO(round);
            gameResponseDTO.getQuestionsInRounds().add(roundResponseDTO);
        }
        return gameResponseDTO;
    }

    public GameStatusResponseDTO gameToGameStatusResponseDTO(Game game, List<Question> questions, List<Team> teamsInGame) {
        GameStatusResponseDTO gameStatusResponseDTO = new GameStatusResponseDTO();
        gameStatusResponseDTO.setId(game.getId());
        gameStatusResponseDTO.setGameDate(game.getGameDate());
        for (Question question: questions) {
            gameStatusResponseDTO.getQuestions().add(question);
        }
        gameStatusResponseDTO.setNumberOfRounds(game.getNumberOfRounds());

        List<TeamResponseDTO> teamResponseDTOs = new ArrayList<>();
        for (Team team: teamsInGame) {
            TeamResponseDTO teamResponseDTO = teamMapper.teamToTeamResponseDto(team);
            teamResponseDTOs.add(teamResponseDTO);
        }
        gameStatusResponseDTO.setTeams(teamResponseDTOs);

        return gameStatusResponseDTO;
    }
}
