package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

  @Mock
  PersonRepository repo;

  @InjectMocks
  PersonService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAll_returnsList() {
    List<Person> sample = List.of(new Person(1L,"A","a@x.com"));
    when(repo.findAll()).thenReturn(sample);
    var res = service.findAll();
    assertEquals(1, res.size());
    verify(repo).findAll();
  }

  @Test
  void create_savesNewPerson() {
    Person p = new Person("John","john@example.com");
    when(repo.findByEmail("john@example.com")).thenReturn(Optional.empty());
    when(repo.save(any(Person.class))).thenAnswer(inv -> {
      Person arg = inv.getArgument(0);
      arg.setId(10L);
      return arg;
    });

    Person saved = service.create(p);
    assertNotNull(saved.getId());
    assertEquals("John", saved.getName());
    verify(repo).save(any());
  }

  @Test
  void create_duplicateEmail_throws() {
    Person p = new Person("Jane","jane@x.com");
    when(repo.findByEmail("jane@x.com")).thenReturn(Optional.of(new Person(2L,"Jane","jane@x.com")));
    assertThrows(IllegalArgumentException.class, () -> service.create(p));
    verify(repo, never()).save(any());
  }

    @Test
    void create_duplicateEmail_throwsException() {
        Person existing = new Person(1L, "User", "a@x.com");
        when(repo.findByEmail("a@x.com")).thenReturn(Optional.of(existing));

        Person incoming = new Person("Test", "a@x.com");

        assertThrows(IllegalArgumentException.class,
                () -> service.create(incoming));
    }

    @Test
    void create_nullName_throwsException() {
        Person p = new Person(null, "x@y.com");
        when(repo.findByEmail("x@y.com")).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.create(p));
    }

    @Test
    void create_nullEmail_throwsException() {
        Person p = new Person("John", null);

        assertThrows(Exception.class, () -> service.create(p));
    }

    @Test
    void create_emptyEmail_throwsException() {
        Person p = new Person("John", "");

        assertThrows(Exception.class, () -> service.create(p));
    }

    @Test
    void findById_notFound_returnsEmpty() {
        when(repo.findById(999L)).thenReturn(Optional.empty());

        assertTrue(service.findById(999L).isEmpty());
    }
}
