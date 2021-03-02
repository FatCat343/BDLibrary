package com.bdcourse.library.reader.student;

import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.student.department.Department;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student")
public class Student extends Reader {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen2")
    @SequenceGenerator(name = "gen2", initialValue = 50, schema = "library_schema")
    @Column(name = "student_id")
    private Integer student_id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "student_code")
    private Integer code;


    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

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
