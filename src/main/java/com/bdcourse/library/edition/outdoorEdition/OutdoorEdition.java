package com.bdcourse.library.edition.outdoorEdition;

import com.bdcourse.library.edition.Edition;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.time.Duration;


@Entity
@Table(name = "outdoor")
public class OutdoorEdition extends Edition {
    @Column(name = "rental_period")
    private Integer rentalPeriod;

    public Integer getRentalPeriod() {
        return rentalPeriod;
    }

    public void setRentalPeriod(Integer rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }
}
