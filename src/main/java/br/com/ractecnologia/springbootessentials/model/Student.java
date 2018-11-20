package br.com.ractecnologia.springbootessentials.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

@Entity
public class Student extends AbstractEntity {

    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
