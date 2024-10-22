package ru.andshir.mappers;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.request.AnswerDTO;
import ru.andshir.model.Answer;

@Component
public class AnswerMapper {

    public Answer DtoToAnswer(AnswerDTO answerDTO, long gameId, int roundNumber) {
        Answer answer = new Answer();
        answer.setGameId(gameId);
        answer.setAnswer(answerDTO.getAnswer());
        answer.setTeamId(answerDTO.getTeamId());
        answer.setRoundNumber(roundNumber);
        return answer;
    }

}
