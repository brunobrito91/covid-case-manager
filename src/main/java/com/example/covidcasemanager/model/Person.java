package com.example.covidcasemanager.model;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document
public class Person {

    @Id
    @CPF(message = "cpf field invalid")
    private String cpf;
    @NotBlank(message = "name field is mandatory")
    private String name;
    @NotBlank(message = "email field is mandatory")
    @Email(message = "email field invalid")
    private String email;
    @NotNull(message = "isVaccinated field is mandatory")
    private Boolean isVaccinated;

}
