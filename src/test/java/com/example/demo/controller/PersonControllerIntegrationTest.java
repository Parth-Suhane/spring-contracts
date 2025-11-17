package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PersonControllerIntegrationTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  PersonRepository repo;

  @Test
  void create_and_get_work_end_to_end() throws Exception {
    String body = "{\"name\":\"Integration\",\"email\":\"int@x.com\"}";
    mvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON).content(body))
       .andExpect(status().isCreated())
       .andExpect(jsonPath("$.id", notNullValue()));

    mvc.perform(get("/api/persons"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$", not(empty())));
  }
}
