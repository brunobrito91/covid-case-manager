package com.example.covidcasemanager.controller;

import com.example.covidcasemanager.model.Person;
import com.example.covidcasemanager.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody final Person person) {
        Person personCreated = personService.save(person);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(personCreated.getCpf())
                        .toUri();
        return ResponseEntity.created(uri).body(personCreated);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Person> read(@PathVariable("cpf") final String cpf) {
        return ResponseEntity.ok(personService.read(cpf));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Void> update(@PathVariable("cpf") final String cpf,
                                       @Valid @RequestBody final Person person) {
        personService.update(cpf, person);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable("cpf") final String cpf) {
        personService.delete(cpf);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<Person>> readAll() {
        return ResponseEntity.ok(personService.readAll());
    }
}
