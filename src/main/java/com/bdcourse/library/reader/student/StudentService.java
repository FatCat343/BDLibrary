package com.bdcourse.library.reader.student;

import com.bdcourse.library.UI.StudentUI.StudentSort;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.reader.student.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.swing.text.html.parser.Entity;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll();
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public Student save(Student student) {
        //student.setStudent_id(student.getId());
        return studentRepository.save(student);
    }

    public boolean exist(Student student) {
        return !studentRepository.existsStudentByCode(student.getCode(), student.getId()).equals(BigInteger.ZERO);
    }
    @Transactional
    public List<Student> fetch(int page, int limit, String firstName, String lastName, String code,
                               String library, String department, List<StudentSort> sortOrders) {
//        System.out.println("ask " + page + " page");
//        System.out.println("filters : " + "firstname = " + firstName + " lastname = " + lastName + " code = " + code +
//                " library = " + library + " department = " + department);
//        System.out.println("sort order : " + sortOrders.toString());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);
        Join<Student, Library> studentLibraryJoin = root.join("library");
        Join<Student, Department> studentDepartmentJoin = root.join("department");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }
        if (lastName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }
        if (code != null) {
            predicates.add(criteriaBuilder.equal(root.get("code"), Integer.parseInt(code)));
        }
        if (library != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(studentLibraryJoin.get("address")), "%" + library.toLowerCase() + "%"));
        }
        if (department != null) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(studentDepartmentJoin.get("faculty")), "%" + department.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(studentDepartmentJoin.get("university")), "%" + department.toLowerCase() + "%")));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        if (!sortOrders.isEmpty()) {
            if (sortOrders.get(0).isDescending()) {
                if (sortOrders.get(0).getPropertyName().equals("department")) {
                    query.orderBy(criteriaBuilder.desc(studentDepartmentJoin.get("university")),
                            criteriaBuilder.desc(studentDepartmentJoin.get("faculty")));
                }
                else {
                    if (sortOrders.get(0).getPropertyName().equals("library")) {
                        query.orderBy(criteriaBuilder.desc(studentLibraryJoin.get("address")));
                    }
                    else query.orderBy(criteriaBuilder.desc(root.get(sortOrders.get(0).getPropertyName())));
                }
            }
            else {
                if (sortOrders.get(0).getPropertyName().equals("department")) {
                    query.orderBy(criteriaBuilder.asc(studentDepartmentJoin.get("university")),
                            criteriaBuilder.asc(studentDepartmentJoin.get("faculty")));
                }
                else {
                    if (sortOrders.get(0).getPropertyName().equals("library")) {
                        query.orderBy(criteriaBuilder.asc(studentLibraryJoin.get("address")));
                    }
                    else query.orderBy(criteriaBuilder.asc(root.get(sortOrders.get(0).getPropertyName())));
                }
            }
        }
        else query.orderBy(criteriaBuilder.asc(root.get("id")));

        List<Student> result = entityManager.createQuery(query).setFirstResult(page * limit).setMaxResults(limit).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        //System.out.println(result);
        return result;
    }

    public StudentSort createSort(String propertyName, boolean descending) {
        return new StudentSort(propertyName, descending);
    }

    public long getStudentCount(String firstName, String lastName, String code, String library, String department){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);
        Join<Student, Library> studentLibraryJoin = root.join("library");
        Join<Student, Department> studentDepartmentJoin = root.join("department");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }
        if (lastName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }
        if (code != null) {
            predicates.add(criteriaBuilder.equal(root.get("code"), Integer.parseInt(code)));
        }
        if (library != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(studentLibraryJoin.get("address")), "%" + library.toLowerCase() + "%"));
        }
        if (department != null) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(studentDepartmentJoin.get("faculty")), "%" + department.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(studentDepartmentJoin.get("university")), "%" + department.toLowerCase() + "%")));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        int result = entityManager.createQuery(query).getResultList().size();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }
}
