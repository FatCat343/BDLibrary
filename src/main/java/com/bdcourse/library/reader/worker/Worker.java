package com.bdcourse.library.reader.worker;

import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.worker.company.Company;
import com.bdcourse.library.reader.worker.profession.Profession;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "worker")
public class Worker extends Reader {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profession_id")
    private Profession profession;



    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

}
