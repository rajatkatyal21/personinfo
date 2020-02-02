package com.test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "hobby")
@Table(name = "person_hobby")
@NoArgsConstructor
@Data
@IdClass(PersonHobbyId.class)
public class PersonHobby {

    @Id
    private Long personId;
    @Id
    private String hobby;
}