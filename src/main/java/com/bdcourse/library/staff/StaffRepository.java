package com.bdcourse.library.staff;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StaffRepository extends CrudRepository<Staff, Integer> {

    @Query(value = "SELECT s FROM Staff s JOIN FETCH s.storage st WHERE s.id > 0")
    List<Staff> findAllFetch();



    //    //9 query
//    List<Object[]> findStaffProductivityByDate();
//
//    //12 query
//    List<Staff> findStaffByStorage();


}
