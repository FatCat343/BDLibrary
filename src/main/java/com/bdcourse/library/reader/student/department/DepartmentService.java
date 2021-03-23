package com.bdcourse.library.reader.student.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        return (List<Department>) departmentRepository.findAll();
    }

    public void save(Department department) {
        departmentRepository.save(department);
    }

    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    public boolean exist(Department department) {
        return departmentRepository.existsDepartmentByFacultyAndUniversity(department.getFaculty(), department.getUniversity());
    }
}
