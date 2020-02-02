DROP TABLE IF EXISTS person_hobby;
DROP TABLE IF EXISTS person;

CREATE TABLE person (
  id INT primary key,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  age INT,
  favourite_color VARCHAR(250)
);


CREATE TABLE person_hobby (
  person_id INT,
  hobby VARCHAR(250),
  PRIMARY KEY (person_id, hobby)
);