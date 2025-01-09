package ee.stefanie.bowling.service;

import ee.stefanie.bowling.entity.Frame;
import ee.stefanie.bowling.entity.Player;
import ee.stefanie.bowling.repository.FrameRepository;
import ee.stefanie.bowling.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BowlingGameService {

    private final PlayerRepository playerRepository;
    private final FrameRepository frameRepository;

    public BowlingGameService(PlayerRepository playerRepository, FrameRepository frameRepository) {
        this.playerRepository = playerRepository;
        this.frameRepository = frameRepository;
    }

    public Player addPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        return playerRepository.save(player);
    }

    public void addRoll(Long playerId, int pins) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        Frame currentFrame = getCurrentFrame(player);
        if (currentFrame == null) {
            currentFrame = createNewFrame(player);
        }

        processRoll(currentFrame, pins, player);
    }

    public int getScore(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        return player.getFrames().stream()
                .mapToInt(Frame::getScore)
                .sum();
    }

    private Frame getCurrentFrame(Player player) {
        return player.getFrames().stream()
                .filter(frame -> frame.getSecondRoll() == null)
                .findFirst().orElse(null);
    }

    private Frame createNewFrame(Player player) {
        Frame frame = new Frame();
        frame.setFrameNumber(player.getFrames().size() + 1);
        frame.setPlayer(player);
        return frameRepository.save(frame);
    }

    private void processRoll(Frame frame, int pins, Player player) {
        // Töötleme viset vastavalt sellele, kas tegemist on esimese või teise viskega või 10. freimiga

        if (frame.getFirstRoll() == null) {
            frame.setFirstRoll(pins);
            if (frame.getFrameNumber() < 10 && pins == 10) {
                frame.setScore(calculateStrikeBonus(player, frame));
            }
        } else if (frame.getSecondRoll() == null) {
            frame.setSecondRoll(pins);
            frame.setScore(calculateFrameScore(frame));
        } else if (frame.getFrameNumber() == 10) {
            frame.setThirdRoll(pins);
            frame.setScore(calculateFrameScore(frame));
        }

        frameRepository.save(frame);
    }

    public List<Frame> getFrames(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        return frameRepository.findAllByPlayer(player);
    }

    private int calculateStrikeBonus(Player player, Frame frame) {
        // Kui tegemist on Strike'iga, siis tuleb arvestada järgmisi kahte viset
        List<Frame> frames = player.getFrames();
        int strikeBonus = 0;

        if (frame.getFrameNumber() < 10) {
            Frame nextFrame = frames.get(frame.getFrameNumber());
            strikeBonus = nextFrame.getFirstRoll();

            // Kui järgmine freim on Strike, siis arvestame järgmise viske
            if (nextFrame.getFirstRoll() == 10 && nextFrame.getFrameNumber() < 9) {
                Frame nextNextFrame = frames.get(frame.getFrameNumber() + 1);
                strikeBonus += nextNextFrame.getFirstRoll();
            } else {
                strikeBonus += nextFrame.getSecondRoll();
            }
        }

        return strikeBonus;
    }

    private int calculateFrameScore(Frame frame) {
        // Kui tegemist on 10. freimiga, siis arvestame kõik kolm viset
        if (frame.getFrameNumber() == 10) {
            // Arvutame kõik kolm viset (isegi kui visked on 0)
            return frame.getFirstRoll() + frame.getSecondRoll() + frame.getThirdRoll();
        }

        // Kui tegemist on Strike'iga (10 visatakse esimeses viskes)
        if (frame.getFirstRoll() == 10) {
            // Kui tegemist on Strike'iga, siis lisame järgmiste viskede boonuspunktid
            return 10 + calculateStrikeBonus(frame.getPlayer(), frame);
        }

        // Kui tegemist on Spindiga (kokku visatakse 10 pinskit)
        if (frame.getFirstRoll() + frame.getSecondRoll() == 10) {
            return frame.getFirstRoll() + frame.getSecondRoll();
        }

        // Kui tegemist on tavalisest freimist, siis summeerime esimese ja teise viske tulemuse
        return frame.getFirstRoll() + frame.getSecondRoll();
    }
}
