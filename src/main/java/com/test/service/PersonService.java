package com.test.service;

import com.test.dto.PersonRequest;
import com.test.entity.Person;

public interface PersonService {

    Person addPersonInfo(PersonRequest personRequestDto);

}
