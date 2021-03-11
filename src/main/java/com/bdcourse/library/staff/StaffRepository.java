package com.bdcourse.library.staff;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StaffRepository extends CrudRepository<Staff, Integer> {

    @Override
    @Query(value = "SELECT * FROM library_schema.staff s WHERE s.staff_id != 0 ORDER BY s.staff_id ASC", nativeQuery = true)
    Iterable<Staff> findAll();

    @Query(value = "SELECT s FROM Staff s JOIN FETCH s.storage st WHERE s.id > 0")
    List<Staff> findAllFetch();

    @Query(value = "SELECT s FROM Staff s JOIN FETCH s.storage st WHERE s.id = :id")
    Staff findStaffByIdFetch(@Param("id") Integer id);

    //    //9 query
//    List<Object[]> findStaffProductivityByDate();
//
//    //12 query
//    List<Staff> findStaffByStorage();


}
