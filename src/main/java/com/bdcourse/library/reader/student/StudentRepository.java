package com.bdcourse.library.reader.student;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student, Integer>, JpaSpecificationExecutor {

    @Query(value = "SELECT COUNT (s) FROM Student s WHERE s.id <> :id AND s.code = :code")
    BigInteger existsStudentByCode(@Param("code") Integer code, @Param("id") Integer id);

}
