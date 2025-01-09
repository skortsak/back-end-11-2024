package ee.stefanie.cardgame.controller;

import ee.stefanie.cardgame.entity.Game;
import ee.stefanie.cardgame.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final GameRepository gameRepository;

    @GetMapping("/{playerName}/games")
    public List<Game> getPlayerGames(@PathVariable String playerName) {
        return gameRepository.findByPlayer_PlayerName(playerName);
    }

    @GetMapping("/scoreboard")
    public List<Game> getScoreboard(@RequestParam(defaultValue = "correctAnswers") String sortBy) {
        return gameRepository.findAll(Sort.by(Sort.Direction.DESC, sortBy));
    }
}
