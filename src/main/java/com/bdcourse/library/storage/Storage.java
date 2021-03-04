package com.bdcourse.library.storage;

import com.bdcourse.library.library.Library;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "storage")
public class Storage implements Serializable {
    @Id
    @SequenceGenerator(name = "storage_generator", sequenceName = "storage_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_generator")
    @Column(name = "storage_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @Column(name = "room_number")
    private Integer roomNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
}
