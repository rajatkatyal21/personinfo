package com.test.service;

import com.test.dto.PersonRequest;
import com.test.entity.Person;
import com.test.entity.PersonHobby;
import com.test.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceImplTest {


    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl personService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Add person test")
    void addPersonInfo() {
        PersonHobby hobby1 = PersonHobby.builder().hobby("cricket").build();
        PersonHobby hobby2 = PersonHobby.builder().hobby("football").build();
        HashSet<PersonHobby> hobbies = new HashSet<>(Arrays.asList(hobby1, hobby2));

        Person person = Person.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .hobbies(hobbies)
                .build();

        Mockito.when(personRepository.save(person)).thenReturn(Person.builder().id(1L).build());

        PersonRequest request = PersonRequest.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .hobbies(new HashSet<>(Arrays.asList("cricket", "football")))
                .build();

        Person response = personService.addPersonInfo(request);

        assertEquals(person.getId(), response.getId());

    }
}