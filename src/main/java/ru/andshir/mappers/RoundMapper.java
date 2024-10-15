package ru.andshir.mappers;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.request.AddQuestionToGameDTO;
import ru.andshir.controllers.dto.response.RoundResponseDTO;
import ru.andshir.model.Round;

@Component
public class RoundMapper {

    public RoundResponseDTO roundToRoundResponseDTO(Round round) {
        RoundResponseDTO roundResponseDTO = new RoundResponseDTO();
        roundResponseDTO.setRoundNumber(round.getRoundNumber());
        roundResponseDTO.setQuestionId(round.getQuestionId());
        roundResponseDTO.setGameId(round.getGameId());
        return roundResponseDTO;
    }

    public Round addQuestionToGameDtoToRound(AddQuestionToGameDTO addQuestionDTO, long gameId) {
        Round round = new Round();
        round.setGameId(gameId);
        round.setRoundNumber(addQuestionDTO.getRoundNumber());
        round.setQuestionId(addQuestionDTO.getQuestionId());
        return round;
    }

}
