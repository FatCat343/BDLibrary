package com.bdcourse.library.publication.author;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "author")
public class Author implements Serializable {
    @Id
    @SequenceGenerator(name = "author_generator", sequenceName = "author_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    @Column(name = "author_id")
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
