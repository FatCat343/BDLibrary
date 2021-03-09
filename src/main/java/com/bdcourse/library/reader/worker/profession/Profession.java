package com.bdcourse.library.reader.worker.profession;

import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "profession")
public class Profession implements Serializable {
    @Id
    @SequenceGenerator(name = "profession_generator", sequenceName = "profession_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profession_generator")
    @Column(name = "profession_id")
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
