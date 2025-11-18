package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
  private final PersonService service;

  public PersonController(PersonService service) { this.service = service; }

  @GetMapping
  public List<Person> all() { return service.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Person> getById(@PathVariable Long id) {
    return service.findById(id)
                  .map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Person> create(@Valid @RequestBody Person person, UriComponentsBuilder uriBuilder) {
    Person created = service.create(person);
    URI location = uriBuilder.path("/api/persons/{id}").buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(location).body(created);
  }
}
