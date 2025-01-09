package ee.stefanie.bowling.repository;

import ee.stefanie.bowling.entity.Frame;
import ee.stefanie.bowling.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrameRepository extends JpaRepository<Frame, Long> {
    List<Frame> findAllByPlayer(Player player);
}
