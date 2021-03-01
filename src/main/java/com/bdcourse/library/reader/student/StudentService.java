package com.bdcourse.library.reader.student;

import com.bdcourse.library.UI.StudentUI.StudentSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll();
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> fetch(int page, int limit, String firstName, String lastName, String code,
                               String library, String department, List<StudentSort> sortOrders) {

        System.out.println("ask " + page + " page");
        return studentRepository.findAll(PageRequest.of(page, limit)).getContent();
    }

    public StudentSort createSort(String propertyName, boolean descending) {
        return new StudentSort(propertyName, descending);
    }

    public long getStudentCount(String firstName, String lastName, String code, String library, String department){
        return studentRepository.count();
    }
}
