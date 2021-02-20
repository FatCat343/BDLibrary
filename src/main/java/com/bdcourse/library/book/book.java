package com.bdcourse.library.book;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book")
public class book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_book")
    @SequenceGenerator(name = "seq_gen_book", sequenceName = "seq_book")
    @Column(name = "book_id")
    private Integer book_id;

    @Column(name = "publication_id")
    private Integer publication_id;

    @Column(name = "genre")
    private String genre;

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(Integer publication_id) {
        this.publication_id = publication_id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
