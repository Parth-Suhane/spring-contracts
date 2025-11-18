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

    @Test
    void duplicateEmail_returns409() throws Exception {
        repo.save(new Person("X", "dup@y.com"));

        String body = "{\"name\":\"Y\",\"email\":\"dup@y.com\"}";

        mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    void get_unknownId_returns404() throws Exception {
        mvc.perform(get("/api/persons/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void post_invalidJson_returns400() throws Exception {
        String body = "{ \"name\": \"test\" ";

        mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_missingFields_returns400() throws Exception {
        String body = "{\"email\":\"x@y.com\"}";

        mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_emptyBody_returns400() throws Exception {
        mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }
}
