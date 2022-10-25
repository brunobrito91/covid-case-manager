package com.example.covidcasemanager.service;

import com.example.covidcasemanager.exception.ResourceAlreadyExistsException;
import com.example.covidcasemanager.exception.ResourceNotFoundException;
import com.example.covidcasemanager.model.Person;
import com.example.covidcasemanager.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings
class PersonServiceTest {

    private static final String CPF = "12345678901";
    @Mock
    private PersonRepository mockPersonRepository;

    @InjectMocks
    private PersonService personService;

    private static Person person;

    @BeforeAll
    static void beforeAll() {
        person = new Person();
        person.setCpf(CPF);
        person.setName("");
        person.setEmail("");
        person.setIsVaccinated(Boolean.TRUE);
    }

    @Test
    void givenNonexistentPersonWhenSaveThenOk() {
        when(mockPersonRepository.existsById(person.getCpf())).thenReturn(false);
        when(mockPersonRepository.save(person)).thenReturn(person);

        Person personSaved = personService.save(person);

        assertEquals(person, personSaved);

        verify(mockPersonRepository).existsById(person.getCpf());
        verify(mockPersonRepository).save(person);
    }

    @Test
    void givenExistentPersonWhenSaveThenOk() {
        when(mockPersonRepository.existsById(person.getCpf())).thenReturn(true);

        Executable executable = () -> personService.save(person);

        assertThrows(ResourceAlreadyExistsException.class, executable);

        verify(mockPersonRepository).existsById(person.getCpf());
        verifyNoMoreInteractions(mockPersonRepository);
    }

    @Test
    void givenExistentPersonWhenReadThenOk() {
        when(mockPersonRepository.findById(person.getCpf())).thenReturn(Optional.of(person));

        Person personRead = personService.read(person.getCpf());

        assertEquals(person, personRead);

        verify(mockPersonRepository).findById(person.getCpf());
    }

    @Test
    void givenNonexistentPersonWhenReadThenOk() {
        when(mockPersonRepository.findById(person.getCpf())).thenReturn(Optional.empty());

        Executable executable = () -> personService.read(person.getCpf());

        assertThrows(ResourceNotFoundException.class, executable);

        verify(mockPersonRepository).findById(person.getCpf());
    }

    @Test
    void givenExistentPersonWhenUpdateThenOk() {
        when(mockPersonRepository.findById(person.getCpf())).thenReturn(Optional.of(person));

        personService.update(person.getCpf(), person);

        verify(mockPersonRepository).findById(person.getCpf());
        verify(mockPersonRepository).save(person);
    }

    @Test
    void givenNonexistentPersonWhenUpdateThenOk() {
        when(mockPersonRepository.findById(person.getCpf())).thenReturn(Optional.empty());

        Executable executable = () -> personService.update(person.getCpf(), person);

        assertThrows(ResourceNotFoundException.class, executable);

        verify(mockPersonRepository).findById(person.getCpf());
        verifyNoMoreInteractions(mockPersonRepository);
    }

    @Test
    void givenPersonWhenDeleteThenOk() {
        personService.delete(person.getCpf());

        verify(mockPersonRepository).deleteById(person.getCpf());
    }

    @Test
    void givenNoPersonWhenReadAllThenOk() {
        when(mockPersonRepository.findAll()).thenReturn(List.of());

        List<Person> personsRead = personService.readAll();

        assertEquals(0, personsRead.size());

        verify(mockPersonRepository).findAll();
    }

    @Test
    void givenPeopleWhenReadAllThenOk() {
        List<Person> expectedPeopleRead = List.of(person, person, person);

        when(mockPersonRepository.findAll()).thenReturn(expectedPeopleRead);

        List<Person> peopleRead = personService.readAll();

        assertArrayEquals(expectedPeopleRead.toArray(), peopleRead.toArray());

        verify(mockPersonRepository).findAll();
    }
}