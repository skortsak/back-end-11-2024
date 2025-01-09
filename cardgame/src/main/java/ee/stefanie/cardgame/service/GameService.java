package ee.stefanie.cardgame.service;

import ee.stefanie.cardgame.entity.Card;
import ee.stefanie.cardgame.entity.Game;
import ee.stefanie.cardgame.entity.Player;
import ee.stefanie.cardgame.repository.GameRepository;
import ee.stefanie.cardgame.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GameService {
    private final DeckService deckService;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    private int lives = 3;
    private int correctAnswers = 0;
    private Card baseCard;

    public Card startRound() {
        baseCard = deckService.drawCard();
        return baseCard;
    }


    public String evaluateGuess(String guess) {
        Card nextCard = deckService.drawCard();
        boolean correct = switch (guess.toLowerCase()) {
            case "higher" -> nextCard.getValue() > baseCard.getValue();
            case "lower" -> nextCard.getValue() < baseCard.getValue();
            case "equal" -> nextCard.getValue() == baseCard.getValue();
            default -> false;
        };

        if (correct) {
            correctAnswers++;
        } else {
            lives--;
        }

        baseCard = nextCard;

        if (lives == 0 || correctAnswers + (3 - lives) == 3) {
            return "GAME_OVER";
        }

        return correct ? "CORRECT" : "INCORRECT";
    }

    @Transactional
    public void saveGame(String playerName) {
        Player player = playerRepository.findByPlayerName(playerName)
                .orElseGet(() -> playerRepository.save(new Player(null, playerName)));

        gameRepository.save(new Game(null, player, correctAnswers, LocalDateTime.now().minusMinutes(5), LocalDateTime.now()));
    }
}
