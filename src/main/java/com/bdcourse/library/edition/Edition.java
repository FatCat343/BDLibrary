package com.bdcourse.library.edition;

import com.bdcourse.library.bookPosition.BookPosition;
import com.bdcourse.library.publication.Publication;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "edition")
public class Edition implements Serializable {
    @Id
    @SequenceGenerator(name = "edition_generator", sequenceName = "edition_seq", initialValue = 70)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edition_generator")
    @Column(name = "edition_id")
    private Integer edition_id;

    @Column(name = "edition_code")
    private Integer code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private BookPosition position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @Column(name = "date_arrived")
    private Date dateArrived;

    @Column(name = "date_left")
    private Date dateLeft;

    public Integer getEdition_id() {
        return edition_id;
    }

    public void setEdition_id(Integer edition_id) {
        this.edition_id = edition_id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BookPosition getPosition() {
        return position;
    }

    public void setPosition(BookPosition position) {
        this.position = position;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Date getDateArrived() {
        return dateArrived;
    }

    public void setDateArrived(Date dateArrived) {
        this.dateArrived = dateArrived;
    }

    public Date getDateLeft() {
        return dateLeft;
    }

    public void setDateLeft(Date dateLeft) {
        this.dateLeft = dateLeft;
    }
}
