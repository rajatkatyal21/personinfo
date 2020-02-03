package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dto.PersonRequest;
import com.test.dto.PersonResponse;
import com.test.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser("admin")
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
    @WithMockUser("admin")
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
    @WithMockUser("admin")
    void PersonDelete() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
        Mockito.verify(personService, Mockito.times(1)).delete(1L);
    }

    @Test
    @WithMockUser("admin")
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