package com.bdcourse.library.library;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "library")
public class Library implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_library")
    @SequenceGenerator(name = "seq_gen_library", sequenceName = "seq_library")
    @Column(name = "library_id")
    private Integer id;

    @Column(name = "address", nullable = false, updatable = false)
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
