package com.test.service;

import com.test.dto.PersonRequest;
import com.test.dto.PersonResponse;

public interface PersonService {

    PersonResponse addPersonInfo(PersonRequest personRequestDto);

    PersonResponse getPersonInfo(Long id);
}
