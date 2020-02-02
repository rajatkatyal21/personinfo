package com.test.repository;

import com.test.entity.PersonHobby;
import com.test.entity.PersonHobbyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PersonHobbyRepository extends JpaRepository<PersonHobby, PersonHobbyId> {

    Set<PersonHobby> findPersonHobbiesByPersonId(long id);
}