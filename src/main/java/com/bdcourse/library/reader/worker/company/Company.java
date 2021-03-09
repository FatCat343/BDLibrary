package com.bdcourse.library.reader.worker.company;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "company")
public class Company implements Serializable {
    @Id
    @SequenceGenerator(name = "company_generator", sequenceName = "company_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
    @Column(name = "company_id")
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
