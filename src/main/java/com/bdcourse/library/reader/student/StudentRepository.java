package com.bdcourse.library.reader.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    @Query("SELECT s FROM Student s JOIN FETCH s.library JOIN FETCH s.department ")
    List<Student> findAllFetch();
}
