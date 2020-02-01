package com.test.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PersonRequest {

    private String firstName;
    private String lastName;
    private int age;
    private String favouriteColor;
    private Set<String> hobbies;
}
