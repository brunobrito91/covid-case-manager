package com.example.covidcasemanager.service;

import com.example.covidcasemanager.exception.ResourceNotFoundException;
import com.example.covidcasemanager.model.Person;
import com.example.covidcasemanager.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person read(String cpf) {
        return personRepository.findById(cpf).orElseThrow(() -> new ResourceNotFoundException(cpf));
    }

    public void update(String cpf, Person personUpdated) {
        Person person = read(cpf);

        person.setName(personUpdated.getName());
        person.setEmail(personUpdated.getEmail());
        person.setIsVaccinated(personUpdated.getIsVaccinated());

        save(person);
    }

    public void delete(String cpf) {
        personRepository.deleteById(cpf);
    }

    public List<Person> readAll() {
        return personRepository.findAll();
    }
}
