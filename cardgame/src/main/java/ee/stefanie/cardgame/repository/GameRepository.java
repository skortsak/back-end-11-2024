package ee.stefanie.cardgame.repository;

import ee.stefanie.cardgame.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByPlayer_PlayerName(String playerName);
}
