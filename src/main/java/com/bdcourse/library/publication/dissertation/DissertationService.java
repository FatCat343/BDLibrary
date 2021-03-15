package com.bdcourse.library.publication.dissertation;

import com.bdcourse.library.UI.DissertationUI.DissertationSort;
import com.bdcourse.library.publication.author.Author;
import com.bdcourse.library.publication.dissertation.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DissertationService {
    @Autowired
    private DissertationRepository dissertationRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public void delete(Dissertation dissertation) {
        dissertationRepository.delete(dissertation);
    }

    public Dissertation save(Dissertation dissertation) {
        return dissertationRepository.save(dissertation);
    }

    public List<Dissertation> fetch(int page, int limit, String title, Set<Author> authors,
                                    Set<Subject> subjects, List<DissertationSort> sortOrders) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dissertation> query = criteriaBuilder.createQuery(Dissertation.class);
        Root<Dissertation> root = query.from(Dissertation.class);
        Join<Dissertation, Author> dissertationAuthorJoin = root.join("author");
        Join<Dissertation, Subject> dissertationSubjectJoin = root.join("subject");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (title != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (authors != null) {
            predicates.add(criteriaBuilder.in(root.get("author")).value(authors));
        }

        if (subjects != null) {
            predicates.add(criteriaBuilder.in(root.get("subject")).value(subjects));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        if (!sortOrders.isEmpty()) {
            if (sortOrders.get(0).isDescending()) {
                if (sortOrders.get(0).getPropertyName().equals("title")) {
                    query.orderBy(criteriaBuilder.desc(root.get("title")));
                }
                else {
                    if (sortOrders.get(0).getPropertyName().equals("subject")) {
                        query.orderBy(criteriaBuilder.desc(dissertationSubjectJoin.get("name")));
                    }
                    else query.orderBy(criteriaBuilder.desc(dissertationAuthorJoin.get("firstName")),
                            criteriaBuilder.desc(dissertationAuthorJoin.get("lastName")));
                }
            }
            else {
                if (sortOrders.get(0).getPropertyName().equals("title")) {
                    query.orderBy(criteriaBuilder.asc(root.get("title")));
                }
                else {
                    if (sortOrders.get(0).getPropertyName().equals("subject")) {
                        query.orderBy(criteriaBuilder.asc(dissertationSubjectJoin.get("name")));
                    }
                    else query.orderBy(criteriaBuilder.asc(dissertationAuthorJoin.get("firstName")),
                            criteriaBuilder.asc(dissertationAuthorJoin.get("lastName")));
                }
            }
        }
        else query.orderBy(criteriaBuilder.asc(root.get("id")));

        List<Dissertation> result = entityManager.createQuery(query).setFirstResult(page * limit).setMaxResults(limit).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

        return result;
    }

    public DissertationSort createSort(String propertyName, boolean descending) {
        return new DissertationSort(propertyName, descending);
    }

    public long getDissertationCount(String title, Set<Author> authors, Set<Subject> subjects) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dissertation> query = criteriaBuilder.createQuery(Dissertation.class);
        Root<Dissertation> root = query.from(Dissertation.class);
//        Join<Dissertation, Author> dissertationAuthorJoin = root.join("author");
//        Join<Dissertation, Subject> dissertationSubjectJoin = root.join("subject");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (title != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (authors != null) {
            predicates.add(criteriaBuilder.in(root.get("author")).value(authors));
        }

        if (subjects != null) {
            predicates.add(criteriaBuilder.in(root.get("subject")).value(subjects));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        int result = entityManager.createQuery(query).getResultList().size();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

}
