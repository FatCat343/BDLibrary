package com.bdcourse.library.publication.book.category;

import com.bdcourse.library.publication.book.Book;
import com.bdcourse.library.publication.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByIdAsc();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public boolean exist(Category category) {
        return categoryRepository.existsCategoryByName(category.getName());
    }
}
