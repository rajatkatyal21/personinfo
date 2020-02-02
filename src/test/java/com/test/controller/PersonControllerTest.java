package com.test.controller;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class PersonControllerTest {

    @Test
    void addPeopleInfo() {

        String payload =  "{\n" +
                "\t\"first_name\": \"rajat\",\n" +
                "\t\"last_name\": \"katyal\",\n" +
                "\t\"age\": 30,\n" +
                "\t\"favourite_color\": \"blue\",\n" +
                "\t\"hobby\": [\"cricket\", \"football\"]\n" +
                "}";

        given()
                .body(payload)
                .post("/person").then().statusCode(200);
    }
}