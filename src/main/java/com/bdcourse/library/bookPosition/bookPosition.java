package com.bdcourse.library.bookPosition;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bookPosition")
public class bookPosition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_bookPosition")
    @SequenceGenerator(name = "seq_gen_bookPosition", sequenceName = "seq_bookPosition")
    @Column(name = "position_id")
    private Integer position_id;

    @Column(name = "storage_id")
    private Integer storage_id;

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

    public Integer getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(Integer storage_id) {
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