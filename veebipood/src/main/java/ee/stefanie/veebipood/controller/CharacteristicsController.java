package ee.stefanie.veebipood.controller;

import ee.stefanie.veebipood.entity.Characteristic;
import ee.stefanie.veebipood.repository.CharacteristicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("http://localhost:4200")
@RestController
public class CharacteristicsController {

    @Autowired
    CharacteristicsRepository characteristicsRepository;

    @GetMapping("characteristics")
    public List<Characteristic> getCharacteristics() {
        return characteristicsRepository.findAll();
    }

    @GetMapping("characteristics/{id}")
    public Characteristic getCharacteristic(@PathVariable Long id) {
        return characteristicsRepository.findById(id).orElse(null);
    }

    @PostMapping("characteristics")
    public List<Characteristic> addCharacteristic(@RequestBody Characteristic characteristic) {
        characteristicsRepository.save(characteristic);
        return characteristicsRepository.findAll();
    }

    @DeleteMapping("characteristics/{id}")
    public List<Characteristic> deleteCharacteristic(@PathVariable Long id) {
        characteristicsRepository.deleteById(id);
        return characteristicsRepository.findAll();
    }

    @PutMapping("characteristics/{id}")
    public List<Characteristic> updateCharacteristic(@PathVariable Long id, @RequestBody Characteristic characteristic) {
        if (characteristicsRepository.findById(id).isPresent()) {
            characteristicsRepository.save(characteristic);
        }
        return characteristicsRepository.findAll();
    }
}
