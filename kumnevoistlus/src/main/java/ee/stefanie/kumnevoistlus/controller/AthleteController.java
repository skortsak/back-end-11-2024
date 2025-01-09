package ee.stefanie.kumnevoistlus.controller;

import ee.stefanie.kumnevoistlus.entity.Athlete;
import ee.stefanie.kumnevoistlus.entity.Event;
import ee.stefanie.kumnevoistlus.repository.AthleteRepository;


import ee.stefanie.kumnevoistlus.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AthleteController {
    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("athletes")
    public List<Athlete> getAthletes() {
        return athleteRepository.findAll();
    }

    @GetMapping("add-athlete")
    public List<Athlete> addAthlete(@RequestParam String name, @RequestParam String country, @RequestParam Integer age) {
        athleteRepository.save(new Athlete(name, country, age));
        return athleteRepository.findAll();
    }

    // Add result for an athlete in a specific event (e.g., "100m", "Long Jump")
    @PostMapping("add-athlete-result")
    public Athlete addResult(@RequestParam Long athleteId, @RequestParam String eventName, @RequestParam Integer result) {
        Optional<Athlete> athleteOpt = athleteRepository.findById(athleteId);
        if (athleteOpt.isPresent()) {
            Athlete athlete = athleteOpt.get();
            
            Integer points = calculatePoints(eventName, result);

            Event event = new Event(eventName, result, points);
            event.setAthlete(athlete);

            eventRepository.save(event);

            return athlete;
        } else {
            System.out.println("Athlete with ID " + athleteId + " not found.");
            throw new RuntimeException("Athlete not found");
        }
    }

    // Get the total score for an athlete
    @GetMapping("athlete-total-score")
    public Integer getTotalScore(@RequestParam Long athleteId) {
        Optional<Athlete> athleteOpt = athleteRepository.findById(athleteId);
        if (athleteOpt.isPresent()) {
            Athlete athlete = athleteOpt.get();
            return athlete.getEvents().stream().mapToInt(Event::getScore).sum();
        } else {
            throw new RuntimeException("Athlete not found");
        }
    }

    // Get all athletes with their total scores
    @GetMapping("athletes-scores")
    public List<String> getAllAthletesWithScores() {
        List<Athlete> athletes = athleteRepository.findAll();
        return athletes.stream()
                .map(athlete -> athlete.getName() + " - " + getTotalScore(athlete.getId()) + " points")
                .toList();
    }

    private Integer calculatePoints(String eventName, Integer result) {

        return switch (eventName) {
            case "100m" -> calculate100mPoints(result);
            case "Long Jump" -> calculateLongJumpPoints(result);
            case "Shot Put" -> calculateShotPutPoints(result);
            case "High Jump" -> calculateHighJumpPoints(result);
            case "400m" -> calculate400mPoints(result);
            case "110m Hurdles" -> calculate110mHurdlesPoints(result);
            case "Discus Throw" -> calculateDiscusThrowPoints(result);
            case "Pole Vault" -> calculatePoleVaultPoints(result);
            case "Javelin Throw" -> calculateJavelinThrowPoints(result);
            case "1500m" -> calculate1500mPoints(result);
            default -> throw new IllegalArgumentException("Unknown event: " + eventName);
        };
    }

    private Integer calculate100mPoints(Integer result) {
        return (int) (25 - result * 0.1); // result is in seconds
    }

    private Integer calculateLongJumpPoints(Integer result) {
        return result * 10; // result is in meters
    }

    private Integer calculateShotPutPoints(Integer result) {
        return (int) (result * 5); // result is in meters
    }

    private Integer calculateHighJumpPoints(Integer result) {
        return result * 100; // result is in meters
    }

    private Integer calculate400mPoints(Integer result) {
        return (int) (300 - result * 0.5); // result is in seconds
    }

    private Integer calculate110mHurdlesPoints(Integer result) {
        return (int) (200 - result); // result is in seconds
    }

    private Integer calculateDiscusThrowPoints(Integer result) {
        return (int) (result * 7); // result is in meters
    }

    private Integer calculatePoleVaultPoints(Integer result) {
        return result * 150; // result is in meters
    }


    private Integer calculateJavelinThrowPoints(Integer result) {
        return result * 8; // result is in meters
    }

    private Integer calculate1500mPoints(Integer result) {
        return (int) (1000 - result * 0.25); // result is in seconds
    }
}
