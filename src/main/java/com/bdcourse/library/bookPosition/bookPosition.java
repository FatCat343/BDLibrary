package com.bdcourse.library.bookPosition;

import com.bdcourse.library.storage.Storage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bookposition")
public class bookPosition implements Serializable {
    @Id
    @SequenceGenerator(name = "position_generator", sequenceName = "bookPosition_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_generator")
    @Column(name = "position_id")
    private Integer position_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storage_id")
    private Storage storage_id;

    @Column(name = "rack_number")
    private Integer rack_number;

    @Column(name = "shelf_number")
    private Integer shelf_number;

    public Integer getPosition_id() {
        return position_id;
    }

    public void setPosition_id(Integer position_id) {
        this.position_id = position_id;
    }

    public Storage getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(Storage storage_id) {
        this.storage_id = storage_id;
    }

    public Integer getRack_number() {
        return rack_number;
    }

    public void setRack_number(Integer rack_number) {
        this.rack_number = rack_number;
    }

    public Integer getShelf_number() {
        return shelf_number;
    }

    public void setShelf_number(Integer shelf_number) {
        this.shelf_number = shelf_number;
    }
}