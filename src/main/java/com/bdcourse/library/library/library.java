package com.bdcourse.library.library;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "library")
public class library implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_library")
    @SequenceGenerator(name = "seq_gen_library", sequenceName = "seq_library")
    @Column(name = "library_id")
    private Integer library_id;

    @Column(name = "address")
    private String address;

    public Integer getLibrary_id() {
        return library_id;
    }

    public void setLibrary_id(Integer library_id) {
        this.library_id = library_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
