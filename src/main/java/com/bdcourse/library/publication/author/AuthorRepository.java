package com.bdcourse.library.publication.author;

import com.bdcourse.library.publication.Publication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
    @Override
    @Query(value = "SELECT * FROM library_schema.author a WHERE a.author_id != 0 ORDER BY a.author_id ASC", nativeQuery = true)
    Iterable<Author> findAll();

    boolean existsAuthorByFirstNameAndLastName(String firstName, String lastName);
}
