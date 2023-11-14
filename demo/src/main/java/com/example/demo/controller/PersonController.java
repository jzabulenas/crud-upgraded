package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.entity.Group;
import com.example.demo.repository.GroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PersonController {

    private PersonRepository personRepository;
    private GroupRepository groupRepository;

    public PersonController(PersonRepository personRepository, GroupRepository groupRepository) {
        this.personRepository = personRepository;
        this.groupRepository = groupRepository;
    }

    @GetMapping("/people")
    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    @GetMapping("/people/{id}")
    public Person getPerson(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    @PostMapping("/people")
    public void addPerson(@RequestBody Person person) {
        List<Group> groups = new ArrayList<>();
        for (Group group : person.getGroups()) {
            Group existingGroup = groupRepository.findByName(group.getName());

            groups.add(existingGroup);
        }

        person.setGroups(groups);
        personRepository.save(person);
    }

    @PutMapping("/people/{id}")
    public Person updatePerson(@RequestBody Person updatedPerson, @PathVariable long id) {
        Optional<Person> personFromDb = personRepository.findById(id);

        if (personFromDb.isPresent()) {
            Person person = personFromDb.get();

            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            person.setBirthday(updatedPerson.getBirthday());
            person.setAddress(updatedPerson.getAddress());
            person.setCity(updatedPerson.getCity());
            person.setState(updatedPerson.getState());
            person.setZipCode(updatedPerson.getZipCode());

            return personRepository.save(person);
        }

        return personRepository.save(updatedPerson);
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable long id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            personRepository.deleteById(id);
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
