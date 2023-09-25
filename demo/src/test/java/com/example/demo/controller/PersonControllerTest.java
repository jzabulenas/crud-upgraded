package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PersonRepository personRepository;

	@Test
	public void testGetPeople() throws Exception {
		// Create some test data
		List<Person> people = new ArrayList<>();
		people.add(new Person("Michael", "Wood", "January 28, 1973", "3664 Vineyard Drive",
				"Cleveland", "Ohio", 44115));
		people.add(new Person("Rodney", "Bonilla", "November 9, 1945", "1419 Cambridge Court",
				"Phoenix", "Arizona", 85034));

		// Mock the behavior of the personRepository
		when(personRepository.findAll())
				.thenReturn(people);

		// Perform the GET request and validate the response
		mockMvc.perform(get("/people")).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].firstName").value("Michael"))
				.andExpect(jsonPath("$[0].lastName").value("Wood"))
				.andExpect(jsonPath("$[1].firstName").value("Rodney"))
				.andExpect(jsonPath("$[1].lastName").value("Bonilla"));
	}

	@Test
	public void testGetPerson() throws Exception {
		Person person = new Person("Michael", "Wood", "January 28, 1973", "3664 Vineyard Drive",
				"Cleveland", "Ohio", 44115);

		when(personRepository.findById(0L))
				.thenReturn(Optional.of(person));

		mockMvc.perform(get("/people/0")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(0))
				.andExpect(jsonPath("$.firstName").value("Michael"))
				.andExpect(jsonPath("$.lastName").value("Wood"));
	}

	@Test
	public void testAddPerson() throws Exception {
		// Create a sample person
		Person person = new Person("John", "Doe", "January 1, 1990", "123 Main St", "City", "State",
				12345);

		// Set up the behavior of personRepository.save
		when(personRepository.save(any()))
				.thenReturn(person);

		// Create a POST request with JSON content
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/people")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"John\",\"lastName\":\"Doe\","
						+ "\"birthdate\":\"January 1, 1990\",\"address\":\"123 Main St\","
						+ "\"city\":\"City\",\"state\":\"State\",\"zip\":12345,"
						+ "\"phone\":\"123-456-7890\"}");

		// Perform the POST request and validate the response
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Verify that personRepository.save was called with the correct data
		verify(personRepository, times(1)).save(any());
	}

	@Test
	public void testUpdatePerson() throws Exception {
		// Create a sample person
		Person person = new Person("John", "Doe", "January 1, 1990", "123 Main St", "City",
				"State", 12345);

		// Set up the behavior of personRepository.findById
		when(personRepository.findById(0L))
				.thenReturn(Optional.of(person));

		// Set up the behavior of personRepository.save
		when(personRepository.save(any()))
				.thenReturn(person);

		// Create a PUT request with JSON content
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/people/0")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"Updatedd\",\"lastName\":\"Personn\","
						+ "\"birthday\":\"February 2, 2000\",\"address\":\"456 New St\","
						+ "\"city\":\"New City\",\"state\":\"New State\",\"zipCode\":54321,"
						+ "\"phoneNumber\":\"987-654-3210\"}");

		// Perform the PUT request and validate the response
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Updatedd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Personn"));

		// Verify that personRepository.findById was called with the correct ID
		verify(personRepository, times(1)).findById(0L);

		// Verify that personRepository.save was called with the person
		verify(personRepository, times(1)).save(person);
	}

	@Test
	public void testDeletePerson() throws Exception {
		// Create a sample person
		Person person = new Person("John", "Doe", "January 1, 1990", "123 Main St", "City", "State",
				12345);

		when(personRepository.findById(1L))
				.thenReturn(Optional.of(person));

		// Define a person ID for testing
		long personId = 1L;

		// Perform the DELETE request
		mockMvc.perform(MockMvcRequestBuilders.delete("/people/{id}", personId))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Verify that personRepository.deleteById was called with the correct ID
		verify(personRepository, times(1)).deleteById(personId);
	}
}
