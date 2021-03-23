package com.bdcourse.library.publication.book.category;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    List<Category> findAllByOrderByIdAsc();

    boolean existsCategoryByName(String name);
}
