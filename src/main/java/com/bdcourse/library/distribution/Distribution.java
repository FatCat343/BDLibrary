package com.bdcourse.library.distribution;

import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.staff.Staff;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    private Staff staff_id;

    @Column(name = "date_give")
    private Date dateGive;

    @Column(name = "date_return")
    private Date dateReturn;


}


