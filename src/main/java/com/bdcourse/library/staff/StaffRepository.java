package com.bdcourse.library.staff;

import com.bdcourse.library.storage.Storage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
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
            "    s.firstname, s.lastname,case when COUNT(*) > 1 then COUNT(*) else 0 end AS served_clients\n" +
            "FROM (SELECT\n" +
            "        d.*\n" +
            "    FROM\n" +
            "        library_schema.distribution d \n" +
            "    WHERE \n" +
            "        d.date_give BETWEEN :start AND :finish ) AS distr\n" +
            "    JOIN library_schema.reader r ON distr.reader_id = r.reader_id\n" +
            "    FULL OUTER JOIN library_schema.staff s ON s.staff_id = distr.staff_id\n" +
            "WHERE \n" +
            "\ts.staff_id != 0\n" +
            "GROUP BY \n" +
            "    s.staff_id\n" +
            "ORDER BY \n" +
            "\ts.staff_id ASC", nativeQuery = true)
    List<Object[]> findStaffProductivityByDate(@Param("start")LocalDate start, @Param("finish") LocalDate finish);

    //@Procedure(value = "library_schema.mvp_staff", outputParameterName = "_val")
    @Query(value = "call library_schema.mvp_staff()", nativeQuery = true)
    String findMostProductiveStaff();
//
//    //12 query
//    List<Staff> findStaffByStorage();

    List<Staff> findAllByStorage(Storage storage);

    boolean existsStaffByFirstNameAndLastNameAndStorage(String firstName, String lastName, Storage storage);
}
