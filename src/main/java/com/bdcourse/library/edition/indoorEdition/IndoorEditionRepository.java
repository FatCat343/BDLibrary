package com.bdcourse.library.edition.indoorEdition;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IndoorEditionRepository extends CrudRepository<IndoorEdition, Integer> {
    @Query(value = "SELECT e FROM Edition e JOIN FETCH e.publication publ JOIN FETCH e.position pos " +
            "JOIN FETCH pos.storage stor WHERE e.id = :id")
    IndoorEdition findIndoorEditionByIdFetchAll(@Param("id") Integer id);

}
