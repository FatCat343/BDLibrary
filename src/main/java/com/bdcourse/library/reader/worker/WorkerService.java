package com.bdcourse.library.reader.worker;

import com.bdcourse.library.UI.StudentUI.StudentSort;
import com.bdcourse.library.UI.WorkerUI.WorkerSort;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.reader.student.Student;
import com.bdcourse.library.reader.student.department.Department;
import com.bdcourse.library.reader.worker.company.Company;
import com.bdcourse.library.reader.worker.profession.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public List<Worker> findAll() {
        return (List<Worker>) workerRepository.findAll();
    }

    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }

    public void delete(Worker worker) {
         workerRepository.delete(worker);
    }

    public boolean exist(Worker worker) {
        return !workerRepository.existsWorkerByCompanyAndProfessionAndFirstNameAndFirstNameAndLibrary(worker.getCompany(),
                worker.getProfession(), worker.getFirstName(), worker.getLastName(), worker.getId()).equals(BigInteger.ZERO);
    }

    public WorkerSort createSort(String propertyName, boolean descending) {
        return new WorkerSort(propertyName, descending);
    }
    public List<Worker> fetch(int page, int limit, String firstName, String lastName, String profession, String company,
                              String library, List<WorkerSort> sortOrders) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worker> query = criteriaBuilder.createQuery(Worker.class);
        Root<Worker> root = query.from(Worker.class);
        Join<Worker, Library> workerLibraryJoin = root.join("library");
        //root.fetch("library", JoinType.INNER);
        Join<Worker, Profession> workerProfessionJoin = root.join("profession");
        Join<Worker, Company> workerCompanyJoin = root.join("company");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }
        if (lastName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }
        if (company != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(workerCompanyJoin.get("name")), "%" + company.toLowerCase() + "%"));
        }
        if (profession != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(workerProfessionJoin.get("name")), "%" + profession.toLowerCase() + "%"));
        }
        if (library != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(workerLibraryJoin.get("address")), "%" + library.toLowerCase() + "%"));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        if (!sortOrders.isEmpty()) {
            if (sortOrders.get(0).isDescending()) {
                if (sortOrders.get(0).getPropertyName().equals("company")) {
                    query.orderBy(criteriaBuilder.desc(workerCompanyJoin.get("name")));
                }
                else {
                    if (sortOrders.get(0).getPropertyName().equals("library")) {
                        query.orderBy(criteriaBuilder.desc(workerLibraryJoin.get("address")));
                    }
                    else {
                        if (sortOrders.get(0).getPropertyName().equals("profession")) {
                            query.orderBy(criteriaBuilder.desc(workerProfessionJoin.get("name")));
                        }
                        else {
                            query.orderBy(criteriaBuilder.desc(root.get(sortOrders.get(0).getPropertyName())));
                        }
                    }
                }
            }
            else {
                if (sortOrders.get(0).getPropertyName().equals("company")) {
                    query.orderBy(criteriaBuilder.asc(workerCompanyJoin.get("name")));
                }
                else {
                    if (sortOrders.get(0).getPropertyName().equals("library")) {
                        query.orderBy(criteriaBuilder.asc(workerLibraryJoin.get("address")));
                    }
                    else {
                        if (sortOrders.get(0).getPropertyName().equals("profession")) {
                            query.orderBy(criteriaBuilder.asc(workerProfessionJoin.get("name")));
                        }
                        else {
                            query.orderBy(criteriaBuilder.asc(root.get(sortOrders.get(0).getPropertyName())));
                        }
                    }
                }
            }
        }
        else query.orderBy(criteriaBuilder.asc(root.get("id")));

        List<Worker> result = entityManager.createQuery(query).setFirstResult(page * limit).setMaxResults(limit).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    public long getWorkerCount(String firstName, String lastName, String profession, String company, String library) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worker> query = criteriaBuilder.createQuery(Worker.class);
        Root<Worker> root = query.from(Worker.class);
        Join<Worker, Library> workerLibraryJoin = root.join("library");
        Join<Worker, Profession> workerProfessionJoin = root.join("profession");
        Join<Worker, Company> workerCompanyJoin = root.join("company");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }
        if (lastName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }
        if (company != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(workerCompanyJoin.get("name")), "%" + company.toLowerCase() + "%"));
        }
        if (profession != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(workerProfessionJoin.get("name")), "%" + profession.toLowerCase() + "%"));
        }
        if (library != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(workerLibraryJoin.get("address")), "%" + library.toLowerCase() + "%"));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        int result = entityManager.createQuery(query).getResultList().size();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }
}

