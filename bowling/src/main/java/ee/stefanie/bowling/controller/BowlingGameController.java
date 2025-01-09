package ee.stefanie.bowling.controller;

import ee.stefanie.bowling.entity.Frame;
import ee.stefanie.bowling.entity.Player;
import ee.stefanie.bowling.service.BowlingGameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bowling")
public class BowlingGameController {
    private final BowlingGameService bowlingGameService;

    public BowlingGameController(BowlingGameService bowlingGameService) {
        this.bowlingGameService = bowlingGameService;
    }

    // Lisada mängija
    @PostMapping("/players")
    public Player addPlayer(@RequestParam String name) {
        return bowlingGameService.addPlayer(name);
    }

    // Lisada viskeid mängijale
    @PostMapping("/roll/{playerId}")
    public void addRoll(@PathVariable Long playerId, @RequestParam int pins) {
        bowlingGameService.addRoll(playerId, pins);
    }

    // Mängija skoori saamine
    @GetMapping("/score/{playerId}")
    public int getScore(@PathVariable Long playerId) {
        return bowlingGameService.getScore(playerId);
    }

    // Kõik freimid mängijale
    @GetMapping("/frames/{playerId}")
    public List<Frame> getFrames(@PathVariable Long playerId) {
        return bowlingGameService.getFrames(playerId);
    }
}
