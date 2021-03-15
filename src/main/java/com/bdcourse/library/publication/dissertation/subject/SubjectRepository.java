package com.bdcourse.library.publication.dissertation.subject;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {
    List<Subject> findAllByOrderByIdAsc();
}
