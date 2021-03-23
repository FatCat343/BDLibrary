package com.bdcourse.library.reader.student.department;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    boolean existsDepartmentByFacultyAndUniversity(String faculty, String university);
}
