package com.bdcourse.library.publication.dissertation.subject;

import com.bdcourse.library.publication.book.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> findAll() {
        return subjectRepository.findAllByOrderByIdAsc();
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void delete(Subject subject) {
        subjectRepository.delete(subject);
    }

    public boolean exist(Subject subject) {
        return subjectRepository.existsSubjectByName(subject.getName());
    }
}
