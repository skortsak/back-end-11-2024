package ee.stefanie.cardgame.controller;

import ee.stefanie.cardgame.entity.Card;
import ee.stefanie.cardgame.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("game")
public class GameController {

    private final GameService gameService;

    @PostMapping("/start")
    public Card startGame() {
        return gameService.startRound();
    }

    @PostMapping("/guess")
    public ResponseEntity<String> makeGuess(@RequestParam String guess) {
        return ResponseEntity.ok(gameService.evaluateGuess(guess));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveGame(@RequestParam String playerName) {
        gameService.saveGame(playerName);
        return ResponseEntity.ok().build();
    }
}
