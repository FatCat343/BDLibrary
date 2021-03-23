package com.bdcourse.library.distribution;

import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.storage.Storage;
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
public class DistributionService {
    @Autowired
    private DistributionRepository distributionRepository;
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public List<Distribution> findAll() {
        return (List<Distribution>) distributionRepository.findAll();
    }

    public Distribution save(Distribution distribution) {
        return distributionRepository.save(distribution);
    }

    public void delete(Distribution distribution) {
        distributionRepository.delete(distribution);
    }

    public boolean exists(Distribution distribution) {
        return !distributionRepository.existsDistributionByStaffAndReaderAndEditionAndDateGive(
                distribution.getStaff(), distribution.getReader(), distribution.getEdition(), distribution.getDateGive(),
                distribution.getId()).equals(BigInteger.ZERO);
    }

    public Distribution findDistributionFetch(Distribution distribution) {
        if (distribution == null) {
            return null;
        }
        else {
            return distributionRepository.findDistributionByIdFetch(distribution.getId());
        }
    }

    public Distribution findDistributionFetchEdition(Distribution distribution) {
        if (distribution == null) return null;
        else return distributionRepository.findDistributionByIdFetchEdition(distribution.getId());
    }

    public Distribution findDistributionFetchStaff(Distribution distribution) {
        if (distribution == null) return null;
        else return distributionRepository.findDistributionByIdFetchStaff(distribution.getId());
    }

    public Distribution findDistributionFetchReader(Distribution distribution) {
        if (distribution == null) return null;
        else return distributionRepository.findDistributionByIdFetchReader(distribution.getId());
    }

    public List<Distribution> findReaderAndEditionByPublicationAndDate(String title,
                                                                       LocalDate start,
                                                                       LocalDate finish) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distribution> query = criteriaBuilder.createQuery(Distribution.class);
        Root<Distribution> root = query.from(Distribution.class);
        root.fetch("reader", JoinType.INNER);
        root.fetch("edition", JoinType.INNER);
        Join<Distribution, Edition> editionJoin = root.join("edition");
        Join<Distribution, Reader> readerJoin = root.join("reader");
        Join<Edition, Publication> publicationJoin = editionJoin.join("publication");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(publicationJoin.get("title"), title));
        if (start != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("dateGive"), start));
        }
        if (finish != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("dateGive"), finish));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.asc(readerJoin.get("id")));
        List<Distribution> result = entityManager.createQuery(query).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }
}
