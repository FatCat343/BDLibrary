package com.bdcourse.library.staff;

import com.bdcourse.library.storage.Storage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "staff")
public class Staff implements Serializable {
    @Id
    @SequenceGenerator(name = "staff_generator", sequenceName = "staff_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staff_generator")
    @Column(name = "staff_id")
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

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

    public Storage getStorage() {
        return storage;
    }

    public String getStorageName() {
        return storage.toString();
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
