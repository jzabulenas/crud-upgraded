package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
        		"Cleveland", "Ohio", 44115, "440-457-6732"));
        people.add(new Person("Rodney", "Bonilla", "November 9, 1945", "1419 Cambridge Court",
        		"Phoenix", "Arizona", 85034, "480-200-1008"));

        // Mock the behavior of the personRepository
        when(personRepository.findAll()).thenReturn(people);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/api/people"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Assuming response is a JSON array
                .andExpect(jsonPath("$[0].firstName").value("Michael"))
                .andExpect(jsonPath("$[0].lastName").value("Wood"))
                .andExpect(jsonPath("$[1].firstName").value("Rodney"))
                .andExpect(jsonPath("$[1].lastName").value("Bonilla"));
    }

}
