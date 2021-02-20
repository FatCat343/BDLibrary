package com.bdcourse.library.distribution;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "distribution")
public class distribution implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_distribution")
    @SequenceGenerator(name = "seq_gen_distribution", sequenceName = "seq_distribution")
    @Column(name = "distribution_id")
    private Integer distribution_id;

    @Column(name = "reader_id")
    private Integer reader_id;

    @Column(name = "edition_id")
    private Integer edition_id;

    @Column(name = "staff_id")
    private Integer staff_id;

    @Column(name = "date_give")
    private Date date_give;

    @Column(name = "date_return")
    private Date date_return;

    public Integer getDistribution_id() {
        return distribution_id;
    }

    public void setDistribution_id(Integer distribution_id) {
        this.distribution_id = distribution_id;
    }

    public Integer getReader_id() {
        return reader_id;
    }

    public void setReader_id(Integer reader_id) {
        this.reader_id = reader_id;
    }

    public Integer getEdition_id() {
        return edition_id;
    }

    public void setEdition_id(Integer edition_id) {
        this.edition_id = edition_id;
    }

    public Integer getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Integer staff_id) {
        this.staff_id = staff_id;
    }

    public Date getDate_give() {
        return date_give;
    }

    public void setDate_give(Date date_give) {
        this.date_give = date_give;
    }

    public Date getDate_return() {
        return date_return;
    }

    public void setDate_return(Date date_return) {
        this.date_return = date_return;
    }
}


