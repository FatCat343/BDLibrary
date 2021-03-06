package com.bdcourse.library.library;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LibraryRepository extends CrudRepository<Library, Integer> {
    @Override
    @Query(value = "SELECT l.* FROM library_schema.library l WHERE l.library_id != 0 ORDER BY l.library_id ASC", nativeQuery = true)
    Iterable<Library> findAll();

    boolean existsLibraryByAddress(String address);
}
