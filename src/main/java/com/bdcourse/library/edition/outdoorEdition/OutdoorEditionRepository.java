package com.bdcourse.library.edition.outdoorEdition;

import com.bdcourse.library.edition.Edition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutdoorEditionRepository extends CrudRepository<OutdoorEdition, Integer> {
    @Query(value = "SELECT e FROM Edition e JOIN FETCH e.publication publ JOIN FETCH e.position pos " +
            "JOIN FETCH pos.storage stor WHERE e.id = :id")
    OutdoorEdition findOutdoorEditionByIdFetchAll(@Param("id") Integer id);

}
