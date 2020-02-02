package com.test.service;

import com.test.dto.PersonRequest;
import com.test.dto.PersonResponse;
import com.test.entity.Person;
import com.test.entity.PersonHobby;
import com.test.entity.PersonHobbyId;
import com.test.exception.NotFoundException;
import com.test.repository.PersonHobbyRepository;
import com.test.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceImplTest {


    @Mock
    PersonRepository personRepository;

    @Mock
    PersonHobbyRepository personHobbyRepository;

    @InjectMocks
    PersonServiceImpl personService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Add person test")
    void addPersonInfo_with_no_hobbies() {
        Person person = Person.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .build();

        Mockito.when(personRepository.save(person)).thenReturn(Person.builder().id(1L).build());

        PersonRequest request = PersonRequest.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .build();

        PersonResponse response = personService.addPersonInfo(request);

        Assertions.assertEquals(1, response.getId());

    }

    @Test
    @DisplayName("Add person test")
    void addPersonInfo() {
        Person person = Person.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .build();

        PersonHobby hobby1 = new PersonHobby();
        hobby1.setPersonId(1L);
        hobby1.setHobby("cricket");

        PersonHobby hobby2 = new PersonHobby();
        hobby2.setPersonId(1L);
        hobby2.setHobby("football");


        Mockito.when(personRepository.save(person)).thenReturn(Person.builder().id(1L).build());

        PersonRequest request = PersonRequest.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .hobbies(new HashSet<>(Arrays.asList("cricket", "football")))
                .build();

        PersonResponse response = personService.addPersonInfo(request);

        Mockito.verify(personHobbyRepository).save(hobby1);
        Mockito.verify(personHobbyRepository).save(hobby2);

        Assertions.assertEquals(1, response.getId());

    }

    @Test
    @DisplayName("get Person method when the entity is not found")
    void GetPersonInfo_Not_found() {
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(personHobbyRepository.findPersonHobbiesByPersonId(1L)).thenReturn(new HashSet<>());

        Assertions.assertThrows(NotFoundException.class, () ->
                personService.getPersonInfo(1L)
        );
    }

    @Test
    @DisplayName("get Person info")
    void GetPersonInfo() {
        Person person = Person.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .build();

        PersonHobby hobby1 = new PersonHobby();
        hobby1.setPersonId(1L);
        hobby1.setHobby("cricket");

        PersonHobby hobby2 = new PersonHobby();
        hobby2.setPersonId(1L);
        hobby2.setHobby("football");

        HashSet<PersonHobby> set = new HashSet<>(Arrays.asList(hobby1, hobby2));

        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        Mockito.when(personHobbyRepository.findPersonHobbiesByPersonId(1L)).thenReturn(set);

        PersonResponse expected = PersonResponse.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .hobbies(new HashSet<>(Arrays.asList("cricket", "football")))
                .build();

        PersonResponse actual = personService.getPersonInfo(1L);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    @DisplayName("get Person info with no hobbies")
    void GetPersonInfo_with_No_Hobbies() {
        Person person = Person.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .build();

        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        Mockito.when(personHobbyRepository.findPersonHobbiesByPersonId(1L)).thenReturn(new HashSet<>());

        PersonResponse expected = PersonResponse.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .hobbies(new HashSet<>())
                .build();

        PersonResponse actual = personService.getPersonInfo(1L);

        Assertions.assertEquals(expected, actual);

    }


    @Test
    @DisplayName("delete by Id")
    void delete() {

        Set<PersonHobby> set = new HashSet<>();
        PersonHobby personHobby = new PersonHobby();
        personHobby.setHobby("cricket");
        personHobby.setPersonId(1L);
        set.add(personHobby);
        Mockito.when(personHobbyRepository.findPersonHobbiesByPersonId(1L)).thenReturn(set);
        personService.delete(1L);

        Mockito.verify(personHobbyRepository).deleteById(new PersonHobbyId(1l, "cricket"));
        Mockito.verify(personRepository).deleteById(1L);

    }

    @Test
    @DisplayName("Update the Person")
    void update_hobbies() {

        PersonRequest personRequest = PersonRequest.builder()
                .hobbies(new HashSet<>(Arrays.asList("rugby")))
                .build();

        Person person = Person.builder()
                .age(10)
                .favouriteColor("white")
                .lastName("katyal")
                .firstName("Rajat")
                .build();

        PersonHobby hobby1 = new PersonHobby();
        hobby1.setPersonId(1L);
        hobby1.setHobby("cricket");

        PersonHobby hobby2 = new PersonHobby();
        hobby2.setPersonId(1L);
        hobby2.setHobby("football");

        Set<PersonHobby> set = new HashSet<>(Arrays.asList(hobby1, hobby2));


        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        Mockito.when(personHobbyRepository.findPersonHobbiesByPersonId(1L)).thenReturn(set);

        personService.updatePerson(1L, personRequest);

        Mockito.verify(personHobbyRepository, Mockito.times(1)).deleteById(new PersonHobbyId(1L, "cricket"));
        Mockito.verify(personHobbyRepository, Mockito.times(1)).deleteById(new PersonHobbyId(1L, "football"));
        Mockito.verify(personRepository, Mockito.times(0)).save(new Person());
    }


}