package com.bdcourse.library.reader.student;

import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.student.department.Department;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student")
public class Student extends Reader {


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "student_code")
    private Integer code;


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Student() {
    }

    public Student(Integer code) {
        super();
        this.code = code;
    }

}
