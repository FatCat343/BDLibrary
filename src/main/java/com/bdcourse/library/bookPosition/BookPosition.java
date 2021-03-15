package com.bdcourse.library.bookPosition;

import com.bdcourse.library.storage.Storage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bookposition")
public class BookPosition implements Serializable {
    @Id
    @SequenceGenerator(name = "position_generator", sequenceName = "bookPosition_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_generator")
    @Column(name = "position_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Column(name = "rack_number")
    private Integer rackNumber;

    @Column(name = "shelf_number")
    private Integer shelfNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Integer getRackNumber() {
        return rackNumber;
    }

    public void setRackNumber(Integer rackNumber) {
        this.rackNumber = rackNumber;
    }

    public Integer getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(Integer shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    @Override
    public String toString() {
        return "shelf: " + shelfNumber + ", rack: " + rackNumber + ", " + storage.toString();
    }
}