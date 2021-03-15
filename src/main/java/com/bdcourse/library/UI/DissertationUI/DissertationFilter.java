package com.bdcourse.library.UI.DissertationUI;

import com.bdcourse.library.publication.author.Author;
import com.bdcourse.library.publication.dissertation.subject.Subject;

import java.util.Set;

public class DissertationFilter {
    private String title = null;
    private Set<Author> authors = null;
    private Set<Subject> subjects = null;

    public DissertationFilter(String title, Set<Author> authors, Set<Subject> subjects) {
        this.title = title;
        this.authors = authors;
        this.subjects = subjects;
    }

    public DissertationFilter() {
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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
