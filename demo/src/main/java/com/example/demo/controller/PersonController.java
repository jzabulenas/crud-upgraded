package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

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

	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
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
			person.setPhoneNumber(updatedPerson.getPhoneNumber());

			return personRepository.save(person);
		}

		return personRepository.save(updatedPerson);
	}

	@DeleteMapping("/people/{id}")
	public void deletePerson(@PathVariable long id) {
		personRepository.deleteById(id);
	}
}
