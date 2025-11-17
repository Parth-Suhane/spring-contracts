package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public List<Person> findAll() {
        return repo.findAll();
    }

    public Optional<Person> findById(Long id) {
        return repo.findById(id);
    }

    public Person create(Person p) {
        repo.findByEmail(p.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Email already exists");
        });
        return repo.save(p);
    }
}
