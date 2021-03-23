package com.bdcourse.library.edition;

import com.bdcourse.library.bookPosition.BookPosition;
import com.bdcourse.library.edition.indoorEdition.IndoorEdition;
import com.bdcourse.library.publication.Publication;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "edition")
public class Edition implements Serializable {
    @Id
    @SequenceGenerator(name = "edition_generator", sequenceName = "edition_seq", initialValue = 70)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edition_generator")
    @Column(name = "edition_id")
    private Integer id;

    @Column(name = "edition_code")
    private Integer code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private BookPosition position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @Column(name = "date_arrived")
    private LocalDate dateArrived;

    @Column(name = "date_left")
    private LocalDate dateLeft;

    public Edition() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer Id) {
        this.id = Id;
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

    public LocalDate getDateArrived() {
        return dateArrived;
    }

    public void setDateArrived(LocalDate dateArrived) {
        this.dateArrived = dateArrived;
    }

    public LocalDate getDateLeft() {
        return dateLeft;
    }

    public void setDateLeft(LocalDate dateLeft) {
        this.dateLeft = dateLeft;
    }

    @Override
    public String toString() {
        return code.toString();
    }
}
