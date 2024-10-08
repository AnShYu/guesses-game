package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andshir.controllers.dto.request.GameDTO;
import ru.andshir.mappers.GameMapper;
import ru.andshir.model.Game;
import ru.andshir.model.Question;
import ru.andshir.repository.GameRepository;
import ru.andshir.repository.QuestionsRepository;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameMapper gameMapper;
    private final GameRepository gameRepository;
    private final QuestionsRepository questionsRepository;

    public long saveGame(GameDTO gameDTO) {
        Game game = gameMapper.dtoToGame(gameDTO);
        Game savedGame = gameRepository.save(game); // при сохранении обычно не получают сразу id. Получают объект, а потом из него уже получают нужные поля (например, ID)
        return savedGame.getId();
    }


    public void addQuestionToGame(long gameId,long questionId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("No game with such ID"));
        Question question = questionsRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("No question with such ID"));
        game.getGameQuestions().add(question);
        gameRepository.save(game);
    }

}
