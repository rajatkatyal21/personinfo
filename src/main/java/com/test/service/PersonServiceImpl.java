package com.test.service;

import com.test.dto.PersonRequest;
import com.test.dto.PersonResponse;
import com.test.entity.Person;
import com.test.entity.PersonHobby;
import com.test.entity.PersonHobbyId;
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
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        Set<String> personHobbies = personHobbyRepository.findPersonHobbiesByPersonId(id).stream().map(PersonHobby::getHobby).collect(Collectors.toSet());
        return PersonResponse.builder()
                .id(person.getId())
                .age(person.getAge())
                .favouriteColor(person.getFavouriteColor())
                .hobbies(personHobbies)
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .build();

    }

    @Override
    public void delete(Long id) {
        Set<PersonHobby> personHobbiesByPersonId = personHobbyRepository.findPersonHobbiesByPersonId(id);
        personHobbiesByPersonId.forEach(personHobby -> {
            PersonHobbyId personHobbyId = new PersonHobbyId(id, personHobby.getHobby());
            personHobbyRepository.deleteById(personHobbyId);
        });
        personRepository.deleteById(id);
    }

    @Override
    public void updatePerson(Long id, PersonRequest personRequestDto) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        Set<PersonHobby> personHobbiesByPersonId = personHobbyRepository.findPersonHobbiesByPersonId(id);

        Integer age = Optional.ofNullable(personRequestDto.getAge()).orElse(person.getAge());
        String favouriteColor = personRequestDto.getFavouriteColor() != null ? personRequestDto.getFavouriteColor() : person.getFavouriteColor();
        String lastName = personRequestDto.getLastName() != null ? personRequestDto.getLastName() : person.getLastName();
        String firstName = personRequestDto.getFirstName() != null ? personRequestDto.getFirstName() : person.getFirstName();

        Person personToSave = Person.builder()
                .id(id)
                .lastName(lastName)
                .firstName(firstName)
                .favouriteColor(favouriteColor)
                .age(age)
                .build();

        personRepository.save(personToSave);

        if (personHobbiesByPersonId != null || personHobbiesByPersonId.size() > 0) {
            personHobbiesByPersonId
                    .forEach(personHobby -> {
                        PersonHobbyId personHobbyId = new PersonHobbyId(personHobby.getPersonId(), personHobby.getHobby());
                        personHobbyRepository.deleteById(personHobbyId);
                    });

            personRequestDto.getHobbies()
                    .forEach(s -> {
                        PersonHobby personHobby = new PersonHobby();
                        personHobby.setPersonId(id);
                        personHobby.setHobby(s);
                        personHobbyRepository.save(personHobby);
                    });
        }
    }
}