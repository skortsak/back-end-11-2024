package ee.stefanie.veebipood.controller;

import ee.stefanie.veebipood.dto.PersonDTO;
import ee.stefanie.veebipood.entity.Person;
import ee.stefanie.veebipood.models.EmailPassword;
import ee.stefanie.veebipood.models.Token;
import ee.stefanie.veebipood.repository.PersonRepository;
import ee.stefanie.veebipood.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2 // --> võimaldab logida. vs sout: annab kellaaja+kuupäeva, info/error/debug, faili kus logis, logifaili
//@CrossOrigin("http://localhost:4200")
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ModelMapper modelMapper; // selle AutoWire-damiseks pidime @Beani sees tegema = new ModelMapper()

    @Autowired
    AuthService authService;

    @PostMapping("login")
    public ResponseEntity<Token> login(@RequestBody EmailPassword emailPassword) {
        // 1. otsime isiku üles e-maili abil
        Person person = personRepository.findByEmail(emailPassword.getEmail());
        if (person == null) {
            throw new RuntimeException("E-mail on vale!");
        }
        // 2. võrdleme isiku parooli sisestatud parooliga
        if (person.getPassword().equals(emailPassword.getPassword())) {
            // 3. tagastame Tokeni kui õnnestus, veateate kui ei õnnestunud
            Token token = authService.getToken(person);
            return ResponseEntity.ok(token);
        }
        // 4. Tagastame veateate  kui ei õnnestunud
        throw new RuntimeException("Parool on vale!");
    }

    @PostMapping("signup")
    public ResponseEntity<List<Person>> signup(@RequestBody Person person) {
        if (person.getEmail() == null) {
            throw new RuntimeException("Isikut registreerides on e-mail puudu!");
        }
        if (person.getPassword() == null) {
            throw new RuntimeException("Isikut registreerides on parool puudu!");
        }
        if (person.getFirstName() == null) {
            throw new RuntimeException("Isikut registreerides on eesnimi puudu!");
        }
        if (person.getLastName() == null) {
            throw new RuntimeException("Isikut registreerides on perenimi puudu!");
        }
        person.setAdmin(false);
        personRepository.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.findAll()); // ResponseEntity --> võimaldab seadistada Headereid ja staatuskoodi
        // ehk rohkem kontrolli enda käes
    }

//    @GetMapping("persons")
//    public List<PersonDTO> getPersons() {
//        List<Person> persons = personRepository.findAll();
//        List<PersonDTO> personDTOList = new ArrayList<>();
//        for (Person p : persons) {
//            PersonDTO person = new PersonDTO();
//            person.setEmail(p.getEmail());
//            person.setFirstName(p.getFirstName());
//            person.setLastName(p.getLastName());
//            personDTOList.add(person);
//        }
//        return personDTOList;
//    }

    @GetMapping("persons")
    public ResponseEntity<List<PersonDTO>> getPersons() {
        log.info("User performed receiving persons API endpoint");
        List<Person> persons = personRepository.findAll();
//         ModelMapper modelMapper = new ModelMapper(); --> selle asemel @AutoWired
        log.info(modelMapper);
        return ResponseEntity.ok().body(List.of(modelMapper.map(persons, PersonDTO[].class)));
    }

    @PatchMapping("make-admin")
    public ResponseEntity<Person> makePersonAdmin(@RequestBody Long personId) {
        Person dbPerson = personRepository.findById(personId).orElseThrow();
        dbPerson.setAdmin(true);
        return ResponseEntity.ok().body(personRepository.save(dbPerson));
    }

    @GetMapping("profile")
    public ResponseEntity<Person> getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Person person = personRepository.findByEmail(email);
        return ResponseEntity.ok().body(person); // võiks teha DTO, kus tagastame vähem andmeid kui tegelikkuses front-endil vaja
    }
}
