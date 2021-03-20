package com.bdcourse.library.staff;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StaffRepository extends CrudRepository<Staff, Integer> {

    @Override
    @Query(value = "SELECT * FROM library_schema.staff s WHERE s.staff_id != 0 ORDER BY s.staff_id ASC", nativeQuery = true)
    Iterable<Staff> findAll();

    @Query(value = "SELECT s FROM Staff s JOIN FETCH s.storage st WHERE s.id > 0")
    List<Staff> findAllFetch();

    @Query(value = "SELECT s FROM Staff s JOIN FETCH s.storage st WHERE s.id = :id")
    Staff findStaffByIdFetch(@Param("id") Integer id);

    //9 query
    @Query(value = "SELECT \n" +
            "    s.firstname, s.lastname, COUNT(*) AS served_clients\n" +
            "FROM \n" +
            "    library_schema.reader r \n" +
            "    JOIN library_schema.distribution d ON d.reader_id = r.reader_id\n" +
            "    JOIN library_schema.staff s ON s.staff_id = d.staff_id\n" +
            "WHERE \n" +
            "    d.date_give BETWEEN :start AND :finish \n" +
            "GROUP BY \n" +
            "    s.staff_id", nativeQuery = true)
    List<Object[]> findStaffProductivityByDate(@Param("start")LocalDate start, @Param("finish") LocalDate finish);
//
//    //12 query
//    List<Staff> findStaffByStorage();


}
