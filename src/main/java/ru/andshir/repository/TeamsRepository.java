package ru.andshir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andshir.model.Team;

import java.util.List;

@Repository
public interface TeamsRepository extends JpaRepository<Team, Long> {

    int countByGameId(long gameId);

    List<Team> findTeamByGameId(long gameId);

}
