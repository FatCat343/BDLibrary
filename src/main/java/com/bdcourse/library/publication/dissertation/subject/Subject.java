package com.bdcourse.library.publication.dissertation.subject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subject")
public class Subject implements Serializable {
    @Id
    @SequenceGenerator(name = "subject_generator", sequenceName = "subject_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_generator")
    @Column(name = "subject_id")
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
