package com.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PersonRequest {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private Integer age;
    @JsonProperty("favourite_color")
    private String favouriteColor;
    @JsonProperty("hobbies")
    private Set<String> hobbies;
}
