package com.bdcourse.library.reader;

import com.bdcourse.library.distribution.Distribution;
import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.reader.student.Student;
import com.bdcourse.library.staff.Staff;
import com.bdcourse.library.storage.Storage;
import com.vaadin.flow.templatemodel.AllowClientUpdates;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public List<Reader> findAll() {
        return (List<Reader>) readerRepository.findAll();
    }

    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    public void delete(Reader reader) {
        readerRepository.delete(reader);
    }

    public List<Reader> findReaderByPublication(String name) {
        return readerRepository.findReaderByPublication(name);
    }

    public List<Reader> findReaderByEdition(Integer code) {
        if (code == null) return new ArrayList<Reader>();
        else return readerRepository.findReaderByEdition(code);
    }

//    public List<Object[]> findReaderAndEditionByPublicationAndDate(String title, LocalDate start, LocalDate finish) {
//        return readerRepository.findReaderAndEditionByPublicationAndDate(title, start, finish);
//    }

    public List<Reader> findReaderByStaffAndDate(Integer staffId, LocalDate start, LocalDate finish) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distribution> query = criteriaBuilder.createQuery(Distribution.class);
        Root<Distribution> root = query.from(Distribution.class);
        Fetch<Distribution, Edition> editionFetch = root.fetch("edition", JoinType.INNER);
        root.fetch("reader", JoinType.INNER);
        Join<Distribution, Staff> staffJoin = root.join("staff");
        Join<Distribution, Reader> readerJoin = root.join("reader");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(staffJoin.get("id"), staffId));

        if (start != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("dateGive"), start));
        }
        if (finish != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("dateGive"), finish));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.asc(readerJoin.get("id")));
        List<Reader> resEdition = new ArrayList<>();
        entityManager.createQuery(query).getResultList().forEach(distribution ->
                resEdition.add(distribution.getReader()));
        entityManager.getTransaction().commit();
        entityManager.close();
        return resEdition;
    }

    public List<Reader> findReaderExpired() {
        return readerRepository.findReaderExpired();
    }

    public List<Reader> findReaderByAvoidingLibraryAndDate(LocalDate start, LocalDate finish) {
        //return readerRepository.findReaderByAvoidingLibraryAndDate(start, finish);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reader> query = criteriaBuilder.createQuery(Reader.class);
        Root<Reader> root = query.from(Reader.class);

        Subquery<BigInteger> visitors = query.subquery(BigInteger.class);
        Root<Distribution> visitorsRoot = visitors.from(Distribution.class);
        visitors.select(visitorsRoot.get("reader"));
        List<Predicate> subPredicatesGive = new ArrayList<>();
        if (start != null) {
            subPredicatesGive.add(criteriaBuilder.greaterThan(visitorsRoot.get("dateGive"), start));
        }
        if (finish != null) {
            subPredicatesGive.add(criteriaBuilder.lessThan(visitorsRoot.get("dateGive"), finish));
        }
        List<Predicate> subPredicatesReturn = new ArrayList<>();
        if (start != null) {
            subPredicatesReturn.add(criteriaBuilder.greaterThan(visitorsRoot.get("dateReturn"), start));
        }
        if (finish != null) {
            subPredicatesReturn.add(criteriaBuilder.lessThan(visitorsRoot.get("dateReturn"), finish));
        }
        visitors.where(criteriaBuilder.or(
                criteriaBuilder.and(subPredicatesGive.toArray(new Predicate[subPredicatesGive.size()])),
                criteriaBuilder.and(subPredicatesReturn.toArray(new Predicate[subPredicatesReturn.size()]))));

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.notEqual(root.get("id"), 0));
        predicates.add(criteriaBuilder.not(root.get("id").in(visitors)));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.asc(root.get("id")));
        List<Reader> resEdition = entityManager.createQuery(query).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return resEdition;
    }
}
