package com.bdcourse.library.publication.book;

import com.bdcourse.library.UI.BookUI.BookSort;
import com.bdcourse.library.publication.author.Author;
import com.bdcourse.library.publication.book.category.Category;
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
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> fetch(int page, int limit, String title, Set<Author> authors,
                            Set<Category> categories, List<BookSort> sortOrders) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        Join<Book, Author> bookAuthorJoin = root.join("author");
        Join<Book, Category> bookCategoryJoin = root.join("category");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (title != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (authors != null) {
            predicates.add(criteriaBuilder.in(root.get("author")).value(authors));
        }

        if (categories != null) {
            predicates.add(criteriaBuilder.in(root.get("category")).value(categories));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        if (!sortOrders.isEmpty()) {
            if (sortOrders.get(0).isDescending()) {
                if (sortOrders.get(0).getPropertyName().equals("title")) {
                    query.orderBy(criteriaBuilder.desc(root.get("title")));
                }
                else {
                    if (sortOrders.get(0).getPropertyName().equals("category")) {
                        query.orderBy(criteriaBuilder.desc(bookCategoryJoin.get("name")));
                    }
                    else query.orderBy(criteriaBuilder.desc(bookAuthorJoin.get("firstName")),
                            criteriaBuilder.desc(bookAuthorJoin.get("lastName")));
                }
            }
            else {
                if (sortOrders.get(0).getPropertyName().equals("title")) {
                    query.orderBy(criteriaBuilder.asc(root.get("title")));
                }
                else {
                    if (sortOrders.get(0).getPropertyName().equals("category")) {
                        query.orderBy(criteriaBuilder.asc(bookCategoryJoin.get("name")));
                    }
                    else query.orderBy(criteriaBuilder.asc(bookAuthorJoin.get("firstName")),
                            criteriaBuilder.asc(bookAuthorJoin.get("lastName")));
                }
            }
        }
        else query.orderBy(criteriaBuilder.asc(root.get("id")));

        List<Book> result = entityManager.createQuery(query).setFirstResult(page * limit).setMaxResults(limit).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

        return result;
    }

    public BookSort createSort(String propertyName, boolean descending) {
        return new BookSort(propertyName, descending);
    }

    public long getBookCount(String title, Set<Author> authors, Set<Category> categories) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        Join<Book, Author> bookAuthorJoin = root.join("author");
        Join<Book, Category> bookCategoryJoin = root.join("category");

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (title != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (authors != null) {
            predicates.add(criteriaBuilder.in(root.get("author")).value(authors));
        }

        if (categories != null) {
            predicates.add(criteriaBuilder.in(root.get("category")).value(categories));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        int result = entityManager.createQuery(query).getResultList().size();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

}
