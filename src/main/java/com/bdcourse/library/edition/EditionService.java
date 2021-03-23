package com.bdcourse.library.edition;

import com.bdcourse.library.distribution.Distribution;
import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.publication.author.Author;
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
public class EditionService {
    @Autowired
    private EditionRepository editionRepository;
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public List<Edition> findAll() {
        return (List<Edition>) editionRepository.findAll();
    }

    public void delete(Edition edition) {
        editionRepository.delete(edition);
    }

    public Edition save(Edition edition) {
        return editionRepository.save(edition);
    }

    public boolean exist(Edition edition) {
        return !editionRepository.existsEditionByCode(edition.getCode(), edition.getId()).equals(BigInteger.ZERO);
    }

    public List<Edition> findAllFetchAll() {
        return editionRepository.findAllFetchAll();
    }

    public List<Edition> findAllByDateFetchAll(Boolean isArrived, LocalDate start, LocalDate finish) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Edition> query = criteriaBuilder.createQuery(Edition.class);
        Root<Edition> root = query.from(Edition.class);
        root.fetch("publication", JoinType.INNER);
        root.fetch("position", JoinType.INNER);
        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.notEqual(root.get("id"), 0));

        if (isArrived != null) {
            String dateType;
            if (isArrived) dateType = "dateArrived";
            else dateType = "dateLeft";

            if (start != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get(dateType), start));
            }
            if (finish != null) {
                predicates.add(criteriaBuilder.lessThan(root.get(dateType), finish));
            }
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.asc(root.get("id")));
        List<Edition> resEdition = entityManager.createQuery(query).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return resEdition;
    }

    public List<Edition> findEditionByAuthor(Author author) {
        return author == null ? new ArrayList<Edition>() : editionRepository.findEditionByAuthor(author.getId());
    }

    public List<Edition> findEditionByPublication(Publication publication) {
        return publication == null ? new ArrayList<Edition>() : editionRepository.findEditionByPublication(publication.getId());
    }

    public List<Edition> findEditionByReaderInLibrary(Integer readerId, LocalDate start, LocalDate finish, Boolean inAssigned) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distribution> query = criteriaBuilder.createQuery(Distribution.class);
        Root<Distribution> root = query.from(Distribution.class);
        Fetch<Distribution, Edition> editionFetch = root.fetch("edition", JoinType.INNER);
        editionFetch.fetch("publication", JoinType.INNER);
        editionFetch.fetch("position", JoinType.INNER);
        Join<Distribution, Edition> editionJoin = root.join("edition");
        Join<Distribution, Reader> readerJoin = root.join("reader");
        Join<Distribution, Storage> storageJoin = editionJoin.join("position").join("storage");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(readerJoin.get("id"), readerId));
        if (inAssigned) {
            predicates.add(criteriaBuilder.equal(readerJoin.get("library"), storageJoin.get("library")));
        }
        else predicates.add(criteriaBuilder.notEqual(readerJoin.get("library"), storageJoin.get("library")));

        if (start != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("dateGive"), start));
        }
        if (finish != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("dateGive"), finish));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.asc(editionJoin.get("id")));
        List<Edition> resEdition = new ArrayList<>();
        entityManager.createQuery(query).getResultList().forEach(distribution ->
                resEdition.add(distribution.getEdition()));
        entityManager.getTransaction().commit();
        entityManager.close();
        return resEdition;
    }

    public List<Edition> findGivenEditionByPosition(String address, Integer room, Integer rack, Integer shelf) {
        System.out.println("find in address = " + address + ", room = " + room + ", rack = " + rack + ", shelf = " + shelf);
        return editionRepository.findGivenEditionByPosition(address, room, rack, shelf);
    }

    public List<Object[]> findEditionByArrivedDate(LocalDate start, LocalDate finish) {
        return editionRepository.findEditionByArrivedDate(start, finish);
    }
}
