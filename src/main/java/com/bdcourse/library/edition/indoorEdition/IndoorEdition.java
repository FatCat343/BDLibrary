package com.bdcourse.library.edition.indoorEdition;

import com.bdcourse.library.edition.Edition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "indoor")
public class IndoorEdition extends Edition {
    @Column(name = "reason_for_indoor_usage_only")
    private String reason;


}
