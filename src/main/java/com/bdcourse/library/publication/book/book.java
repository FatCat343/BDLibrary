package com.bdcourse.library.publication.book;

import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.publication.book.category.Category;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book")
public class book extends Publication {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
