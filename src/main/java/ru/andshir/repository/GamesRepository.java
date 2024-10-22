package ru.andshir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andshir.model.Game;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long> {

}
