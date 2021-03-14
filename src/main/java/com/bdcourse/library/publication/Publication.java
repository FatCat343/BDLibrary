package com.bdcourse.library.publication;

import com.bdcourse.library.publication.author.Author;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "publication")
public class Publication implements Serializable {
    @Id
    @SequenceGenerator(name = "publication_generator", sequenceName = "publication_seq", initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publication_generator")
    @Column(name = "publication_id")
    private Integer id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return author + ", " + title;
    }
}
