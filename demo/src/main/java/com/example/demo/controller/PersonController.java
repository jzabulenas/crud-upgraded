package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/api/people")
	public List<Person> getPeople() {
		return personRepository.findAll();
	}
	
	@GetMapping("/api/people/{id}")
	public Person getPerson(@PathVariable long id) {
		return personRepository.findById(id).get();
	}
}
