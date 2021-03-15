package com.bdcourse.library.UI.BookUI;

import com.bdcourse.library.publication.author.Author;
import com.bdcourse.library.publication.book.category.Category;

import java.util.Set;

public class BookFilter {
    private String title = null;
    private Set<Author> authors = null;
    private Set<Category> categories = null;

    public BookFilter() {
    }

    public BookFilter(String title, Set<Author> authors, Set<Category> categories) {
        this.title = title;
        this.authors = authors;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
