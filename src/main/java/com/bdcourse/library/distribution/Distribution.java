package com.bdcourse.library.distribution;

import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.staff.Staff;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "distribution")
public class Distribution implements Serializable {
    @Id
    @SequenceGenerator(name = "distribution_generator", sequenceName = "distribution_seq", initialValue = 60)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distribution_generator")
    @Column(name = "distribution_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_id")
    private Edition edition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "date_give")
    private LocalDate dateGive;

    @Column(name = "date_return")
    private LocalDate dateReturn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public LocalDate getDateGive() {
        return dateGive;
    }

    public void setDateGive(LocalDate dateGive) {
        this.dateGive = dateGive;
    }

    public LocalDate getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(LocalDate dateReturn) {
        this.dateReturn = dateReturn;
    }

    @Override
    public String toString() {
        return "Distribution{" +
                "id=" + id +
                ", reader=" + reader +
                ", edition=" + edition +
                ", staff=" + staff +
                ", dateGive=" + dateGive +
                ", dateReturn=" + dateReturn +
                '}';
    }
}


