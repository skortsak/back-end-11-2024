package ee.stefanie.bowling.repository;

import ee.stefanie.bowling.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository  extends JpaRepository<Player, Long> {
}
