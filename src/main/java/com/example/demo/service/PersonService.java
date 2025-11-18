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
        if (p.getName() == null || p.getName().isBlank())
        throw new IllegalArgumentException("Name is required");

        if (p.getEmail() == null || p.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");

        repo.findByEmail(p.getEmail())
                .ifPresent(existing -> { throw new IllegalArgumentException("Duplicate email"); });

        return repo.save(p);
    }
}
