package com.bdcourse.library.reader.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> findAll() {
        return studentRepository.findAllFetch();
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
