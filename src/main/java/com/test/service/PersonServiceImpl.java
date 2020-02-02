package com.test.service;

import com.test.dto.PersonRequest;
import com.test.dto.PersonResponse;
import com.test.entity.Person;
import com.test.entity.PersonHobby;
import com.test.exception.NotFoundException;
import com.test.repository.PersonHobbyRepository;
import com.test.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private PersonHobbyRepository personHobbyRepository;

    @Override
    public PersonResponse addPersonInfo(PersonRequest personRequest) {
        Person person = Person.builder()
                .age(personRequest.getAge())
                .favouriteColor(personRequest.getFavouriteColor())
                .firstName(personRequest.getFirstName())
                .lastName(personRequest.getLastName())
                .build();

        Person savedPerson = personRepository.save(person);

        Optional.ofNullable(personRequest.getHobbies())
                .orElseGet(HashSet::new)
                .forEach(hobby -> {
                    PersonHobby personHobby = new PersonHobby();
                    personHobby.setHobby(hobby);
                    personHobby.setPersonId(savedPerson.getId());
                    personHobbyRepository.save(personHobby);
                });

        return PersonResponse.builder()
                .id(savedPerson.getId())
                .build();

    }

    @Override
    public PersonResponse getPersonInfo(Long id) {
        Person person =  personRepository.findById(id).orElseThrow(NotFoundException::new);
        Set<String> personHobbies =  personHobbyRepository.findPersonHobbiesByPersonId(id).stream().map(PersonHobby::getHobby).collect(Collectors.toSet());
        return PersonResponse.builder()
                .id(person.getId())
                .age(person.getAge())
                .favouriteColor(person.getFavouriteColor())
                .hobbies(personHobbies)
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .build();

    }
}