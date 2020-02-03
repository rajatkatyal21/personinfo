package com.test.service;

import com.test.dto.PersonRequest;
import com.test.dto.PersonResponse;
import com.test.exception.NotFoundException;

public interface PersonService {

    PersonResponse addPersonInfo(PersonRequest personRequestDto);

    PersonResponse getPersonInfo(Long id) throws NotFoundException;

    void delete(Long id);

    void updatePerson(Long id, PersonRequest personRequestDto) throws NotFoundException;

}
