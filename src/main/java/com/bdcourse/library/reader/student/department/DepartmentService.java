package com.bdcourse.library.reader.student.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        List<Department> res =  (List<Department>) departmentRepository.findAll();
        System.out.println(res);
        return res;
    }

}
