package com.bdcourse.library.publication.author;

import com.bdcourse.library.publication.Publication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
    @Override
    @Query(value = "SELECT * FROM library_schema.author WHERE author_id != 0", nativeQuery = true)
    Iterable<Author> findAll();
}
