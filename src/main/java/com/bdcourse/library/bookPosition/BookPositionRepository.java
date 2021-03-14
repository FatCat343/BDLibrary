package com.bdcourse.library.bookPosition;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookPositionRepository extends CrudRepository<BookPosition, Integer> {

    @Query(value = "SELECT p FROM BookPosition p JOIN FETCH p.storage st WHERE p.id > 0")
    List<BookPosition> findAllFetch();
}
