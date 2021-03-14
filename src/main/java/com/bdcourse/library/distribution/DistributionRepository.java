package com.bdcourse.library.distribution;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistributionRepository extends CrudRepository<Distribution, Integer> {

    @Override
    @Query(value = "SELECT * FROM library_schema.distribution d ORDER BY d.distribution_id ASC", nativeQuery = true)
    Iterable<Distribution> findAll();

    @Query(value = "SELECT d FROM Distribution d JOIN FETCH d.edition e JOIN FETCH d.reader r " +
            "JOIN FETCH d.staff s WHERE d.id = :id")
    Distribution findDistributionByIdFetch(@Param("id") Integer id);

    @Query(value = "SELECT d FROM Distribution d JOIN FETCH d.edition e " +
            " WHERE d.id = :id")
    Distribution findDistributionByIdFetchEdition(@Param("id") Integer id);

    @Query(value = "SELECT d FROM Distribution d JOIN FETCH d.staff s WHERE d.id = :id")
    Distribution findDistributionByIdFetchStaff(@Param("id") Integer id);

    @Query(value = "SELECT d FROM Distribution d JOIN FETCH d.reader r WHERE d.id = :id")
    Distribution findDistributionByIdFetchReader(@Param("id") Integer id);
}
