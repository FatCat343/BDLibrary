package com.bdcourse.library.reader;

import com.bdcourse.library.library.Library;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reader")
public class Reader implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "reader_id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "firstname", updatable = false, nullable = false)
    private String firstName;

    @Column(name = "lastname", updatable = false, nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_library_id")
    private Library library;

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

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
