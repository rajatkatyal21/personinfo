package com.test.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonResponse {
    Long id;
    String firstName;
    String lastName;
    Integer age;
    String favouriteColor;
    Set<String> hobbies;
}
