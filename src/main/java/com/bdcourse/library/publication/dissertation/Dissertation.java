package com.bdcourse.library.publication.dissertation;


import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.publication.dissertation.subject.Subject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dissertation")
public class Dissertation extends Publication {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
