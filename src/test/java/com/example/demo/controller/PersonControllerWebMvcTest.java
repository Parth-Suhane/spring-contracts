package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerWebMvcTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  PersonService service;

  @Test
  void getAll_returnsJsonArray() throws Exception {
    Mockito.when(service.findAll()).thenReturn(List.of(new Person(1L,"A","a@x.com")));
    mvc.perform(get("/api/persons"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$", hasSize(1)))
       .andExpect(jsonPath("$[0].name", is("A")));
  }

  @Test
  void getById_found() throws Exception {
    Mockito.when(service.findById(1L)).thenReturn(Optional.of(new Person(1L,"A","a@x.com")));
    mvc.perform(get("/api/persons/1"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.email", is("a@x.com")));
  }

  @Test
  void create_returnsCreated() throws Exception {
    Person out = new Person(5L,"Bob","bob@x.com");
    Mockito.when(service.create(any())).thenReturn(out);
    String body = "{\"name\":\"Bob\",\"email\":\"bob@x.com\"}";
    mvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON).content(body))
       .andExpect(status().isCreated())
       .andExpect(header().string("Location", containsString("/api/persons/5")))
       .andExpect(jsonPath("$.id", is(5)));
  }

    @Test
    void getById_notFound_returns404() throws Exception {
        Mockito.when(service.findById(100L))
                .thenReturn(Optional.empty());

        mvc.perform(get("/api/persons/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    void post_missingName_returns400() throws Exception {
        String body = "{\"email\":\"x@y.com\"}";

        mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_malformedJson_returns400() throws Exception {
        String body = "{ \"name\": \"Joe\", ";

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

    @Test
    void post_duplicateEmail_returns400() throws Exception {
        Mockito.when(service.create(any()))
                .thenThrow(new IllegalArgumentException("duplicate email"));

        String body = "{\"name\":\"Joe\",\"email\":\"x@y.com\"}";

        mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_wrongContentType_returns415() throws Exception {
        mvc.perform(post("/api/persons")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("name=joe"))
                .andExpect(status().isUnsupportedMediaType());
    }
}
