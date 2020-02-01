package com.test.controller;

import com.test.dto.PersonRequest;
import com.test.dto.PersonResponse;
import com.test.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;

    @PostMapping
    @ResponseBody
    public PersonResponse addPeopleInfo(@RequestBody PersonRequest request) {
        return null;

    }

}
