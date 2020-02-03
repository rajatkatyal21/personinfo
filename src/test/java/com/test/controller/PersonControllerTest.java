package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dto.PersonRequest;
import com.test.dto.PersonResponse;
import com.test.service.PersonService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonService personService;


    @Test
    void addPeopleInfo() throws Exception {
        PersonRequest request = PersonRequest.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .hobbies(new HashSet<>(Arrays.asList("cricket", "football")))
                .build();
        PersonResponse response = PersonResponse.builder()
                .id(1L)
                .build();
        Mockito.when(personService.addPersonInfo(request)).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{" +
                        "    \"id\": 1    \n" +
                        "}"));
    }

    @Test
    void GetPeopleInfo() throws Exception {
        PersonResponse response = PersonResponse.builder()
                .age(20)
                .firstName("Rajat")
                .lastName("katyal")
                .favouriteColor("black")
                .hobbies(new HashSet<>(Arrays.asList("cricket", "football")))
                .build();

        Mockito.when(personService.getPersonInfo(1L)).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();


        this.mockMvc.perform(MockMvcRequestBuilders.get("/person/1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void PersonDelete() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
        Mockito.verify(personService, Mockito.times(1)).delete(1L);
    }

    @Test
    void updatePerson() throws Exception {
        PersonRequest request = PersonRequest.builder()
                .age(20)
                .hobbies(new HashSet<>(Arrays.asList("cricket", "football")))
                .build();
        ObjectMapper objectMapper = new ObjectMapper();



        this.mockMvc.perform(MockMvcRequestBuilders.patch("/person/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(personService, Mockito.times(1)).updatePerson(1L, request);
    }


}